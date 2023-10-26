package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class select extends AppCompatActivity {
    private ImageView imageView_caidat;
    private ImageView imageView_trove;
    private LinearLayout linear_chitietsp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        imageView_caidat = findViewById(R.id.img_caidat);
        imageView_caidat.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), CaiDatActivity.class))
        );

        imageView_trove = findViewById(R.id.img_trove);
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );

        linear_chitietsp = findViewById(R.id.chitiet_sp);
        linear_chitietsp.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), detail.class))
        );
    }
}