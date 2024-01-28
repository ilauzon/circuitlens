package com.circuitlens.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Gate not = new Not();
        not.addInput(new Constant(true));
        Gate and = new And();
        and.addInput(new Constant(true));
        and.addInput(new Constant(true));
        and.addInput(new Constant(true));
        Gate or = new Or();
        or.addInput(new Constant(true));
        or.addInput(new Constant(true));

        printTruthTable(not);
        System.out.println("\n");
        printTruthTable(and);
        System.out.println("\n");
        printTruthTable(or);

    }

    private static void printTruthTable(Gate gate) {
        boolean[] vals = gate.getTruthTable();
        System.out.println("In\tOut");
        for (int i = 0; i < vals.length; i++) {

            System.out.println(Integer.toBinaryString(i) + "\t" + vals[i]);
        }
    }
}