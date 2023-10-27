package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class activity_login extends AppCompatActivity {

    private Button button_dangky;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_dangky = findViewById(R.id.btsignup);
        button_dangky.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), activity_dangky.class))
        );
    }
}