package com.circuitlens.app;

public class And extends Gate {

    public And() {
        maxInputs = MAX_INPUTS;
    }

    protected boolean calculateBool(boolean[] bits) {

        for (boolean bit : bits) {
            if (!bit) return false;
        }
        return true;
    }
}
