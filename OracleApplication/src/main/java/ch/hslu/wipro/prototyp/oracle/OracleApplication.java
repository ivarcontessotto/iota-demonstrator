package ch.hslu.wipro.prototyp.oracle;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import tangle.TangleAPI;
import tangle.TryteTool;

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
    public static void main(final String[] args) {
        LOGGER.info("Initiate new Oracles");

        new Thread(OracleInstance::new).start();

        while (true) {
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
