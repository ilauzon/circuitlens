package com.circuitlens.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gate A = new Variable();
        Gate B = new Variable();
        Gate C = new Variable();
        Gate D = new Variable();

        Gate and = new And(A, B);
        Gate or = new Or(C, and);

        OutputReader out = new OutputReader(or);
        printTruthTable(out);

    }

    private static void printTruthTable(OutputReader out) {
        boolean[] vals = out.getTruthTable();

        for (int i = out.getVarArray().length - 1; i >= 0; i--) {
            System.out.print(out.getVarChar(i) + "\t");
        }
        System.out.println(out.getFunctionString());

        for (int i = 0; i < vals.length; i++) {
            String binaryString = Integer.toBinaryString(i);

            // pad with leading zeroes
            while (binaryString.length() < out.getVarArray().length) {
                binaryString = '0' + binaryString;
            }

            for (char c : binaryString.toCharArray()) {
                System.out.print(c + "\t");
            }
            System.out.println(vals[i]);
        }
    }
}