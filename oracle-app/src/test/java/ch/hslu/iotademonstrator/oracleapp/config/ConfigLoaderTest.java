package ch.hslu.iotademonstrator.oracleapp.config;

import ch.hslu.iotademonstrator.oracleapp.inputprovider.config.LogfileInputConfig;
import ch.hslu.iotademonstrator.oracleapp.inputprovider.config.MAMMode;
import ch.hslu.iotademonstrator.oracleapp.inputprovider.config.MamStreamInputConfig;
import ch.hslu.iotademonstrator.oracleapp.inputprovider.config.WebServiceInputConfig;
import com.jayway.jsonpath.JsonPath;
import org.junit.Assert;
import org.junit.Test;
import org.qubiclite.qlite.oracle.input.QueueInputConfig;
import org.qubiclite.qlite.oracle.input.ValueType;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Queue;
import java.util.regex.Pattern;

public class ConfigLoaderTest {

    @Test
    public void testLoadOracleConfigForQueueInput() {
        // Arrange
        ConfigLoader.setConfigFilePath(Paths.get("src/test/res/config_queue.json"));
        // Act
        OracleConfig config = ConfigLoader.load();
        // Assert
        Assert.assertEquals("SHYPXXS9WFWCPGXLSFWUGFSTZNJFOBKHCOOPWPUODUZ9MTUKSRQHETK99QGJYVURWDRDSNHCGGTOBZMRU", config.getAppAddressSeed());
        Assert.assertEquals("IOTAISLIFE3", config.getPromotionTag());
        Assert.assertEquals(InputMethod.QUEUE, config.getInputMethod());
        Assert.assertEquals(ValueType.STRING, config.getInputConfig().getValueType());

        Assert.assertTrue(config.getInputConfig() instanceof QueueInputConfig);
        QueueInputConfig inputConfig = (QueueInputConfig)config.getInputConfig();

        // Asserts that input sequence in config is immutable!
        Assert.assertEquals("50", inputConfig.getInputSequence().poll());
        Assert.assertEquals("50", inputConfig.getInputSequence().poll());
        Assert.assertEquals(5, inputConfig.getInputSequence().size());

        Queue sequence = inputConfig.getInputSequence();
        Assert.assertEquals("50", sequence.poll());
        Assert.assertEquals("40", sequence.poll());
        Assert.assertEquals("4", sequence.poll());
        Assert.assertEquals("2", sequence.poll());
        Assert.assertEquals("20", sequence.poll());
        Assert.assertEquals(0, sequence.size());
    }

    @Test
    public void testLoadOracleConfigForLogfileInput() {
        // Arrange
        ConfigLoader.setConfigFilePath(Paths.get("src/test/res/config_logfile.json"));
        // Act
        OracleConfig config = ConfigLoader.load();
        // Assert
        Assert.assertEquals("SHYPXXS9WFWCPGXLSFWUGFSTZNJFOBKHCOOPWPUODUZ9MTUKSRQHETK99QGJYVURWDRDSNHCGGTOBZMRU", config.getAppAddressSeed());
        Assert.assertEquals("IOTAISLIFE3", config.getPromotionTag());
        Assert.assertEquals(InputMethod.LOGFILE, config.getInputMethod());
        Assert.assertEquals(ValueType.INTEGER, config.getInputConfig().getValueType());

        Assert.assertTrue(config.getInputConfig() instanceof LogfileInputConfig);
        LogfileInputConfig inputConfig = (LogfileInputConfig)config.getInputConfig();
        Assert.assertEquals(Paths.get("C:/somefolder/target/Sensor.log"), inputConfig.getLogfilePath());
        Assert.assertEquals(Pattern.compile("Speed \\[km/h]: ([0-9]*),").pattern(), inputConfig.getValueRegex().pattern());
    }

