package ch.hslu.iotademonstrator.oracleapp.config;

import ch.hslu.iotademonstrator.oracleapp.inputprovider.config.MAMMode;
import ch.hslu.iotademonstrator.oracleapp.inputprovider.config.MamStreamInputConfig;
import com.jayway.jsonpath.JsonPath;
import org.json.JSONArray;
import org.json.JSONObject;
import org.qubiclite.qlite.oracle.input.OracleInputConfig;
import org.qubiclite.qlite.oracle.input.ValueType;

import java.util.LinkedList;
import java.util.Queue;

public class MamstreamConfigLoader {

    private static final String ServicePort = "ServicePort";
    private static final String PollingInterval = "PollingInterval";
    private static final String NodeProviderUrl = "NodeProviderUrl";
    private static final String Mode = "Mode";
    private static final String RootAddress = "RootAddress";
    private static final String EncryptionKey = "EncryptionKey";
    private static final String ValueQueries = "ValueQueries";

    public static OracleInputConfig load(JSONObject inputConfigJson) {

        return new MamStreamInputConfig(
                ValueType.valueOf(inputConfigJson.getString(ConfigLoader.ValueType)),
                inputConfigJson.getInt(ServicePort),
                inputConfigJson.getInt(PollingInterval),
                inputConfigJson.getString(NodeProviderUrl),
                MAMMode.valueOf(inputConfigJson.getString(Mode)),
                inputConfigJson.getString(RootAddress),
                inputConfigJson.getString(EncryptionKey),
                toQueue(inputConfigJson.getJSONArray(ValueQueries)));
    }

    private static Queue<JsonPath> toQueue(JSONArray jsonArray) {
        Queue<JsonPath> queue = new LinkedList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            queue.add(JsonPath.compile(jsonArray.getString(i)));
        }
        return queue;
    }
}
