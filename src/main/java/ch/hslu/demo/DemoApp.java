/*
 * Copyright 2016 Roland Gisler
 * Hochschule Luzern Informatik, Switzerland
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.hslu.demo;

import oracle.OracleManager;
import oracle.OracleWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import qubic.EditableQubicSpecification;
import qubic.QubicReader;
import qubic.QubicWriter;

import java.util.List;

public final class DemoApp {

    private static final Logger LOGGER = LogManager.getLogger(DemoApp.class);

    /** X-Wert. Beispiel. */
    private static final int COR_X = 2;

    /** Y-Wert. Beispiel. */
    private static final int COR_Y = -1;

    /**
     * Privater Konstruktor.
     */
    private DemoApp() {
    }

    /**
     * Main-Methode.
     * @param args Startargumente.
     */
    public static void main(final String[] args) throws InterruptedException {
        LOGGER.info("IOTA Demonstrator start");

        QubicWriter qubicWriter = new QubicWriter();
        EditableQubicSpecification eqs = qubicWriter.getEditable();

        final int runtimeLimit = 9;
        final int hashPeriodDuration = 17;
        final String code = "return(33);";

        eqs.setRuntimeLimit(runtimeLimit);
        eqs.setHashPeriodDuration(hashPeriodDuration);
        eqs.setCode(code);

        qubicWriter.publishQubicTransaction();
        String qubicTransactionHash = qubicWriter.getQubicTransactionHash();
        String qubicId = qubicWriter.getID();
        QubicReader qubicReader = new QubicReader(qubicId);
        OracleWriter oracleWriter = new OracleWriter(qubicReader);


        OracleManager om = new OracleManager(oracleWriter);
        om.start();

        List<JSONObject> applicants = qubicWriter.fetchApplications();
        while(applicants.isEmpty()) {
            Thread.sleep(1000);
            applicants = qubicWriter.fetchApplications();
        }

        Thread.sleep(5000);
        LOGGER.info("IOTA Demonstrator finished");
    }
}
