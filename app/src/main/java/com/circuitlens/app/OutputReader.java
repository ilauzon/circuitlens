package com.circuitlens.app;

/**
 * Calculates boolean functions based off of an array of fundamental (NOT, OR, AND) Gate objects.
 */
public class OutputReader extends Gate {

    public OutputReader(Gate input) {
        maxInputs = 1;
        minInputs = 1;

        inputs = new Gate[]{input};

        //only one input ever, doesn't matter the index passed into inputs[].
        functionString = traceOutputFormula(inputs[0]);
    }

    /**
     * Recursive method that converts an n-ary tree of gates to a boolean expression.
     * @param gate the one gate that is an input to the OutputReader.
     * @return a String representing the boolean expression, with order of operations shown via parentheses.
     */
    public String traceOutputFormula(Gate gate) {
        String temp;

        if (gate instanceof Not) {
            temp = wrap(gate.functionString + traceOutputFormula(gate.inputs[0]));
        } else if (gate instanceof Variable){
            temp = wrap(gate.functionString);
        } else {
            temp = "(" + traceOutputFormula(gate.inputs[0]);

            for (int inputNum = 1; inputNum < gate.inputs.length; inputNum++) {
                temp += gate.functionString;
                temp += traceOutputFormula(gate.inputs[inputNum]);
            }
            temp += ")";
        }

        return temp;
    }

//    @Override
//    protected boolean calculateBool(boolean[] bits) {
//        return false;
//    }

    private String wrap(String s) {
        return "(" + s + ")";
    }
}
