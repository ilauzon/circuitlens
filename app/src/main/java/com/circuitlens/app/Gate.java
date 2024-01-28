package com.circuitlens.app;

/**
 * The template for a logical gate. A gate is defined to have a single output and can have any
 * amount of inputs.
 */
public abstract class Gate {
    protected Gate[] inputs = new Gate[0];

    public static final int MAX_INPUTS = 31;

    protected String functionString = "";

    protected int maxInputs;
    protected int minInputs;

    protected boolean[] truthTable = new boolean[0];

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

            // convert chars 1 and 0 to true and false
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

        if (inputs.length + 1 > maxInputs) {
            throw new IllegalArgumentException("More inputs than max inputs");
        }
        Gate[] temp = new Gate[inputs.length + 1];
        System.arraycopy(inputs, 0, temp, 0, inputs.length);
        temp[temp.length - 1] = gate;
        inputs = temp;

        return temp.length - 1;
    }

    public void removeInput(int pos) {
        if (pos > inputs.length - 1) {
            throw new IllegalArgumentException("position greater than number of inputs");
        }
        System.arraycopy(inputs, pos + 1, inputs, pos, inputs.length - pos);
        checkInputAmount();
    }

    public Gate[] getInputs() {
        return inputs;
    }

    protected void checkInputAmount() throws IllegalArgumentException {
        if (inputs.length < minInputs || inputs.length > maxInputs) {
            throw new IllegalArgumentException("Incorrect number of gates provided, " +  inputs.length
                    + " gates provided outside range " + minInputs + " to " + maxInputs);
        }
    }

    public String getFunctionString() {
        return functionString;
    }
}
