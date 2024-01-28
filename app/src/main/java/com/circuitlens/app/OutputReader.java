package com.circuitlens.app;

/**
 * Calculates boolean functions based off of an array of fundamental (NOT, OR, AND) Gate objects.
 */
public class OutputReader extends Gate {

    public OutputReader(Gate input) {
        maxInputs = 1;
        minInputs = 1;

        inputs = new Gate[]{input};

        functionString = traceOutputFormula(inputs[0]);
    }

    public String traceOutputFormula(Gate gate) {
        String temp;

        // if it is a NOT gate
        if (gate.inputs.length == 1) {
            temp = wrap(gate.functionString + traceOutputFormula(gate.inputs[0]));
        }
        // if it is an AND or OR gate
        else if (gate.inputs.length == 2) {
            temp = wrap(traceOutputFormula(gate.inputs[0]) + gate.functionString + traceOutputFormula(gate.inputs[1]));
        }
        // if it is a variable
        else {
            temp = gate.functionString;
        }
        return temp;
    }

    @Override
    protected boolean calculateBool(boolean[] bits) {
        return false;
    }

    private String wrap(String s) {
        return "(" + s + ")";
    }
}
