package com.circuitlens.app;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.function.BiFunction;

/**
 * Calculates boolean functions based off of an array of fundamental (NOT, OR, AND) Gate objects.
 */
public class OutputReader extends Gate {
    private ArrayList<String> vars = new ArrayList<>();

    public OutputReader(Gate input) {
        maxInputs = 1;
        minInputs = 1;

        inputs = new Gate[]{input};

        functionTree = inputs[0].functionTree;
        initVars(functionTree);
    }

    // cached truth table
    private boolean[] truthTable;

    private void calculateTruthTable() {
        if (inputs.length > maxInputs) {
            throw new IllegalArgumentException("More inputs than max inputs");
        }
        truthTable = new boolean[(int) Math.pow(2, Math.max(vars.size(), 1))];

        //repeat for each "line" in the truth table
        for (int i = 0; i < truthTable.length; i++) {
            char[] bitChars = Integer.toBinaryString(i).toCharArray();

            //pad with leading 0s
            while (bitChars.length < vars.size()) {
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
            truthTable[i] = calculateBool(bits, functionTree);
        }
    }

    private void initVars(BoolNode<?> root) {
        if (root.value instanceof Variable && !vars.contains(root.value.toString())) {
            vars.add(root.value.toString());
            return;
        }

        initVars(root.left);
        initVars(root.right);
    }

    /**
     * The goal of this method is to improve upon the initial calculateBool method using a stack
     * structure to manage scope.
     * @param bits
     * @return
     */
    private boolean calculateBool(boolean[] bits, BoolNode<?> root) {
        if (root.value instanceof Variable) { //i.e. if the root is a leaf
            return bits[vars.indexOf(root.value.toString())];
        } else if (root.value instanceof Boolean) { //constant boolean value
            return (Boolean) root.value;
        } else { //root.value is a LogicalOperator
            return ((LogicalOperator) (root.value)).getFunction().apply(calculateBool(bits, root.left), calculateBool(bits, root.right));
        }
    }

    public boolean getValue(int row) {
        if (truthTable == null) {
            calculateTruthTable();
        }
        return truthTable[row];
    }

    public boolean[] getTruthTable() {
        if (truthTable == null) {
            calculateTruthTable();
        }
        return truthTable;
    }

    public ArrayList<String> getVars() {
        return vars;
    }
}
