import com.sun.jdi.IntegerValue;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
public class Server extends AbstractVerticle {
  Node node;

  public Server(int nodeId) {
    this.node = new Node(nodeId);
  }

  @Override
  public void start(Future<Void> future) {
    vertx.createHttpServer()
        .requestHandler(new ServerHandler(vertx,node))
        .listen(9090,
            result -> {
              if (result.succeeded()) {
                future.complete();
              }
              if (result.failed()) {
                future.cause();
              }
            });
  }

  public static void main(String[] args) {
    int nodeId = Integer.parseInt(args[0]);
    System.out.println("Node Id = " + nodeId);
    Vertx vertx = Vertx.vertx();
    vertx.deployVerticle(new Server(nodeId));
  }
}
