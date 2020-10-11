package com.example.project;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class login extends AppCompatActivity {
    TextView txt1, txt2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txt1 = (TextView) findViewById(R.id.newUser);
        txt1.setTextColor(Color.WHITE);
        txt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this,register.class);
                startActivity(intent);

            }
        });
        txt2 = (TextView) findViewById(R.id.forgetPassword);
        txt2.setTextColor(Color.WHITE);
    }
}