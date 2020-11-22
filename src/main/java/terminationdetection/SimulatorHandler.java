package terminationdetection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import io.vertx.ext.web.RoutingContext;

public class SimulatorHandler implements Handler<RoutingContext> {
  private Vertx vertx;
  private Node node;
  private int portNo;

  public SimulatorHandler(Vertx vertx, int portNo) {
    this.vertx = vertx;
    this.portNo = portNo;
  }

  @Override
  public void handle(RoutingContext routingContext) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      SimulatorMessage simulatorMessage = objectMapper.readValue(routingContext.getBodyAsString(), SimulatorMessage.class);
      if (simulatorMessage.getCommand().equals(SimulatorCommands.INITIATOR.name())) {
        System.out.println("Node " + simulatorMessage.getSourceNo() + " Initiated the Termination Detection Algorithm");
        node.setParent(simulatorMessage.getSourceNo());
        node.setState(State.ACTIVE.name());
      } else if (simulatorMessage.getCommand().equals(SimulatorCommands.SEND.name())) {
        Message message = new Message();
        message.setSenderNodeID(simulatorMessage.getSourceNo());
        String jsonMessage = objectMapper.writeValueAsString(message);
        vertx.createHttpClient()
            .post(portNo, "localhost","/", response -> {
              System.out.println("Response Status Code:" + response.statusCode());
            })
            .write(jsonMessage);

      }

      System.out.println("sourceNo :" + simulatorMessage.getSourceNo());
      System.out.println("command :" + simulatorMessage.getCommand());
      System.out.println("destinationNo :" + simulatorMessage.getDestinationNo());
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }
    routingContext.response().end("Message Received");
  }
}


