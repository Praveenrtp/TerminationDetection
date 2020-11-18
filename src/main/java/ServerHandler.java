import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import io.vertx.core.http.HttpServerRequest;

public class ServerHandler implements Handler<HttpServerRequest> {
  Vertx vertx;
  Node node;


  public ServerHandler(Vertx vertx, Node node) {
    this.vertx = vertx;
    this.node = node;
  }



  @Override
  public void handle(HttpServerRequest httpServerRequest) {
    // Get the sender's node id from http request.
    int senderNodeId = Integer.parseInt(httpServerRequest.getFormAttribute("SenderNodeId"));
    System.out.println("Sender Node Id = " + senderNodeId);
  }
}

