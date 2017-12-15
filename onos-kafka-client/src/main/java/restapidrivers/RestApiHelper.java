package restapidrivers;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

public interface RestApiHelper {


    DefaultHttpClient createHttpClient(String username, String password);

    HttpGet getRequest(HttpClient httpClient, String uri);

    HttpResponse getResponse(HttpGet HttpRequest, HttpClient httpClient);

    void httpClientShutDown(HttpClient httpClient);

}
