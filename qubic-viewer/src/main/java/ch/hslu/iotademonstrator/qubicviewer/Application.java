package ch.hslu.iotademonstrator.qubicviewer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Application {

    private static final Logger LOGGER = LogManager.getLogger(Application.class);

    public static void main(final String[] args) {
        LOGGER.info("Start Qubic Viewer Application");
        QubicViewerRunner runner = new QubicViewerRunner();
        runner.start();
    }
}
