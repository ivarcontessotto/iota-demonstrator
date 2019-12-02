package ch.hslu.iotademonstrator.oracleapp;

import org.qubiclite.qlite.oracle.OracleManager;
import org.qubiclite.qlite.oracle.OracleWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.qubiclite.qlite.oracle.input.config.ValueType;
import org.qubiclite.qlite.oracle.input.provider.OracleInputProvider;
import org.qubiclite.qlite.qubic.QubicReader;
import org.qubiclite.qlite.tangle.QubicPromotion;
import org.qubiclite.qlite.tangle.TangleAPI;
import org.qubiclite.qlite.tangle.TryteTool;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class OracleRunner {

    private final static Logger LOGGER = LogManager.getLogger(Application.class);
    private final static String KEYWORD = "IOTAISLIFE3";

    public void start(){

        LOGGER.info("Generat Root Address for Test");
        String rootAddressForTest = TangleAPI.getInstance().getNextUnspentAddressFromSeed(TryteTool.TEST_SEED);
        LOGGER.info(rootAddressForTest);

        List<String> promotedQubics = QubicPromotion.GetQubicAddressesByKeyword(KEYWORD);

        QubicReader qubicReader;
        if (promotedQubics.size() > 0) {
            String firstPromotedQubicId = promotedQubics.get(0);
            LOGGER.info("First Promoted Qubic found: " + firstPromotedQubicId );
            qubicReader = new QubicReader(firstPromotedQubicId);
        }
        else {
            LOGGER.info("No promoted Qubics found ");
            return;
        }

        Queue<String> inputSequence = new LinkedList<>(Arrays.asList("50", "40", "30"));
        QueueInputProviderConfig inputConfig = new QueueInputProviderConfig(ValueType.INTEGER, inputSequence);
        LOGGER.info("Create Input Provider");
        OracleInputProvider inputProvider = new QueueInputProvider(inputConfig);

        LOGGER.info("Create Oracle");
        OracleWriter oracleWriter = new OracleWriter(rootAddressForTest, qubicReader, inputProvider);
        LOGGER.info("Oracle ID (IAM Identity): " + oracleWriter.getID());
        OracleManager oracleManager = new OracleManager(oracleWriter, "OracleManager");
        LOGGER.info("Start Oracle Lifecycle");
        oracleManager.start();
    }
}

