package terminationdetection;


public class Node {
  private int nodeId;
  private int parentNode;
  private int ackCount;
  private String state;
  Node node;







  public void setAckCount(int ackCount) {
    this.ackCount = ackCount;
  }



  public void setState(String state) {
    this.state = state;
  }

  public String getState() {
    return state;
  }

  public Node(int nodeId) {
    this.nodeId = nodeId;
    parentNode = -1;
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



  public int getAckCount() {
    return ackCount;
  }


//  public void checkIdle () throws InterruptedException {
//    if(node.getAckCount() == node.getMsgCount() && node.getState().equals(State.INACTIVE.name())) {
//      if(node.getNodeId() != node.getParent()) {
//        node.setState(State.IDLE.name());
//        node.setParent(-1);
//        System.out.println("node " + node.getNodeId() + " is idle");
//
//      }
//      else {
//        System.out.println(" Termination Detection Finished ");
//      }
//    }
//  }
  public boolean hasParent() {
    if(parentNode == -1) {
      return false;
    }
    return true;
  }
}
