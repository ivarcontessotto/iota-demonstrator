package ch.hslu.iotademonstrator.oracleapp;

import ch.hslu.iotademonstrator.oracleapp.config.ConfigLoader;
import ch.hslu.iotademonstrator.oracleapp.config.OracleConfig;
import org.qubiclite.qlite.oracle.OracleManager;
import org.qubiclite.qlite.oracle.OracleWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.qubiclite.qlite.oracle.input.*;
import org.qubiclite.qlite.qubic.QubicReader;
import org.qubiclite.qlite.tangle.QubicPromotion;
import org.qubiclite.qlite.tangle.TangleAPI;
import org.qubiclite.qlite.tangle.TryteTool;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OracleRunner {

    private final Logger logger = LogManager.getLogger(OracleRunner.class);
    private final OracleConfig config;

    public OracleRunner() {
        this.config = ConfigLoader.load();
    }

    public void start(){

        logger.info("Generat Root Address for Test");
        String rootAddressForTest = TangleAPI.getInstance().getNextUnspentAddressFromSeed(this.config.getAppAddressSeed());
        logger.info(rootAddressForTest);

        List<String> promotedQubics = QubicPromotion.GetQubicAddressesByKeyword(this.config.getPromotionTag());

        QubicReader qubicReader;
        if (promotedQubics.size() > 0) {
            String firstPromotedQubicId = promotedQubics.get(0);
            logger.info("First Promoted Qubic found: " + firstPromotedQubicId );
            qubicReader = new QubicReader(firstPromotedQubicId);
        }
        else {
            logger.info("No promoted Qubics found ");
            return;
        }

        logger.info("Create Oracle Input Provider");
        OracleInputProvider inputProvider = InputProviderFactory.getInputProvider(this.config);

        logger.info("Create Oracle");
        OracleWriter oracleWriter = new OracleWriter(rootAddressForTest, qubicReader, inputProvider);
        logger.info("Oracle ID (IAM Identity): " + oracleWriter.getID());
        OracleManager oracleManager = new OracleManager(oracleWriter, "OracleManager");
        logger.info("Start Oracle Lifecycle");
        oracleManager.start();
    }
}

