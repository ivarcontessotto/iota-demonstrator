package ch.hslu.wipro.prototyp.qubic.viewer;

import oracle.QuorumBasedResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import qlvm.InterQubicResultFetcher;
import qubic.QubicReader;
import tangle.QubicPromotion;

import java.util.List;

public class QubicResultViewerInstance {
    private final static Logger LOGGER = LogManager.getLogger(QubicResultViewerInstance.class);
    private final static String KEYWORD = "IOTAISLIFE3";

    public QubicResultViewerInstance(){
        this.StartResultViewer();
    }

    private void StartResultViewer(){
        List<String> promotedQubics = QubicPromotion.GetQubicAddressesByKeyword(KEYWORD);

        QubicReader qubicReader;
        if (promotedQubics.size() > 0) {
            String firstPromotedQubicId = promotedQubics.get(0);
            LOGGER.info("First Promoted Qubic found: " + firstPromotedQubicId);
            qubicReader = new QubicReader(firstPromotedQubicId);
        }
        else {
            LOGGER.info("No promoted Qubics found");
            return;
        }

        LOGGER.info("Wait for the qubic to start");
        while (qubicReader.lastCompletedEpoch() == -1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        LOGGER.info("Read Assembly List");
        for (String oracleId : qubicReader.getAssemblyList()) {
            LOGGER.info("Oracle Part of Assembly: " + oracleId);
        }

        for (int epoch = 0; epoch < 10; epoch++) {
            LOGGER.info("Waiting for Epoch " + epoch + " to complete");
            while (qubicReader.lastCompletedEpoch() < epoch) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            LOGGER.info("Epoch " + epoch + " completed");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            LOGGER.info("Fetch Quorum Based Result");
            QuorumBasedResult quorumBasedResult = InterQubicResultFetcher.fetchResultConsensus(qubicReader.getID(), epoch);

            double quorum = quorumBasedResult.getQuorum();
            double quorumMax = quorumBasedResult.getQuorumMax();
            double percentage = Math.round(1000 * quorum / quorumMax) / 10;

            LOGGER.info("EPOCH: " + epoch +
                    ", RESULT: " + quorumBasedResult.getResult() +
                    ", QUORUM: "  +  quorum + " / " + quorumMax + " ("+percentage+"%)" );
        }
    }
}
