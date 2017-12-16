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
        String linkEventType = "LINK";
        JsonBuilder jsonBuilder = new JsonBuilder();
        monitoringService monitorLink = new monitoringService();
        BufferedReader LinksBufferReader = monitorLink.kafkaRegister(appName);
        JSONObject registerReponseLinks = jsonBuilder.createJsonObject(LinksBufferReader);
        monitorLink.kafkaSubscribe(linkEventType, appName, registerReponseLinks);

        try {
            monitorLink.runConsumer(registerReponseLinks,linkEventType);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }
}
