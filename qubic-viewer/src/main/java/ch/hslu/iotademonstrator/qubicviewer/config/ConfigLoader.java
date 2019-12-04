package ch.hslu.iotademonstrator.qubicviewer.config;

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

    private final static String PromotionTag = "PromotionTag";

    private final static Logger LOGGER = LogManager.getLogger(ConfigLoader.class);
    private static Path configFilePath = Paths.get("config.json");

    protected static void setConfigFilePath(Path path) {
        configFilePath = path;
    }

    public static QubicViewerConfig load() {
        try {
            String configFileContent = new String(Files.readAllBytes(configFilePath), StandardCharsets.UTF_8);
            return toConfig(configFileContent);

        } catch (IOException | JSONException e) {
            LOGGER.error("Error loading qubic viewer configuration.", e);
            throw new IllegalStateException(e);
        }
    }

    private static QubicViewerConfig toConfig(String configFileContent) {
        JSONObject configJson = new JSONObject(configFileContent);
        return new QubicViewerConfig(
                configJson.getString(PromotionTag));
    }
}
