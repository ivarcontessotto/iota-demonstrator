package ch.hslu.iotademonstrator.qubicviewer.config;

public class QubicViewerConfig {

    private final String promotionTag;

    public QubicViewerConfig(
            String promotionTag
    ) {
        this.promotionTag = promotionTag;
    }

    public String getPromotionTag() {
        return this.promotionTag;
    }
}
