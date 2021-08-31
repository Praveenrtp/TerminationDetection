## Executed a tree-based Dijkstra-Scholten algorithm for detecting termination in a distributed system. Successfully tested it on a set of 20 systems, using asynchronous I/O Vertx framework for communication between systems.

## Termination detection

The basis of termination detection is in the concept of a distributed system process' state. At any time, a process in a distributed system is either in an active state or in an idle state. An active process may become idle at any time but an idle process may only become active again upon receiving a computational message.
Termination occurs when all processes in the distributed system become idle and there are no computational messages in transit.

The Dijkstra–Scholten algorithm is a tree-based algorithm which can be described by the following:
- The initiator of a computation is the root of the tree.
- Upon receiving a computational message:
    - If the receiving process is currently not in the computation: the process joins the tree by becoming a child of the sender of the message. (No acknowledgment message is sent at this point.)
    - If the receiving process is already in the computation: the process immediately sends an acknowledgment message to the sender of the message.
- When a process has no more children and has become idle, the process detaches itself from the tree by sending an acknowledgment message to its tree parent.
- Termination occurs when the initiator has no children and has become idle.

