package apps;

import monitoring.monitoringService;
import org.json.simple.JSONObject;
import restapihelper.JsonBuilder;

import java.io.BufferedReader;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class linkEventConsumer {

    public static void main(String[] args) {


        /**
         * Register a consumer to receive link events.
         */
        String appName = "LinkConsumerApp";
        String EventType = "LINK";

        JsonBuilder jsonBuilder = new JsonBuilder();
        monitoringService monitor = new monitoringService();

        BufferedReader PacketsBufferReader = monitor.kafkaRegister(appName);
        JSONObject registerReponse = jsonBuilder.createJsonObject(PacketsBufferReader);
        monitor.kafkaSubscribe(EventType, appName, registerReponse);

        ExecutorService monitorExecutor = Executors.newSingleThreadExecutor();

        monitorExecutor.execute(() -> {
            try {
                monitor.linkEventConsumer(registerReponse, EventType);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });


    }
}
