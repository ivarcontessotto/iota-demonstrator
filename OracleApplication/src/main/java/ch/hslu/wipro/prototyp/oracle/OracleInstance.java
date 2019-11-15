package ch.hslu.wipro.prototyp.oracle;

import oracle.OracleManager;
import oracle.OracleWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import qubic.QubicReader;
import tangle.QubicPromotion;
import tangle.TangleAPI;
import tangle.TryteTool;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class OracleInstance{
    private final static Logger LOGGER = LogManager.getLogger(OracleApplication.class);
    private final static String KEYWORD = "IOTAISLIFE3";
    private String name;

    public OracleInstance(String name){
        this.name = name;
        this.StartOracle();
    }

    private void StartOracle(){
        LOGGER.info("Generat Root Address for Test");
        String rootAddressForTest = TangleAPI.getInstance().getNextUnspentAddressFromSeed(TryteTool.TEST_SEED);
        LOGGER.info(rootAddressForTest);

        List<String> promotedQubics = QubicPromotion.GetQubicAddressesByKeyword(KEYWORD);

        QubicReader qubicReader;
        if (promotedQubics.size() > 0) {
            String firstPromotedQubicId = promotedQubics.get(0);
            LOGGER.info("First Promoted Qubic found: " + firstPromotedQubicId + " for Oracle " + name);
            qubicReader = new QubicReader(firstPromotedQubicId);
        }
        else {
            LOGGER.info("No promoted Qubics found for Oracle " + name);
            return;
        }

        Path argsFilePath = Paths.get("argsfile" + name + ".txt");
        LOGGER.info("Create Args File: " + argsFilePath.toAbsolutePath().toString());
        createArgsFile(argsFilePath, Arrays.asList(50));

        LOGGER.info("Create Oracle " + name);
        OracleWriter oracleWriter = new OracleWriter(rootAddressForTest, qubicReader, argsFilePath, "Oracle" + name);
        LOGGER.info("Oracle ID (IAM Identity): " + oracleWriter.getID());
        OracleManager oracleManager = new OracleManager(oracleWriter, "OracleManager" + name);
        LOGGER.info("Start Oracle Lifecycle");
        oracleManager.start();
    }

    private static void createArgsFile(Path argsFilePath, List<Integer> argsList) {
        File argsFile = argsFilePath.toFile();
        if (argsFile.exists()) {
            argsFile.delete();
        }

        try (PrintWriter out = new PrintWriter(argsFilePath.toFile())) {
            argsList.forEach(a -> out.println(a.toString()));
        } catch (IOException e) {
            LOGGER.error("Could not create new argsfile", e);

        }
    }
}

