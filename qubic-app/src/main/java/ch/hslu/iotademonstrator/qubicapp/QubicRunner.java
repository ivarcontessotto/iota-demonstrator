package ch.hslu.iotademonstrator.qubicapp;

import org.qubiclite.qlite.constants.TangleJSONConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.qubiclite.qlite.qubic.EditableQubicSpecification;
import org.qubiclite.qlite.qubic.QubicWriter;
import org.qubiclite.qlite.tangle.TangleAPI;
import org.qubiclite.qlite.tangle.TryteTool;

import java.util.List;

public class QubicRunner {

    private final static Logger LOGGER = LogManager.getLogger(Application.class);
    private final static String KEYWORD = "IOTAISLIFE3";

    public void start(){

        LOGGER.info("Generat Root Address for Test");
        String rootAddressForTest = TangleAPI.getInstance().getNextUnspentAddressFromSeed(TryteTool.TEST_SEED);
        LOGGER.info(rootAddressForTest);

        int secondsToExecutionStart = 120;
        int secondsUntilAssemble = 90;
        int secondsResultPeriod = 30;
        int secondsHashPeriod = 30;
        int secondsRuntimeLimit = 10;

        LOGGER.info("Create Qubic");
        QubicWriter qubicWriter = new QubicWriter(rootAddressForTest);
        EditableQubicSpecification specification = qubicWriter.getEditable();
        specification.setExecutionStartToSecondsInFuture(secondsToExecutionStart);
        specification.setResultPeriodDuration(secondsResultPeriod);
        specification.setHashPeriodDuration(secondsHashPeriod);
        specification.setRuntimeLimit(secondsRuntimeLimit);
        specification.setCode("kmh=GetArgs(0);if(kmh<=10){traffic='stau';}else{traffic='normal';}return(traffic);");


        LOGGER.info("Publish Qubic Transaction to Tangle Address: " + rootAddressForTest);
        qubicWriter.publishQubicTransaction();
        String qubicId = qubicWriter.getID();
        LOGGER.info("Qubic ID (IAM Identity): " + qubicId);
        LOGGER.info("Qubic Transaction Hash: " + qubicWriter.getQubicTransactionHash());

        LOGGER.info("Promote Qubic");
        qubicWriter.promote(rootAddressForTest, this.KEYWORD);

        LOGGER.info("Wait for Oracles to Subscribe");
        try {
            Thread.sleep(secondsUntilAssemble * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LOGGER.info("Fetch Application");
        List<JSONObject> applications = qubicWriter.fetchApplications();
        if (applications.size() == 0) {
            LOGGER.info("No Applications found");
        }

        for (JSONObject application : applications) {
            String oracleID = application.getString(TangleJSONConstants.ORACLE_ID);
            LOGGER.info("Add Oracle to Assembly: " + oracleID);
            qubicWriter.getAssembly().add(oracleID);
        }

        LOGGER.info("Publish Assembly Transaction");
        qubicWriter.publishAssemblyTransaction();
        LOGGER.info("Assembly Transaction Hash: " + qubicWriter.getAssemblyTransactionHash());
    }
}
