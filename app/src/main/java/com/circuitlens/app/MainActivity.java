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
        Gate g0 = new And(A, B);
        Gate g1 = new Or(g0, g0);
        Gate g2 = new Not(g1);
        Gate out = new OutputReader(g2);

        System.out.println(out.getFunctionString());

    }

    private static void printTruthTable(Gate gate) {
        boolean[] vals = gate.getTruthTable();
        System.out.println("In\tOut");
        for (int i = 0; i < vals.length; i++) {

            System.out.println(Integer.toBinaryString(i) + "\t" + vals[i]);
        }
    }
}