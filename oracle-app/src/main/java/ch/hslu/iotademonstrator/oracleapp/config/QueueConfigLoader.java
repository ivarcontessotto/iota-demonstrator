package ch.hslu.iotademonstrator.oracleapp.config;

import org.json.JSONArray;
import org.json.JSONObject;
import org.qubiclite.qlite.oracle.input.OracleInputConfig;
import org.qubiclite.qlite.oracle.input.QueueInputConfig;
import org.qubiclite.qlite.oracle.input.ValueType;

import java.util.LinkedList;
import java.util.Queue;

class QueueConfigLoader {

    private final static String InputSequence = "InputSequence";

    static OracleInputConfig load(JSONObject inputConfigJson) {

        return new QueueInputConfig(
                ValueType.valueOf(inputConfigJson.getString(ConfigLoader.ValueType)),
                toQueue(inputConfigJson.getJSONArray(InputSequence))
        );
    }

    private static Queue<String> toQueue(JSONArray jsonArray) {
        Queue<String> queue = new LinkedList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            queue.add(jsonArray.getString(i));
        }
        return queue;
    }
}
