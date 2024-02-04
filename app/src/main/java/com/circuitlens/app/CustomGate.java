package com.circuitlens.app;

import java.util.HashMap;

/**
 * A group of Gates.
 */
public class CustomGate extends Gate {
    private Gate[] subGates;

    public CustomGate(int maxInputs, Gate... gates) {
        if (maxInputs > MAX_INPUTS) {
            throw new IllegalArgumentException("Too many inputs, max is " + Gate.MAX_INPUTS);
        }
        this.maxInputs = maxInputs;

        subGates = gates;
    }


    @Override
    public int addInput(Gate gate) {
        if (inputs == null) {
            inputs = new Gate[0];
        }
        if (inputs.length + 1 > maxInputs) {
            throw new IllegalArgumentException("More inputs than max inputs");
        }
        Gate[] temp = new Gate[inputs.length + 1];
        System.arraycopy(inputs, 0, temp, 0, inputs.length);
        temp[temp.length - 1] = gate;
        inputs = temp;

        calculateTruthTable();

        return temp.length - 1;
    }


    private Gate findEndGate(Gate[] gates) {
        HashMap<Gate, Integer> gatesWithOutputs = new HashMap<>();

        for (int i = 0; i < gates.length; i++) {
            Gate[] currentGateInputs = gates[i].getInputs();
            for (Gate gate : currentGateInputs) {
                gatesWithOutputs.put(gate, i);
            }
        }
        for (Gate gate : gates) {
            if (!gatesWithOutputs.containsKey(gate)) {
                return gate;
            }
        }
        return null;
    }

}