    @Test
    public void testLoadOracleConfigForWebserviceInput() throws MalformedURLException {
        // Arrange
        ConfigLoader.setConfigFilePath(Paths.get("src/test/res/config_webservice.json"));
        // Act
        OracleConfig config = ConfigLoader.load();
        // Assert
        Assert.assertEquals("SHYPXXS9WFWCPGXLSFWUGFSTZNJFOBKHCOOPWPUODUZ9MTUKSRQHETK99QGJYVURWDRDSNHCGGTOBZMRU", config.getAppAddressSeed());
        Assert.assertEquals("IOTAISLIFE3", config.getPromotionTag());
        Assert.assertEquals(InputMethod.WEBSERVICE, config.getInputMethod());
        Assert.assertEquals(ValueType.DOUBLE, config.getInputConfig().getValueType());

        Assert.assertTrue(config.getInputConfig() instanceof WebServiceInputConfig);

        WebServiceInputConfig inputConfig = (WebServiceInputConfig)config.getInputConfig();
        Assert.assertEquals(new URL("https://data.geo.admin.ch/ch.meteoschweiz.messwerte-lufttemperatur-10min/ch.meteoschweiz.messwerte-lufttemperatur-10min_de.json"), inputConfig.getServiceUrl());
        Assert.assertEquals(10, inputConfig.getServicePollingInterval());

        // Asserts that the queries queue from the config is immutable!
        Assert.assertEquals(JsonPath.compile("$.features[?(@.id=='CHZ')].properties.value").getPath(), inputConfig.getValueQueries().poll().getPath());
        Assert.assertEquals(JsonPath.compile("$.features[?(@.id=='CHZ')].properties.value").getPath(), inputConfig.getValueQueries().poll().getPath());
        Assert.assertEquals(2, inputConfig.getValueQueries().size());

        Queue<JsonPath> queries = inputConfig.getValueQueries();
        Assert.assertEquals(JsonPath.compile("$.features[?(@.id=='CHZ')].properties.value").getPath(), queries.poll().getPath());
        Assert.assertEquals(JsonPath.compile("$[0]").getPath(), queries.poll().getPath());
        Assert.assertEquals(0, queries.size());
    }

    @Test
    public void testLoadOracleConfigForMamstreamInput() {
        // Arrange
        ConfigLoader.setConfigFilePath(Paths.get("src/test/res/config_mamstream.json"));
        // Act
        OracleConfig config = ConfigLoader.load();
        // Assert
        Assert.assertEquals("SHYPXXS9WFWCPGXLSFWUGFSTZNJFOBKHCOOPWPUODUZ9MTUKSRQHETK99QGJYVURWDRDSNHCGGTOBZMRU", config.getAppAddressSeed());
        Assert.assertEquals("IOTAISLIFE3", config.getPromotionTag());
        Assert.assertEquals(InputMethod.MAMSTREAM, config.getInputMethod());
        Assert.assertEquals(ValueType.STRING, config.getInputConfig().getValueType());

        Assert.assertTrue(config.getInputConfig() instanceof MamStreamInputConfig);

        MamStreamInputConfig inputConfig = (MamStreamInputConfig)config.getInputConfig();

        Assert.assertEquals(3050, inputConfig.getServicePort());
        Assert.assertEquals(60, inputConfig.getServicePollingInterval());
        Assert.assertEquals("https://nodes.devnet.thetangle.org:443", inputConfig.getNodeProviderUrl());
        Assert.assertEquals(MAMMode.RESTRICTED, inputConfig.getMode());
        Assert.assertEquals("KMTJV9ZFBAURYLFLMUHJNTNO9EBYERXJUNNSMDWMBIUFUETDCSCQZLKBJWKTFNJLSNXJUCKCLUKHWKFYJ", inputConfig.getRootAddress());
        Assert.assertEquals("RAREYODAPEPE", inputConfig.getEncryptionKey());

        // Asserts that the queries queue from the config is immutable!
        Assert.assertEquals(JsonPath.compile("$[?(@.data.Measurement=='Temperature')].data.Value").getPath(), inputConfig.getValueQueries().poll().getPath());
        Assert.assertEquals(JsonPath.compile("$[?(@.data.Measurement=='Temperature')].data.Value").getPath(), inputConfig.getValueQueries().poll().getPath());
        Assert.assertEquals(2, inputConfig.getValueQueries().size());

        Queue<JsonPath> queries = inputConfig.getValueQueries();
        Assert.assertEquals(JsonPath.compile("$[?(@.data.Measurement=='Temperature')].data.Value").getPath(), queries.poll().getPath());
        Assert.assertEquals(JsonPath.compile("$[-1]").getPath(), queries.poll().getPath());
        Assert.assertEquals(0, queries.size());
    }
}
