package apps;

import monitoring.monitoringService;
import org.json.simple.JSONObject;
import restapihelper.JsonBuilder;

import java.io.BufferedReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class packetConsumer {

    public static void main(String[] args) {


        /**
         * Register a consumer to receive link events.
         */
        String appName = "PacketConsumerApp";
        String EventType = "PACKET";
        
        //String appName2 = "kafkaConsumerApp2";
        //String EventType2 = "LINK";
        JsonBuilder jsonBuilder = new JsonBuilder();
        monitoringService monitor = new monitoringService();
        //monitoringService monitor2 = new monitoringService();

        BufferedReader PacketsBufferReader = monitor.kafkaRegister(appName);
        //BufferedReader PacketsBufferReader2 = monitor.kafkaRegister(appName2);
        JSONObject registerReponse = jsonBuilder.createJsonObject(PacketsBufferReader);
        //JSONObject registerReponse2 = jsonBuilder.createJsonObject(PacketsBufferReader2);
        monitor.kafkaSubscribe(EventType, appName, registerReponse);
        //monitor2.kafkaSubscribe(EventType2, appName2, registerReponse2);

        ExecutorService monitorExecutor = Executors.newSingleThreadExecutor();

        monitorExecutor.execute(()-> {
            try {
                monitor.packetEventConsumer(registerReponse,EventType);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        /*System.out.println("Monitor 1 is running");
        monitorExecutor.execute(()-> {
            try {
                monitor2.linkEventConsumer(registerReponse2,EventType2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });*/



    }
}
