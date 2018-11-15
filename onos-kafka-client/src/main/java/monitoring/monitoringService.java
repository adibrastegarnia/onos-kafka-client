package monitoring;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.BytesDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.utils.Bytes;
import org.json.simple.JSONObject;
import restapihelper.DefaultRestApiHelper;
import restapiurls.onosRestUrls;

import java.io.BufferedReader;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.Properties;
import java.util.Arrays;

/**
 * Monitor device and link events using onos kafka-integration app.
 */
public class monitoringService {


    private final static String BOOTSTRAP_SERVERS =
            "localhost:9092,localhost:9093,localhost:9094";
    DefaultRestApiHelper restApiHelper;
    DefaultHttpClient httpClient;

    /**
     * Default constructor.
     */
    public monitoringService() {
        restApiHelper = new DefaultRestApiHelper();
        httpClient = restApiHelper.createHttpClient("onos", "rocks");

    }

    private static Consumer<Long, Bytes> createConsumer(JSONObject registerResponse, String eventType) {
        final Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,
                registerResponse.get("groupId").toString());
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                LongDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                BytesDeserializer.class.getName());

        final Consumer<Long, Bytes> consumer =
                new KafkaConsumer<>(props);

        // Subscribe to the topic.
        consumer.subscribe(Collections.singletonList(eventType));


        return consumer;
    }

    /**
     * Register an app for recevining onos events.
     * @param appName application name
     * @return registration response.
     */
    public BufferedReader kafkaRegister(String appName) {
        StringEntity input = null;
        try {
            input = new StringEntity(appName.toString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        BufferedReader br = restApiHelper.httpPostRequest(httpClient,
                onosRestUrls.KAFKA_REGISTER.getUrl(), input);

        return br;
    }

    /**
     *
     * @param eventType Event type.
     * @param appName application name.
     * @param registerReponse registration response.
     */
    public void kafkaSubscribe(String eventType, String appName, JSONObject registerReponse) {


        JSONObject jsonResult = new JSONObject();
        jsonResult.put("appName", appName);
        jsonResult.put("groupId", registerReponse.get("groupId").toString());
        jsonResult.put("eventType", eventType);
        StringEntity stringEntity = null;
        try {
            stringEntity = new StringEntity(jsonResult.toJSONString());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        restApiHelper.httpPostRequest(httpClient,
                onosRestUrls.KAFKA_SUBSCRIBE.getUrl(), stringEntity);

    }

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for ( int j = 0; j < bytes.length; j++ ) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * creates and runs a consumer.
     * @param registerResponse register response information.
     * @throws InterruptedException
     */
    public void runConsumer(JSONObject registerResponse, String eventType) throws InterruptedException {
        final Consumer<Long, Bytes> consumer = createConsumer(registerResponse, eventType);


        while (true) {
            final ConsumerRecords<Long, Bytes> consumerRecords =
                    consumer.poll(100);


            consumerRecords.forEach(record -> {
                System.out.printf("value1 = %s\n", record.value().get());


            });

            consumer.commitAsync();

        }

    }

}
