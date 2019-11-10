package ch.hslu.demo;

public class QubicLiteDemo {

    private QubicGenerator qubicGenerator;

    public QubicLiteDemo(){
        this.qubicGenerator = new QubicGenerator();
    }

    public void CreateNewQubic(){
        String code = "return(epoch^2)";
        int executionStart = (int)(System.currentTimeMillis()/1000) + 180;
        this.qubicGenerator.GenerateNewQubic(code, executionStart);
    }
}
