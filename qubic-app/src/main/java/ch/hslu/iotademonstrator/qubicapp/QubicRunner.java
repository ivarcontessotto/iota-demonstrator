package ch.hslu.iotademonstrator.qubicapp;

import ch.hslu.iotademonstrator.qubicapp.config.ConfigLoader;
import ch.hslu.iotademonstrator.qubicapp.config.QubicConfig;
import org.qubiclite.qlite.constants.TangleJSONConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.qubiclite.qlite.qubic.EditableQubicSpecification;
import org.qubiclite.qlite.qubic.QubicWriter;
import org.qubiclite.qlite.tangle.TangleAPI;

import java.util.List;

public class QubicRunner {

    private final Logger logger = LogManager.getLogger(QubicRunner.class);
    private final QubicConfig config;

    public QubicRunner() {
        this.config = ConfigLoader.load();
    }

    public void start(){

        logger.info("Generate application's address");
        String rootAddressForTest = TangleAPI.getInstance().getNextUnspentAddressFromSeed(this.config.getAppAddressSeed());
        logger.info(rootAddressForTest);

        logger.info("Create Qubic");
        QubicWriter qubicWriter = new QubicWriter(rootAddressForTest);
        EditableQubicSpecification specification = qubicWriter.getEditable();
        specification.setExecutionStartToSecondsInFuture(this.config.getSecondsUntilExecution());
        specification.setResultPeriodDuration(this.config.getSecondsResultPeriod());
        specification.setHashPeriodDuration(this.config.getSecondsHashPeriod());
        specification.setRuntimeLimit(this.config.getSecondsRuntimeLimit());
        specification.setCode(this.config.getCode());

        logger.info("Publish Qubic Transaction to Tangle Address: " + rootAddressForTest);
        qubicWriter.publishQubicTransaction();
        String qubicId = qubicWriter.getID();
        logger.info("Qubic ID (IAM Identity): " + qubicId);
        logger.info("Qubic Transaction Hash: " + qubicWriter.getQubicTransactionHash());

        logger.info("Promote Qubic");
        qubicWriter.promote(rootAddressForTest, this.config.getPromotionTag());

        logger.info("Wait for Oracles to Subscribe");
        try {
            Thread.sleep(this.config.getSecondsUntilAssemble() * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        logger.info("Fetch Application");
        List<JSONObject> applications = qubicWriter.fetchApplications();
        if (applications.size() == 0) {
            logger.info("No Applications found");
        }

        for (JSONObject application : applications) {
            String oracleID = application.getString(TangleJSONConstants.ORACLE_ID);
            logger.info("Add Oracle to Assembly: " + oracleID);
            qubicWriter.getAssembly().add(oracleID);
        }

        logger.info("Publish Assembly Transaction");
        qubicWriter.publishAssemblyTransaction();
        logger.info("Assembly Transaction Hash: " + qubicWriter.getAssemblyTransactionHash());
    }
}
