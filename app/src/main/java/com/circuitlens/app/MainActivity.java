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

        Gate and = new And(B, C);
        Gate or = new Or(A, and);

        OutputReader out = new OutputReader(or);
        printTruthTable(out);

    }

    private static void printTruthTable(OutputReader out) {
        boolean[] vals = out.getTruthTable();

        for (int i = out.varsCount() - 1; i >= 0; i--) {
            System.out.print((char) (i + 65) + "\t");
        }
        System.out.println(out.getFunctionString());

        for (int i = 0; i < vals.length; i++) {
            String binaryString = Integer.toBinaryString(i);

            // pad with leading zeroes
            while (binaryString.length() < out.varsCount()) {
                binaryString = '0' + binaryString;
            }

            for (char c : binaryString.toCharArray()) {
                System.out.print(c + "\t");
            }
            System.out.println(vals[i]);
        }
    }
}