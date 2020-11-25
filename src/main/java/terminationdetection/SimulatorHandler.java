package terminationdetection;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;

import io.vertx.ext.web.RoutingContext;

public class SimulatorHandler implements Handler<RoutingContext> {
  private Vertx vertx;
  private Node node;

  public SimulatorHandler(Vertx vertx) {
    this.vertx = vertx;
  }

  @Override
  public void handle(RoutingContext routingContext) {
    try {
      ObjectMapper objectMapper = new ObjectMapper();
      SimulatorMessage simulatorMessage = objectMapper.readValue(routingContext.getBodyAsString(), SimulatorMessage.class);
      System.out.println("sourceNo :" + simulatorMessage.getSourceNo());
      System.out.println("command :" + simulatorMessage.getCommand());
      System.out.println("destinationNo :" + simulatorMessage.getDestinationNo());

      if (simulatorMessage.getCommand().equals(SimulatorCommands.INITIATOR.name())) {
        System.out.println("Node " + simulatorMessage.getSourceNo() + " Initiated the Termination Detection Algorithm");
        node.setParent(simulatorMessage.getSourceNo());
        node.setState(State.ACTIVE.name());
      } else if (simulatorMessage.getCommand().equals(SimulatorCommands.SEND.name())) {
        Message message = new Message();
        message.setSenderNodeID(simulatorMessage.getSourceNo());
        String jsonMessage = objectMapper.writeValueAsString(message);
        System.out.println("Sending Message to Server : " + simulatorMessage.getDestinationNo());
        vertx.createHttpClient()
            .post(9091, "localhost", "/", response -> {
              System.out.println("Response Status Code:" + response.statusCode());
            })
            .setChunked(true)
            .write(jsonMessage)
            .end();
        System.out.println("Sent Message");
      } else if (simulatorMessage.getCommand().equals(SimulatorCommands.IDLE.name())) {
        node.setState(State.IDLE.name());
        long timerId = vertx.setPeriodic(1000, id -> {
          if (node.getAckCount() == 0) {
            if (node.getNodeId() != node.getParent()) {
              node.setParent(-1);
              Message message = new Message();
              message.setAck(true);
              message.setSenderNodeID(node.getNodeId());
              String lastJsonMessage = null;
              try {
                lastJsonMessage = objectMapper.writeValueAsString(message);
              } catch (JsonProcessingException e) {
                e.printStackTrace();
              }
              vertx.createHttpClient()
                  .post(9092, "localhost", "/", lastResponse -> {
                    System.out.println("Last Ack from nodeId :" + node.getNodeId());
                  })
                  .setChunked(true)
                  .write(lastJsonMessage)
                  .end();
              vertx.cancelTimer(id);
            }
          }
        });

      }
    } catch (JsonMappingException exception) {
      exception.printStackTrace();
    } catch (JsonProcessingException exception) {
      exception.printStackTrace();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    routingContext.response().end("Message Received");
  }
}



