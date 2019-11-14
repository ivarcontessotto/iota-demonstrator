package ch.hslu.demo;

import constants.TangleJSONConstants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import qubic.EditableQubicSpecification;
import qubic.QubicReader;
import qubic.QubicSpecification;
import qubic.QubicWriter;
import tangle.TryteTool;

import java.util.List;

public class QubicApplication {
    private final static Logger LOGGER = LogManager.getLogger(QubicApplication.class);
    private final static String KEYWORD = "IOTAISLIFE1";

    public QubicApplication(){
        this.StartQubic();
    }

    private void StartQubic(){
        int secondsToExecutionStart = 70;
        int secondsUntilAssemble = 40;
        int secondsResultPeriod = 15;
        int secondsHashPeriod = 15;
        int secondsRuntimeLimit = 5;

        LOGGER.info("Create Qubic");
        QubicWriter qubicWriter = new QubicWriter();
        EditableQubicSpecification specification = qubicWriter.getEditable();
        specification.setExecutionStartToSecondsInFuture(secondsToExecutionStart);
        specification.setResultPeriodDuration(secondsResultPeriod);
        specification.setHashPeriodDuration(secondsHashPeriod);
        specification.setRuntimeLimit(secondsRuntimeLimit);
        specification.setCode("return(epoch^2);");

        LOGGER.info("Publish Qubic Transaction to Tangle Address: " + TryteTool.TEST_ADDRESS_1);
        qubicWriter.publishQubicTransaction();
        qubicWriter.promote(this.KEYWORD);
        String qubicId = qubicWriter.getID();
        LOGGER.info("Qubic ID (IAM Identity): " + qubicId);
        LOGGER.info("Qubic Transaction Hash: " + qubicWriter.getQubicTransactionHash());

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

        QubicReader qubicReader = new QubicReader(qubicId);
        LOGGER.info("Read Assembly List");
        for (String oracleId : qubicReader.getAssemblyList()) {
            LOGGER.info("Oracle Part of Assembly: " + oracleId);
        }
    }
}
