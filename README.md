# A Decentralized Voting System
This is the implementation of a decentralized voting by DATX02-87.

**Requirements**
* Docker

## Starting A Development Network
To start up a demo environment, execute the following script.
```bash
$ ./scripts/start-demo.sh
```
Please note that you might need to make the file executable. Do that by executing `chmod +x scripts/start-demo.sh`.

The development network is being run with a single node and devmode consensus, and should not be used for production environments. 

After the network has started, you can reach the web UI on port 8080. The terminal in which you started the network will outpout several different private keys with the ability to vote on the network.

<!-- ## Starting A Production Network
The production network makes use of the Practical Byzantine Fault Tolerant consensus algorithm. It needs at least 4 nodes to be operable.

**Requirements**
* The nodes should be reachable over port  -->