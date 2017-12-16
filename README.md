
# Usage Guide:

1- First, follow the instructions in https://wiki.onosproject.org/display/ONOS/Kafka+Integration to run onos, Activate Zookeeper and, Activate the Kafka server.

2- If you run onos on a remote server (i.e. a machine other than your localhost), you need to update URLs (**KAFKA_REGISTER** and **KAFKA_SUBSRIBE**) in *OnosRestUrls.java* based on your remote server's IP address. 

3- After the above steps, If you run the main, the program registers itself to listen to the link events. You can easily change it to listen to other type of events (i.e. host evetns).  

4- To test the functionality of the consumer app, you can use mininet to simulate a network topology.  You can use link command (e.g link switch1 switch2 down, link switch1 switch2 up) to enable and disable links between the network switches. Whenever there is a change in the links between the network devices, the program detects it and print recevied consumer records. 
