package terminationdetection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import io.vertx.ext.web.RoutingContext;

public class ServerHandler implements Handler<RoutingContext> {
  Vertx vertx;
  Node node;


  public ServerHandler(Vertx vertx, Node node) {
    this.vertx = vertx;
    this.node = node;
  }

  @Override
  public void handle(RoutingContext routingContext) {
    // Get the sender's node id from http request.
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      Message message = objectMapper.readValue(routingContext.getBodyAsString(), Message.class);
      System.out.println("SenderNodeId :" + message.getSenderNodeID());
      if (node.hasParent()) {
        Message responseMessage = new Message();
        responseMessage.setAck(true);
        responseMessage.setSenderNodeID(node.getNodeId());
        String responseJsonMessage = objectMapper.writeValueAsString(responseMessage);
        routingContext.response().write(responseJsonMessage);
      } else {
        node.setParent(message.getSenderNodeID());
        node.setState(State.ACTIVE.name());
      }
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    routingContext.response().setStatusCode(200).end("Message Received");
  }

}


