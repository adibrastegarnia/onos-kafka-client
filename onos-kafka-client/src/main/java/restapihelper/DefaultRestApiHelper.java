package restapihelper;

import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * REST API helper functions.
 */
public class DefaultRestApiHelper implements RestApiHelper {

    /**
     * Create an HTTP client to connect to REST API server.
     *
     * @param username username
     * @param password password
     * @return an http client instance
     */
    public DefaultHttpClient createHttpClient(final String username,
                                              final String password) {
        DefaultHttpClient httpClient = new DefaultHttpClient();
        httpClient.getCredentialsProvider().setCredentials(
                new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT),
                new UsernamePasswordCredentials(username, password));

        return httpClient;
    }

    /**
     * Get an http request.
     *
     * @param httpClient an instance of http client
     * @param uri        address of http page
     * @return an instance of HttpGet
     */
    public HttpGet getRequest(HttpClient httpClient, String uri) {

        HttpGet httpReq = new HttpGet(uri);
        httpReq.addHeader("accept", "application/json");
        return httpReq;

    }

    /**
     * Execute a http request to get a HTTP response.
     *
     * @param httpRequest an http request.
     * @param httpClient  an instance of http client.
     * @return an HttpResponse instance
     */
    public HttpResponse getResponse(HttpGet httpRequest, HttpClient httpClient) {

        HttpResponse httpResponse = null;
        try {
            httpResponse = httpClient.execute(httpRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + httpResponse.getStatusLine().getStatusCode());
        }
        //httpClientShutDown(httpClient);
        return httpResponse;

    }

    /**
     * Shut down http client connection.
     *
     * @param httpClient an instance of HTTP client.
     */
    public void httpClientShutDown(HttpClient httpClient) {
        httpClient.getConnectionManager().shutdown();

    }


    /**
     * Post a HTTP Request.
     *
     * @param httpClient   an instance of HTTPClient
     * @param uri          URL address
     * @param stringEntity an instance of the JSON entity which should
     *                     be posted.
     */
    public BufferedReader httpPostRequest(HttpClient httpClient,
                                          String uri,
                                          StringEntity stringEntity) {

        HttpPost postRequest = new HttpPost(uri);
        stringEntity.setContentType("application/json");
        postRequest.setEntity(stringEntity);


        HttpResponse response = null;
        try {
            response = httpClient.execute(postRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.getStatusLine().getStatusCode() != 200) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        }

        BufferedReader br = null;
        try {
            br = new BufferedReader(
                    new InputStreamReader((response.getEntity().getContent())));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //httpClientShutDown(httpClient);

        return br;

    }

    /**
     * Create a http delete request.
     * @param httpClient an instance of http client.
     * @param uri url.
     */

    public void httpDelRequest(HttpClient httpClient,
                               String uri) {

        HttpDelete delRequest = new HttpDelete(uri);
        delRequest.addHeader("accept", "application/json");

        HttpResponse response = null;
        try {
            response = httpClient.execute(delRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (response.getStatusLine().getStatusCode() != 204) {
            throw new RuntimeException("Failed : HTTP error code : "
                    + response.getStatusLine().getStatusCode());
        }


    }

}
