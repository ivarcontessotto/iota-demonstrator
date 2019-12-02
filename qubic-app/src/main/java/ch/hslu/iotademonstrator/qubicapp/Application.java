package ch.hslu.iotademonstrator.qubicapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Application {

    private static final Logger LOGGER = LogManager.getLogger(Application.class);

    public static void main(final String[] args) {
        LOGGER.info("Start qubic application");
        QubicRunner runner = new QubicRunner();
        runner.start();
    }
}
