package terminationdetection;

public class Message {
  private int senderNodeID;
  Node node;

  public boolean isAck() {
    return isAck;
  }

  private boolean isAck;

  public boolean setAck(boolean ack) {
    if(node.getAckCount() == 0) {
      return true;
    }
    return false;
  }

  public int getSenderNodeID() {
    return senderNodeID;
  }

  public void setSenderNodeID(int senderNodeId) {
    this.senderNodeID = senderNodeId;
  }
}
