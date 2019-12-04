package ch.hslu.iotademonstrator.qubicapp.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigLoader {

    private final static String AppAddressSeed = "AppAddressSeed";
    private final static String PromotionTag = "PromotionTag";
    private final static String QubicSpecification = "QubicSpecification";
    private final static String SecondsUntilExecution = "SecondsUntilExecution";
    private final static String SecondsUntilAssemble = "SecondsUntilAssemble";
    private final static String SecondsHashPeriod = "SecondsHashPeriod";
    private final static String SecondsResultPeriod = "SecondsResultPeriod";
    private final static String SecondsRuntimeLimit = "SecondsRuntimeLimit";

    private final static Logger LOGGER = LogManager.getLogger(ConfigLoader.class);
    private static Path configFilePath = Paths.get("config.json");
    private static Path codeFilePath = Paths.get("qubic.ql");

    protected static void setConfigFilePath(Path path) {
        configFilePath = path;
    }

    protected static void setCodeFilePath(Path path) {
        codeFilePath = path;
    }

    public static QubicConfig load() {
        try {
            String configFileContent = new String(Files.readAllBytes(configFilePath), StandardCharsets.UTF_8);
            String codeFileContent = new String(Files.readAllBytes(codeFilePath), StandardCharsets.UTF_8);
            return toConfig(configFileContent, codeFileContent);

        } catch (IOException | JSONException e) {
            LOGGER.error("Error loading qubic configuration.", e);
            throw new IllegalStateException(e);
        }
    }

    private static QubicConfig toConfig(String configFileContent, String codeFileContent) {
        String code = codeFileContent.replaceAll("\\s+","").trim();
        JSONObject configJson = new JSONObject(configFileContent);
        JSONObject qubicSpecJson = configJson.getJSONObject(QubicSpecification);
        return new QubicConfig(
                configJson.getString(AppAddressSeed),
                configJson.getString(PromotionTag),
                qubicSpecJson.getInt(SecondsUntilExecution),
                qubicSpecJson.getInt(SecondsUntilAssemble),
                qubicSpecJson.getInt(SecondsHashPeriod),
                qubicSpecJson.getInt(SecondsResultPeriod),
                qubicSpecJson.getInt(SecondsRuntimeLimit),
                code);
    }
}
