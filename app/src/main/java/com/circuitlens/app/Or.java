package com.circuitlens.app;

/**
 * represents a fundamental OR gate.
 */
public class Or extends Gate {

    public Or(Gate... inputs) {
        maxInputs = MAX_INPUTS;
        minInputs = 2;

        this.inputs = inputs;
        checkInputAmount();

        functionTree = new BoolNode(LogicalOperator.OR);
        functionTree.left = inputs[0].functionTree;
        functionTree.right = inputs[1].functionTree;
    }
}