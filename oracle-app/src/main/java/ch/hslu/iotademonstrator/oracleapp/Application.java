package ch.hslu.iotademonstrator.oracleapp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class Application {

    private static final Logger LOGGER = LogManager.getLogger(Application.class);

    public static void main(final String[] args) {
        LOGGER.info("Start oracle application");
        OracleRunner runner = new OracleRunner();
        runner.start();
    }
}
