package com.circuitlens.app;

/**
 * represents a fundamental OR gate.
 */
public class Or extends Gate {

    public Or(Gate... inputs) {
        maxInputs = MAX_INPUTS;
        minInputs = 2;
        functionString += '|';
        this.inputs = inputs;

        checkInputAmount();
    }

    protected boolean calculateBool(boolean[] bits) {

        for (boolean bit : bits) {
            if (bit) return true;
        }
        return false;
    }
}
