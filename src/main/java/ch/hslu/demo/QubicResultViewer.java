package ch.hslu.demo;

import oracle.QuorumBasedResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import qlvm.InterQubicResultFetcher;
import qubic.QubicReader;
import tangle.QubicPromotion;

public class QubicResultViewer {
    private final static Logger LOGGER = LogManager.getLogger(QubicResultViewer.class);
    private final static String KEYWORD = "IOTAISLIFE1";

    public QubicResultViewer(){
        this.StartResultViewer();
    }

    private void StartResultViewer(){
        String qubicId = this.FindQubicId();
        QubicReader qubicReader = new QubicReader(qubicId);
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

            LOGGER.info("Fetch Quorum Based Result");
            QuorumBasedResult quorumBasedResult = InterQubicResultFetcher.fetchResultConsensus(qubicId, epoch);

            double quorum = quorumBasedResult.getQuorum();
            double quorumMax = quorumBasedResult.getQuorumMax();
            double percentage = Math.round(1000 * quorum / quorumMax) / 10;

            LOGGER.info("EPOCH: " + epoch +
                    ", RESULT: " + quorumBasedResult.getResult() +
                    ", QUORUM: "  +  quorum + " / " + quorumMax + " ("+percentage+"%)" );
        }
    }

    private String FindQubicId(){
        String qubicId = QubicPromotion.GetQubicAddressesByKeyword(this.KEYWORD).get(0);
        LOGGER.info("Qubic address found: " + qubicId);
        return qubicId;
    }
}
