package com.circuitlens.app;

public class Or extends Gate {

    public Or() {
        maxInputs = (int) Math.pow(2, 16);
    }

    protected boolean calculateBool(boolean[] bits) {

        for (boolean bit : bits) {
            if (bit) return true;
        }
        return false;
    }
}
