package com.circuitlens.app;

import java.util.ArrayList;
import java.util.List;

/**
 * Calculates boolean functions based off of an array of fundamental (NOT, OR, AND) Gate objects.
 */
public class OutputReader extends Gate {
    private char[] vars = new char[0];

    public OutputReader(Gate input) {
        maxInputs = 1;
        minInputs = 1;

        inputs = new Gate[]{input};

        //only one input ever, doesn't matter the index passed into inputs[].
        functionString = traceOutputFormula(inputs[0]);
        vars = setVars();
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

    protected boolean[] truthTable = new boolean[0];

    public void calculateTruthTable() {
        if (inputs.length > maxInputs) {
            throw new IllegalArgumentException("More inputs than max inputs");
        }
        truthTable = new boolean[(int) Math.pow(2, Math.max(vars.length, 1))];

        //repeat for each "line" in the truth table
        for (int i = 0; i < truthTable.length; i++) {
            char[] bitChars = Integer.toBinaryString(i).toCharArray();

            //pad with leading 0s
            while (bitChars.length < vars.length) {
                char[] temp = new char[bitChars.length + 1];
                System.arraycopy(bitChars, 0, temp, 1, bitChars.length);
                temp[0] = '0';
                bitChars = temp;
            }

            boolean[] bits = new boolean[bitChars.length];

            // convert chars 1 and 0 to true and false
            for (int j = 0; j < bitChars.length; j++) {
                bits[j] = bitChars[bitChars.length - 1 - j] == '1';
            }
            truthTable[i] = calculateBool(bits);
        }
    }

    private char[] setVars() {
        for (char c : functionString.toCharArray()) {
            if (c >= 'A' && c <= 'Z' && getVarPosition(c) == -1) {
                char[] temp = new char[vars.length + 1];
                System.arraycopy(vars, 0, temp, 0, vars.length);
                temp[temp.length - 1] = c;
                vars = temp;
            }
        }
        return vars;
    }

    private int getVarPosition(char v) {
        for (int i = 0; i < vars.length; i++) {
            if (v == vars[i]) {
                return i;
            }
        }
        return -1;
    }

    public char getVarChar(int index) {
        return vars[index];
    }

    protected boolean calculateBool(boolean[] bits) {

        return calculateBoolRecursive(bits, functionString);
    }

    private boolean calculateBoolRecursive(boolean[] bits, String expression) {

        // if entirely wrapped, go down one level and look at what is inside parentheses
        if (isWrapped(expression)) {
            return calculateBoolRecursive(bits, expression.substring(1, expression.length() - 1));
        }

        // if variable, evaluate
        if (expression.matches("^[A-Za-z]$")) {
            return bits[getVarPosition(expression.toCharArray()[0])];
        }

        // Otherwise, assume that we are dealing with a string of operators and operands. Order of
        // operations does not matter since everything is wrapped in parentheses to indicate order.

        if (expression.charAt(0) == '!') {
            return !calculateBoolRecursive(bits, expression.substring(2, endParen(expression, 1)));
        } else {
            String[] operands = findOperands(expression);
            char operator = findOperator(expression);

            return evaluateInSeries(bits, operands, operator, 0);
        }
    }

    private boolean evaluateInSeries(boolean[] bits, String[] expressions, char operand, int firstOperand) {
        if (firstOperand == expressions.length - 1) {
            return calculateBoolRecursive(bits, expressions[firstOperand]);
        }

        if (operand == '&') {
            return calculateBoolRecursive(bits, expressions[firstOperand]) && evaluateInSeries(bits, expressions, operand, firstOperand + 1);
        } else if (operand == '|') {
            return calculateBoolRecursive(bits, expressions[firstOperand]) || evaluateInSeries(bits, expressions, operand, firstOperand + 1);
        } else {
            throw new IllegalArgumentException("Operand is neither & or |.");
        }
    }

    private static String[] findOperands(String expression) {
        List<String> operands = new ArrayList<>();

        int start = 0;

        while (start < expression.length()) {
            int end = endParen(expression, start);
            String operand = expression.substring(start + 1, end);
            operands.add(operand);
            start = endParen(expression, start) + 2;
        }

        String[] operandsArray = new String[operands.size()];
        return operands.toArray(operandsArray);
    }

    private static char findOperator(String expression) {
        //finds operator in series by looking at the character after the first set of parentheses.
        int firstOperatorIndex = endParen(expression, 0) + 1;
        return expression.charAt(firstOperatorIndex);
    }

    private static boolean isWrapped(String s) {
        if (s.length() < 2) {
            return false;
        }
        String input = s.substring(1, s.length() - 1);
        int nestingLevel = 0;

        for (char c : input.toCharArray()) {
            if (c == '(') {
                nestingLevel++;
            } else if (c == ')') {
                if (nestingLevel == 0) {
                    return false; // Unmatched closing parenthesis
                }
                nestingLevel--;
            }
        }
        return nestingLevel == 0;
    }

    private static int endParen(String s, int startParen) {
        if (s.charAt(startParen) != '(') {
            throw new IllegalArgumentException("Given index must point to starting parenthesis");
        }
        int c = startParen;
        int nestingLevel = 1;
        while(c < s.length()) {
            c++;
            if (s.charAt(c) == '(') {
                nestingLevel++;
            } else if (s.charAt(c) == ')') {
                nestingLevel--;
            }

            if (nestingLevel == 0) {
                return c;
            }
        }
        return -1;
    }


    public boolean getValue(int row) {
        return truthTable[row];
    }

    public boolean[] getTruthTable() {
        calculateTruthTable();
        boolean[] temp = new boolean[truthTable.length];
        System.arraycopy(truthTable, 0, temp, 0, truthTable.length);
        return temp;
    }

    public char[] getVarArray() {
        char[] temp = new char[vars.length];
        System.arraycopy(vars, 0, temp, 0, vars.length);
        return temp;
    }

    private String wrap(String s) {
        return "(" + s + ")";
    }
}
