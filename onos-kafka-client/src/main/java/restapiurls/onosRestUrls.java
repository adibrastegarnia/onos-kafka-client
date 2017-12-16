package restapiurls;


public enum onosRestUrls {

    KAFKA_REGISTER("http://127.0.0.1:8181/onos/kafka-integration/kafkaService/register"),
    KAFKA_SUBSCRIBE("http://127.0.0.1:8181/onos/kafka-integration/kafkaService/subscribe");


    private String url;

    onosRestUrls(String url) {
        this.url = url;

    }


    public String getUrl() {
        return this.url;
    }
}
