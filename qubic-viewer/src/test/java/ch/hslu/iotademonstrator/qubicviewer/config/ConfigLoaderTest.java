package ch.hslu.iotademonstrator.qubicviewer.config;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

public class ConfigLoaderTest {

    @Test
    public void testLoadQubicConfiguration() {
        // Arrange
        ConfigLoader.setConfigFilePath(Paths.get("src/test/res/config.json"));
        // Act
        QubicViewerConfig config = ConfigLoader.load();
        // Assert
        Assert.assertEquals("IOTAISLIFE3", config.getPromotionTag());
    }
}
