package com.circuitlens.app;

import java.util.ArrayList;
import java.util.List;

/**
 * The template for a logical gate. A gate is defined to have a single output and can have any
 * amount of inputs.
 */
public abstract class Gate {
    public Gate[] inputs = new Gate[0];

    public static final int MAX_INPUTS = 25;

    public BoolNode<?> functionTree;

    public int maxInputs;
    public int minInputs;

    public void checkInputAmount() throws IllegalArgumentException {
        if (inputs.length > maxInputs || inputs.length < minInputs) {
            throw new IllegalArgumentException("Incorrect number of gates provided, " +  inputs.length
                    + " gates provided outside range " + minInputs + " to " + maxInputs);
        }
    }

    public BoolNode<?> getFunctionTree() {
        return functionTree;
    }
}
