package ch.hslu.demo;

import oracle.OracleManager;
import oracle.OracleWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import qubic.QubicReader;
import tangle.QubicPromotion;

public class OracleApplication {
    private final static Logger LOGGER = LogManager.getLogger(OracleApplication.class);
    private final static String KEYWORD = "IOTAISLIFE1";
    private String name;

    public OracleApplication(String name){
        this.name = name;
        this.StartOracle();
    }

    private void StartOracle(){
        LOGGER.info("Create Oracle " + this.name);
        String qubicId = this.FindQubicId();
        QubicReader qubicReader = new QubicReader(qubicId);
        OracleWriter oracleWriter = new OracleWriter(qubicReader, "Oracle " + this.name);
        LOGGER.info("Oracle ID (IAM Identity): " + oracleWriter.getID());
        OracleManager oracleManager = new OracleManager(oracleWriter, "OracleManager " + this.name);
        LOGGER.info("Start Oracle Lifecycle");
        oracleManager.start();
    }

    private String FindQubicId(){
        String qubicId = QubicPromotion.GetQubicAddressesByKeyword(this.KEYWORD).get(0);
        LOGGER.info("Qubic address found: " + qubicId);
        return qubicId;
    }
}
