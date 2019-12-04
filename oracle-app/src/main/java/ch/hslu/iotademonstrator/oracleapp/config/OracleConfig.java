package ch.hslu.iotademonstrator.oracleapp.config;

import org.qubiclite.qlite.oracle.input.OracleInputConfig;

public class OracleConfig {

    private final String appAddressSeed;
    private final String promotionTag;
    private final InputMethod inputMethod;
    private final OracleInputConfig inputConfig;

    public OracleConfig(
            String appAddressSeed,
            String promotionTag,
            InputMethod inputMethod,
            OracleInputConfig inputConfig
    ) {
        this.appAddressSeed = appAddressSeed;
        this.promotionTag = promotionTag;
        this.inputMethod = inputMethod;
        this.inputConfig = inputConfig;
    }

    public String getAppAddressSeed() {
        return this.appAddressSeed;
    }

    public String getPromotionTag() {
        return this.promotionTag;
    }

    public InputMethod getInputMethod() {
        return this.inputMethod;
    }

    public OracleInputConfig getInputConfig() {
        return this.inputConfig;
    }
}
