package restapihelper;

import org.json.simple.JSONObject;

import java.io.BufferedReader;

public interface JsonBuilderInterface {

    JSONObject createJsonObject(BufferedReader br);

}
