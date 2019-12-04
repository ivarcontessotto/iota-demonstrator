package ch.hslu.iotademonstrator.oracleapp.config;

import ch.hslu.iotademonstrator.oracleapp.inputprovider.config.WebServiceInputConfig;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;
import org.qubiclite.qlite.oracle.input.OracleInputConfig;
import org.qubiclite.qlite.oracle.input.ValueType;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.Queue;

public class WebserviceConfigLoader {

    private static final String ServiceUrl = "ServiceUrl";
    private static final String PollingInterval = "PollingInterval";
    private static final String ValueQueries = "ValueQueries";

    public static OracleInputConfig load(JSONObject inputConfigJson) {
        try {
            return new WebServiceInputConfig(
                    ValueType.valueOf(inputConfigJson.getString(ConfigLoader.ValueType)),
                    new URL(inputConfigJson.getString(ServiceUrl)),
                    inputConfigJson.getInt(PollingInterval),
                    toQueue(inputConfigJson.getJSONArray(ValueQueries)));

        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Malformed Webservice URL.", e);
        }
    }

    private static Queue<JsonPath> toQueue(JSONArray jsonArray) {
        Queue<JsonPath> queue = new LinkedList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            queue.add(JsonPath.compile(jsonArray.getString(i)));
        }
        return queue;
    }
}
