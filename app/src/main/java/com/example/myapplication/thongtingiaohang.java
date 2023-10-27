package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class thongtingiaohang extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongtingiaohang);
        findViewById(R.id.backve).setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed());
    }
}