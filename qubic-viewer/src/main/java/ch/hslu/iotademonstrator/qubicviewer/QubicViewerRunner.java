package ch.hslu.iotademonstrator.qubicviewer;

import ch.hslu.iotademonstrator.qubicviewer.config.ConfigLoader;
import ch.hslu.iotademonstrator.qubicviewer.config.QubicViewerConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.qubiclite.qlite.oracle.QuorumBasedResult;
import org.qubiclite.qlite.qlvm.InterQubicResultFetcher;
import org.qubiclite.qlite.qubic.QubicReader;
import org.qubiclite.qlite.tangle.QubicPromotion;

import java.util.List;

public class QubicViewerRunner {

    private final Logger logger = LogManager.getLogger(QubicViewerRunner.class);
    private final QubicViewerConfig config;

    public QubicViewerRunner() {
        this.config = ConfigLoader.load();
    }

    public void start() {

        List<String> promotedQubics = QubicPromotion.GetQubicAddressesByKeyword(this.config.getPromotionTag());

        QubicReader qubicReader;
        if (promotedQubics.size() > 0) {
            String firstPromotedQubicId = promotedQubics.get(0);
            logger.info("First Promoted Qubic found: " + firstPromotedQubicId);
            qubicReader = new QubicReader(firstPromotedQubicId);
        }
        else {
            logger.info("No promoted Qubics found");
            return;
        }

        logger.info("Wait for the qubic to start");
        while (qubicReader.lastCompletedEpoch() == -1) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        logger.info("Read Assembly List");
        for (String oracleId : qubicReader.getAssemblyList()) {
            logger.info("Oracle Part of Assembly: " + oracleId);
        }

        for (int epoch = 0; epoch < Integer.MAX_VALUE; epoch++) {
            logger.info("Waiting for Epoch " + epoch + " to complete");
            int lastCompletedEpoch = qubicReader.lastCompletedEpoch();
            while (lastCompletedEpoch < epoch) {
                try {
                    Thread.sleep(1000);
                    lastCompletedEpoch = qubicReader.lastCompletedEpoch();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            logger.info("Epoch " + epoch + " completed");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.info("Fetch Quorum Based Result");
            QuorumBasedResult quorumBasedResult = InterQubicResultFetcher.fetchResultConsensus(qubicReader.getID(), epoch);

            double quorum = quorumBasedResult.getQuorum();
            double quorumMax = quorumBasedResult.getQuorumMax();
            double percentage = Math.round(1000 * quorum / quorumMax) / 10;

            logger.info("EPOCH: " + epoch +
                    ", RESULT: " + quorumBasedResult.getResult() +
                    ", QUORUM: "  +  quorum + " / " + quorumMax + " ("+percentage+"%)" );
        }
    }
}
