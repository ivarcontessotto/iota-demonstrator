package ch.hslu.wipro.prototyp.qubic;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class QubicApplication {
    private static final Logger LOGGER = LogManager.getLogger(QubicApplication.class);

    /**
     * Privater Konstruktor.
     */
    private QubicApplication() {
    }

    /**
     * Main-Methode.
     * @param args Startargumente.
     */
    public static void main(final String[] args) throws InterruptedException {
        LOGGER.info("Initiate new qubic");

        new Thread(new Runnable() {
            public void run() {
                new QubicInstance();
            }
        }).start();

        while (true) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
