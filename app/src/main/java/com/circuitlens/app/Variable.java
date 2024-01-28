package com.circuitlens.app;


public class Variable extends Gate {

    public Variable(String functionString) {
        this.functionString = functionString;

        maxInputs = 0;
        minInputs = 0;
    }
    @Override
    protected boolean calculateBool(boolean[] bits) {
        return false;
    }
}
