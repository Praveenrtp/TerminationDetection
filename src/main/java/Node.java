public class Node {
  private int nodeId;
  private int parentNode;
  private int msgCount;
  private int ackCount;

  public Node(int nodeId) {
    this.nodeId = nodeId;
    parentNode = -1;
    msgCount = 0;
    ackCount = 0;
  }

  public int getNodeId() {
    return this.nodeId;
  }

  public void setParent( int parentNode) {
    if(this.parentNode == -1) {
      this.parentNode = parentNode;
    }
  }

  public int getParent() {
    return this.parentNode;
  }

  public int getMsgCount() {
    return msgCount;
  }

  public int getAckCount() {
    return ackCount;
  }
}
