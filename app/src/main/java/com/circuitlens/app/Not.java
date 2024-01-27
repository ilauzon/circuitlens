package com.circuitlens.app;

public class Not extends Gate {

    public Not() {
        maxInputs = 1;
    }

    protected boolean calculateBool(int input) {
        char[] bitChars = Integer.toBinaryString(input).toCharArray();
        boolean[] bits = new boolean[bitChars.length];
        for (int i = 0; i < bitChars.length; i++) {
            bits[i] = (bitChars[i] == '1') ? true : false;
        }

        if (bits[0])
            return false;
        return true;
    }
}
