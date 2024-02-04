package com.circuitlens.app;


public class Variable extends Gate {
    private static int existingVariables = 0;

    public Variable() {
        functionString = Character.toString((char) (65 + existingVariables));
        existingVariables++;

        maxInputs = 0;
        minInputs = 0;
    }

    public static int getExistingVariables() {
        return existingVariables;
    }
}
