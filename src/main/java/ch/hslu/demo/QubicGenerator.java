package ch.hslu.demo;

import constants.GeneralConstants;
import constants.TangleJSONConstants;
import org.json.JSONObject;
import qubic.QubicSpecification;

public class QubicGenerator {

    public QubicGenerator(){
    }

    public void GenerateNewQubic(String code, int executionStart){
        QubicSpecification qubicSpecification = this.CreateQubicSpecification(code, executionStart);
    }

    private QubicSpecification CreateQubicSpecification(String code, int executionStart){
        JSONObject jsonQubic = new JSONObject();
        jsonQubic.put(TangleJSONConstants.TRANSACTION_TYPE, "qubic transaction");
        jsonQubic.put(TangleJSONConstants.VERSION, GeneralConstants.VERSION);
        jsonQubic.put(TangleJSONConstants.QUBIC_CODE, code);
        jsonQubic.put(TangleJSONConstants.QUBIC_EXECUTION_START, executionStart);
        jsonQubic.put(TangleJSONConstants.QUBIC_HASH_PERIOD_DURATION, 30);
        jsonQubic.put(TangleJSONConstants.QUBIC_RESULT_PERIOD_DURATION, 30);
        jsonQubic.put(TangleJSONConstants.QUBIC_RUN_TIME_LIMIT, 10);
        return new QubicSpecification(jsonQubic);
    }


}
