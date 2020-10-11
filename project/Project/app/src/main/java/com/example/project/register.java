package com.example.project;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class register extends AppCompatActivity {
    TextView txt1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        txt1 = (TextView) findViewById(R.id.dangki);
        txt1.setTextColor(Color.WHITE);
    }
}