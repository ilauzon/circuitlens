package com.circuitlens.app;

/**
 * Represents a fundamental NOT Gate.
 */
public class Not extends Gate {

    public Not(Gate... inputs) {
        maxInputs = 1;
        minInputs = 1;

        this.inputs = inputs;
        checkInputAmount();

        functionTree = new BoolNode(LogicalOperator.NOT);
        functionTree.left = inputs[0].functionTree;
        functionTree.right = new BoolNode(false);
    }
}
