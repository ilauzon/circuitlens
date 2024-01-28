package com.circuitlens.app;

public class Or extends Gate {

    public Or() {
        maxInputs = MAX_INPUTS;
    }

    protected boolean calculateBool(boolean[] bits) {

        for (boolean bit : bits) {
            if (bit) return true;
        }
        return false;
    }
}
