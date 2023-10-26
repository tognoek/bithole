package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

public class HoTroActivity extends AppCompatActivity {

    private ImageView imageView_trove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho_tro);

        imageView_trove = findViewById(R.id.img_trove);
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed());
    }
}