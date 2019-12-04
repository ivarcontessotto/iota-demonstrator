package ch.hslu.iotademonstrator.qubicapp.config;

import org.junit.Assert;
import org.junit.Test;

import java.nio.file.Paths;

public class ConfigLoaderTest {

    @Test
    public void testLoadQubicConfiguration() {
        // Arrange
        ConfigLoader.setConfigFilePath(Paths.get("src/test/res/config.json"));
        ConfigLoader.setCodeFilePath(Paths.get("src/test/res/qubic.ql"));
        // Act
        QubicConfig config = ConfigLoader.load();
        // Assert
        Assert.assertEquals("SHYPXXS9WFWCPGXLSFWUGFSTZNJFOBKHCOOPWPUODUZ9MTUKSRQHETK99QGJYVURWDRDSNHCGGTOBZMRU", config.getAppAddressSeed());
        Assert.assertEquals("IOTAISLIFE3", config.getPromotionTag());
        Assert.assertEquals(180, config.getSecondsUntilExecution());
        Assert.assertEquals(120, config.getSecondsUntilAssemble());
        Assert.assertEquals(30, config.getSecondsHashPeriod());
        Assert.assertEquals(30, config.getSecondsResultPeriod());
        Assert.assertEquals(10, config.getSecondsRuntimeLimit());
        Assert.assertEquals("kmh=GetInput(0);if(kmh<=10){traffic='stau';}else{traffic='normal';}return(traffic);", config.getCode());
    }
}
