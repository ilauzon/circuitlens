package com.circuitlens.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gate A = new Variable("A");
        Gate B = new Variable("B");
        Gate C = new Variable("C");

        Gate and = new And(A, B);
        Gate or = new Or(C, and);

        or.functionTree.printTree(0);

        OutputReader out = new OutputReader(or);
        printTruthTable(out);

    }

    private static void printTruthTable(OutputReader out) {
        boolean[] vals = out.getTruthTable();

        for (int i = out.getVars().size() - 1; i >= 0; i--) {
            System.out.print(out.getVars().get(i) + "\t");
        }
        System.out.println(out.functionTree);

        for (int i = 0; i < vals.length; i++) {
            String binaryString = Integer.toBinaryString(i);

            // pad with leading zeroes
            while (binaryString.length() < out.getVars().size()) {
                binaryString = '0' + binaryString;
            }

            for (char c : binaryString.toCharArray()) {
                System.out.print(c + "\t");
            }
            System.out.println(vals[i]);
        }
    }
}