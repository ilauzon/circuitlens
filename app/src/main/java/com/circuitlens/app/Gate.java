package com.circuitlens.app;

import java.util.ArrayList;
import java.util.List;

/**
 * The template for a logical gate. A gate is defined to have a single output and can have any
 * amount of inputs.
 */
public abstract class Gate {
    protected Gate[] inputs = new Gate[0];

    public static final int MAX_INPUTS = 31;

    protected String functionString = "";

    protected int maxInputs;
    protected int minInputs;

    protected boolean[] truthTable = new boolean[0];

    public void calculateTruthTable() {
        if (inputs.length > maxInputs) {
            throw new IllegalArgumentException("More inputs than max inputs");
        }

        truthTable = new boolean[(int) Math.pow(2, Math.max(varsCount(), 1))];

        //repeat for each "line" in the truth table
        for (int i = 0; i < truthTable.length; i++) {
            char[] bitChars = Integer.toBinaryString(i).toCharArray();

            //pad with leading 0s
            while (bitChars.length < varsCount()) {
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

    public int varsCount() {
        int counter = 0;
        for (char c : functionString.toCharArray()) {
            if (c >= 'A' && c <= 'Z') {
                counter++;
            }
        }
        return counter;
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
            return bits[expression.toCharArray()[0] - 'A'];
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

    public int addInput(Gate gate) {

        if (inputs.length + 1 > maxInputs) {
            throw new IllegalArgumentException("More inputs than max inputs");
        }
        Gate[] temp = new Gate[inputs.length + 1];
        System.arraycopy(inputs, 0, temp, 0, inputs.length);
        temp[temp.length - 1] = gate;
        inputs = temp;

        return temp.length - 1;
    }

    public void removeInput(int pos) {
        if (pos > inputs.length - 1) {
            throw new IllegalArgumentException("position greater than number of inputs");
        }
        System.arraycopy(inputs, pos + 1, inputs, pos, inputs.length - pos);
        checkInputAmount();
    }

    public Gate[] getInputs() {
        return inputs;
    }

    protected void checkInputAmount() throws IllegalArgumentException {
        if (inputs.length < minInputs || inputs.length > maxInputs) {
            throw new IllegalArgumentException("Incorrect number of gates provided, " +  inputs.length
                    + " gates provided outside range " + minInputs + " to " + maxInputs);
        }
    }

    public String getFunctionString() {
        return functionString;
    }
}
