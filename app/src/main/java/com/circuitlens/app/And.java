package com.circuitlens.app;

public class And extends Gate {

    public And() {
        maxInputs = (int) Math.pow(2, 16);
    }

    protected boolean calculateBool(int input) {
        char[] bitChars = Integer.toBinaryString(input).toCharArray();
        boolean[] bits = new boolean[bitChars.length];
        for (int i = 0; i < bitChars.length; i++) {
            bits[i] = (bitChars[i] == '1') ? true : false;
        }

        for (boolean bit : bits) {
            if (!bit) return false;
        }
        return true;
    }
}
