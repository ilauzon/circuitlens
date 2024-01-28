package com.circuitlens.app;

/**
 * represents a fundamental AND gate.
 */
public class And extends Gate {

    public And(Gate... inputs) {
        maxInputs = MAX_INPUTS;
        minInputs = 2;
        functionString += '&';
        this.inputs = inputs;

        checkInputAmount();
    }

    protected boolean calculateBool(boolean[] bits) {

        for (boolean bit : bits) {
            if (!bit) return false;
        }
        return true;
    }
}
