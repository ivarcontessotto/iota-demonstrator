package ch.hslu.wipro.prototyp.oracle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class OracleApplication {
    private static final Logger LOGGER = LogManager.getLogger(OracleApplication.class);

    /**
     * Privater Konstruktor.
     */
    private OracleApplication() {
    }

    /**
     * Main-Methode.
     * @param args Startargumente.
     */
    public static void main(final String[] args) throws InterruptedException {
        LOGGER.info("Initiate new qubic");

        new Thread(new Runnable() {
            public void run() {
                new OracleInstance("Fabian");
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
