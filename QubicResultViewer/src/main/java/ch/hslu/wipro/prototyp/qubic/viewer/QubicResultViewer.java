package ch.hslu.wipro.prototyp.qubic.viewer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class QubicResultViewer {
    private static final Logger LOGGER = LogManager.getLogger(QubicResultViewer.class);

    /**
     * Privater Konstruktor.
     */
    private QubicResultViewer() {
    }

    /**
     * Main-Methode.
     * @param args Startargumente.
     */
    public static void main(final String[] args) {
        LOGGER.info("Initiate new result viewer");

        new Thread(QubicResultViewerInstance::new).start();

        while (true) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
