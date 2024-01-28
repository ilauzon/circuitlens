package com.circuitlens.app;

public class Constant extends Gate {
    public final boolean value;

    public Constant(boolean b) {
        value = b;
        maxInputs = 0;
    }

    public Constant(int i) {
        value = i == 1;
        maxInputs = 0;
    }
    @Override
    protected boolean calculateBool(boolean[] bits) {
        return value;
    }

}
