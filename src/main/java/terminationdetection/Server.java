package terminationdetection;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;

public class Server extends AbstractVerticle {
  private Node node;
  private int portNo;


  public Server(int nodeId, int portNo) {
    this.node = new Node(nodeId);
    this.portNo = portNo;
  }

  @Override
  public void start() {
    String path = "/simulator";
    Router router = Router.router(vertx);
    router.route().handler(BodyHandler.create());
    router.route("/").handler(new ServerHandler(vertx, node));
    router.route(path).handler(new SimulatorHandler(vertx));

    vertx.createHttpServer()
        .requestHandler(router)
        .listen(portNo,
            result -> {
              if (result.succeeded()) {
                System.out.println("Server Started with node id :" + node.getNodeId());
              }
              if (result.failed()) {
                System.out.println("Server failed to start with node id : " + node.getNodeId());
              }
            });
  }

  public static void main(String[] args) {
    int nodeId = Integer.parseInt(args[0]);
    int portNo = Integer.parseInt(args[1]);
    System.out.println("Node Id = " + nodeId);
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Server(nodeId,portNo));
  }
}

