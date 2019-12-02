package ch.hslu.iotademonstrator.oracleapp;

import org.qubiclite.qlite.oracle.input.config.OracleInputConfig;
import org.qubiclite.qlite.oracle.input.config.ValueType;

import java.util.LinkedList;
import java.util.Queue;

public class QueueInputProviderConfig extends OracleInputConfig {

    private Queue<String> inputSequence;

    public QueueInputProviderConfig(ValueType valueType, Queue<String> inputSequence) {
        super(valueType);
        this.inputSequence = inputSequence;
    }

    public Queue<String> getInputSequence() {
        return new LinkedList<>(this.inputSequence);
    }
}
