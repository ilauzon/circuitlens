package com.circuitlens.app;

public class And extends Gate {

    public And() {
        maxInputs = (int) Math.pow(2, 16);
    }

    protected boolean calculateBool(boolean[] bits) {

        for (boolean bit : bits) {
            if (!bit) return false;
        }
        return true;
    }
}
