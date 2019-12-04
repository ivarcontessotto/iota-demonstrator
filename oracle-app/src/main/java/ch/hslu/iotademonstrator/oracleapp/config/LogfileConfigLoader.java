package ch.hslu.iotademonstrator.oracleapp.config;

import ch.hslu.iotademonstrator.oracleapp.inputprovider.config.LogfileInputConfig;
import org.json.JSONObject;
import org.qubiclite.qlite.oracle.input.OracleInputConfig;
import org.qubiclite.qlite.oracle.input.ValueType;

import java.nio.file.Paths;
import java.util.regex.Pattern;

public class LogfileConfigLoader {

    private static final String LogfilePath = "LogfilePath";
    private static final String ValueRegex = "ValueRegex";

    public static OracleInputConfig load(JSONObject inputConfigJson) {

        return new LogfileInputConfig(
                ValueType.valueOf(inputConfigJson.getString(ConfigLoader.ValueType)),
                Paths.get(inputConfigJson.getString(LogfilePath)),
                Pattern.compile(inputConfigJson.getString(ValueRegex)));
    }
}
