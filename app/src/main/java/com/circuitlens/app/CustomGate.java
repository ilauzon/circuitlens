package com.circuitlens.app;

import java.util.HashMap;

public class CustomGate extends Gate {

    public CustomGate(int maxInputs) {
        if (maxInputs > MAX_INPUTS) {
            throw new IllegalArgumentException("Too many inputs, max is " + Gate.MAX_INPUTS);
        }
        this.maxInputs = maxInputs;
    }

    @Override
    protected boolean calculateBool(boolean[] bits) {
        return false;
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
