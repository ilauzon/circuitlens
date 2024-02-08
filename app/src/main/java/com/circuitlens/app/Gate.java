package com.circuitlens.app;

import java.util.ArrayList;
import java.util.List;

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
