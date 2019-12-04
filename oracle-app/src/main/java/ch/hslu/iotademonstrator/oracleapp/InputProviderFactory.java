package ch.hslu.iotademonstrator.oracleapp;

import ch.hslu.iotademonstrator.oracleapp.config.OracleConfig;
import ch.hslu.iotademonstrator.oracleapp.inputprovider.LogfileInputProvider;
import ch.hslu.iotademonstrator.oracleapp.inputprovider.MamStreamInputProvider;
import ch.hslu.iotademonstrator.oracleapp.inputprovider.WebServiceInputProvider;
import ch.hslu.iotademonstrator.oracleapp.inputprovider.config.LogfileInputConfig;
import ch.hslu.iotademonstrator.oracleapp.inputprovider.config.MamStreamInputConfig;
import ch.hslu.iotademonstrator.oracleapp.inputprovider.config.WebServiceInputConfig;
import org.qubiclite.qlite.oracle.input.OracleInputProvider;
import org.qubiclite.qlite.oracle.input.QueueInputConfig;
import org.qubiclite.qlite.oracle.input.QueueInputProvider;

public class InputProviderFactory {

    static OracleInputProvider getInputProvider(OracleConfig oracleConfig) {
        try {
            switch (oracleConfig.getInputMethod()) {
                case QUEUE:
                    return new QueueInputProvider((QueueInputConfig)oracleConfig.getInputConfig());
                case LOGFILE:
                    return new LogfileInputProvider((LogfileInputConfig)oracleConfig.getInputConfig());
                case WEBSERVICE:
                    return new WebServiceInputProvider((WebServiceInputConfig)oracleConfig.getInputConfig());
                case MAMSTREAM:
                    return new MamStreamInputProvider((MamStreamInputConfig)oracleConfig.getInputConfig());
                default:
                    throw new IllegalArgumentException("Configured oracle input method not supported.");
            }
        } catch (ClassCastException e) {
            throw new IllegalStateException("Oracle configuration is invalid.", e);
        }
    }
}
