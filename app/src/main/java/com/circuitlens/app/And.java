package com.circuitlens.app;

/**
 * represents a fundamental AND gate.
 */
public class And extends Gate {

    public And(Gate... inputs) {
        maxInputs = MAX_INPUTS;
        minInputs = 2;

        this.inputs = inputs;
        checkInputAmount();

        functionTree = new BoolNode(LogicalOperator.AND);
        functionTree.left = inputs[0].functionTree;
        functionTree.right = inputs[1].functionTree;
    }
}