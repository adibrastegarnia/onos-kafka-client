package main;

import monitoring.monitoringService;
import org.json.simple.JSONObject;
import restapihelper.JsonBuilder;

import java.io.BufferedReader;

public class Main {

    public static void main(String[] args) {


        /**
         * Register a consumer to receive link events.
         */
        String appName = "kafkaConsumerApp";
        String EventType = "PACKET";
        JsonBuilder jsonBuilder = new JsonBuilder();
        monitoringService monitor = new monitoringService();
        BufferedReader PacketsBufferReader = monitor.kafkaRegister(appName);
        JSONObject registerReponse = jsonBuilder.createJsonObject(PacketsBufferReader);
        monitor.kafkaSubscribe(EventType, appName, registerReponse);

        try {
            monitor.runConsumer(registerReponse,EventType);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
