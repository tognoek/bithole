package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

public class CaiDatActivity extends AppCompatActivity {

    private ImageView imageView_hotro;
    private ImageView imageView_trove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat);

        imageView_hotro = findViewById(R.id.img_hotro);
        imageView_hotro.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), HoTroActivity.class))
        );

        imageView_trove = findViewById(R.id.img_trove);
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed());
    }
}