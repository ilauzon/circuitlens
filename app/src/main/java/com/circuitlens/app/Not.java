package com.circuitlens.app;

public class Not extends Gate {

    public Not() {
        maxInputs = 1;
    }

    protected boolean calculateBool(boolean[] bits) {
        return !bits[0];
    }
}
