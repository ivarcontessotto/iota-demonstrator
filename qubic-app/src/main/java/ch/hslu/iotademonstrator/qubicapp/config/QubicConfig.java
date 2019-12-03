package ch.hslu.iotademonstrator.qubicapp.config;

public class QubicConfig {

    private final String appAddressSeed;
    private final String promotionTag;
    private final int secondsUntilExecution;
    private final int secondsUntilAssemble;
    private final int secondsHashPeriod;
    private final int secondsResultPeriod;
    private final int secondsRuntimeLimit;
    private final String code;

    public QubicConfig(
            String appAddressSeed,
            String promotionTag,
            int secondsUntilExecution,
            int secondsUntilAssemble,
            int secondsHashPeriod,
            int secondsResultPeriod,
            int secondsRuntimeLimit,
            String code
    ) {
        this.appAddressSeed = appAddressSeed;
        this.promotionTag = promotionTag;
        this.secondsUntilExecution = secondsUntilExecution;
        this.secondsUntilAssemble = secondsUntilAssemble;
        this.secondsHashPeriod = secondsHashPeriod;
        this.secondsResultPeriod = secondsResultPeriod;
        this.secondsRuntimeLimit = secondsRuntimeLimit;
        this.code = code;
    }

    public String getAppAddressSeed() {
        return this.appAddressSeed;
    }

    public String getPromotionTag() {
        return this.promotionTag;
    }

    public int getSecondsUntilExecution() {
        return this.secondsUntilExecution;
    }

    public int getSecondsUntilAssemble() {
        return this.secondsUntilAssemble;
    }

    public int getSecondsHashPeriod() {
        return this.secondsHashPeriod;
    }

    public int getSecondsResultPeriod() {
        return this.secondsResultPeriod;
    }

    public int getSecondsRuntimeLimit() {
        return this.secondsRuntimeLimit;
    }

    public String getCode() {
        return this.code;
    }
}