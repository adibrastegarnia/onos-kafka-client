
# Usage Guide:

1- First, follow the instructions in https://wiki.onosproject.org/display/ONOS/Kafka+Integration to run onos, activate Zookeeper and, activate the Kafka server.
- **Note:** If you run onos on a remote server (i.e. a machine other than your localhost), you need to update URLs (**KAFKA_REGISTER** and **KAFKA_SUBSRIBE**) in *onosRestUrls.java* based on your remote server's IP address. 

3- After the above steps, If you run the main, the program registers itself to listen to the link events. You can easily change it to listen to other type of events (i.e. host evetns).  

4- To test the functionality of the consumer app, you can use mininet to simulate a network topology.  In the mininet environment, you can use the link command (e.g *link switch1 switch2 down*, or *link switch1 switch2 up*) to enable or disable links between the network switches. Whenever there is a change in the links between the network devices, the program detects it and prints recevied consumer records. 
