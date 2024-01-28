package com.circuitlens.app;

public abstract class Gate {
    protected Gate[] inputs, outputs;

    protected int maxInputs;

    protected boolean[] truthTable;

    public void calculateTruthTable() {
        if (inputs.length > maxInputs) {
            throw new IllegalArgumentException("More inputs than max inputs");
        }

        truthTable = new boolean[(int) Math.pow(2, inputs.length)];

        //repeat for each "line" in the truth table
        for (int i = 0; i < truthTable.length; i++) {
            char[] bitChars = Integer.toBinaryString(i).toCharArray();

            //pad with leading 0s
            while (bitChars.length < inputs.length) {
                char[] temp = new char[bitChars.length + 1];
                System.arraycopy(bitChars, 0, temp, 1, bitChars.length);
                temp[0] = '0';
                bitChars = temp;
            }

            boolean[] bits = new boolean[bitChars.length];

            for (int j = 0; j < bitChars.length; j++) {
                bits[j] = bitChars[bitChars.length - 1 - j] == '1';
            }

            truthTable[i] = calculateBool(bits);
        }
    }

    protected abstract boolean calculateBool(boolean[] bits);

    public boolean getValue(int row) {
        return truthTable[row];
    }

    public boolean[] getTruthTable() {
        boolean[] temp = new boolean[truthTable.length];
        System.arraycopy(truthTable, 0, temp, 0, truthTable.length);
        return temp;
    }

    public int addInput(Gate gate) {
        if (inputs == null) {
            inputs = new Gate[0];
        }
        if (inputs.length + 1 > maxInputs) {
            throw new IllegalArgumentException("More inputs than max inputs");
        }
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
