package restapihelper;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;


public class JsonBuilder implements JsonBuilderInterface {

    /**
     * Create a JSON object based on a given buffer.
     *
     * @param br an instance of BufferReader
     * @return a JSON object contains retrieved information usign REST-API.
     */
    public JSONObject createJsonObject(BufferedReader br) {

        JSONObject jsonObject = null;
        Object obj = null;
        JSONParser parser = new JSONParser();

        String line;
        StringBuilder sb = new StringBuilder();

        try {

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            obj = parser.parse(sb.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return (JSONObject) obj;
    }


}
