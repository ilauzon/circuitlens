package com.circuitlens.app;

public abstract class Gate {
    protected Gate[] inputs, outputs;

    protected int maxInputs;

    protected boolean[] truthTable;

    protected void calculateTruthTable() {
        if (inputs.length > maxInputs) {
            throw new IllegalArgumentException("Too many inputs");
        }
        truthTable = new boolean[(int) Math.pow(2, inputs.length)];
        for (int i = 0; i < truthTable.length; i++) {
            truthTable[i] = calculateBool(i);
        }
    }

    protected abstract boolean calculateBool(int i);

    public boolean getValue(int row) {
        return truthTable[row];
    }

    public int addInput(Gate gate) {
        Gate[] temp = new Gate[inputs.length + 1];
        System.arraycopy(inputs, 0, temp, 0, inputs.length);
        temp[temp.length - 1] = gate;
        inputs = temp;

        calculateTruthTable();

        return temp.length - 1;
    }

    public int addOutput(Gate gate) {
        Gate[] temp = new Gate[inputs.length + 1];
        System.arraycopy(inputs, 0, temp, 0, inputs.length);
        temp[temp.length - 1] = gate;
        inputs = temp;

        calculateTruthTable();

        return temp.length - 1;
    }

    public void removeInput(int pos) {
        System.arraycopy(inputs, pos + 1, inputs, pos, inputs.length - pos);

        calculateTruthTable();
    }

    public void removeOutput(int pos) {
        System.arraycopy(outputs, pos + 1, outputs, pos, outputs.length - pos);

        calculateTruthTable();
    }
}
