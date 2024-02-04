package com.circuitlens.app;

/**
 * Represents a fundamental NOT Gate.
 */
public class Not extends Gate {

    public Not(Gate... inputs) {
        maxInputs = 1;
        minInputs = 1;
        functionString = "!";
        this.inputs = inputs;

        checkInputAmount();
    }

//    protected boolean calculateBool(boolean[] bits) {
//        return !bits[0];
//    }
}
