package com.circuitlens.app;


public class Variable extends Gate {
    private static int existingVariables = 0;
    private String identifier;

    public Variable(String identifier) {
        existingVariables++;
        this.identifier = identifier;
        functionTree = new BoolNode<Variable>(this);

        maxInputs = 0;
        minInputs = 0;
    }

    public String toString() {
        return identifier;
    }

    public static int getExistingVariables() {
        return existingVariables;
    }
}
