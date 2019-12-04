package ch.hslu.iotademonstrator.oracleapp.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;
import org.qubiclite.qlite.oracle.input.OracleInputConfig;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigLoader {

    private final static String AppAddressSeed = "AppAddressSeed";
    private final static String PromotionTag = "PromotionTag";
    private final static String InputConfig = "InputConfig";
    private final static String Method = "Method";
    protected final static String ValueType = "ValueType";

    private final static Logger LOGGER = LogManager.getLogger(ConfigLoader.class);
    private static Path configFilePath = Paths.get("config.json");

    protected static void setConfigFilePath(Path path) {
        configFilePath = path;
    }

    public static OracleConfig load() {
        try {
            String configFileContent = new String(Files.readAllBytes(configFilePath), StandardCharsets.UTF_8);
            return toConfig(configFileContent);

        } catch (IOException | JSONException | IllegalArgumentException e) {
            LOGGER.error("Error loading oracle configuration.", e);
            throw new IllegalStateException(e);
        }
    }

    private static OracleConfig toConfig(String configFileContent) {
        JSONObject configJson = new JSONObject(configFileContent);
        JSONObject inputConfigJson = configJson.getJSONObject(InputConfig);
        InputMethod inputMethod = InputMethod.valueOf(inputConfigJson.getString(Method));
        return new OracleConfig(
                configJson.getString(AppAddressSeed),
                configJson.getString(PromotionTag),
                inputMethod,
                parseInputConfig(inputMethod, inputConfigJson));
    }

    private static OracleInputConfig parseInputConfig(InputMethod inputMethod, JSONObject inputConfigJson) {
        switch (inputMethod) {
            case QUEUE:
                return QueueConfigLoader.load(inputConfigJson);
            case LOGFILE:
                return LogfileConfigLoader.load(inputConfigJson);
            case MAMSTREAM:
                return MamstreamConfigLoader.load(inputConfigJson);
            case WEBSERVICE:
                return WebserviceConfigLoader.load(inputConfigJson);
            default:
                throw new IllegalArgumentException("Unknown oracle input type.");
        }
    }
}
