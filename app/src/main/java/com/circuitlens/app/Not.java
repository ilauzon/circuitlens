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
        functionTree.right = inputs[0].functionTree;
        functionTree.left = new BoolNode<>(false);
    }
}
