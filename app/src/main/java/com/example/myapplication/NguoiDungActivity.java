package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class NguoiDungActivity extends AppCompatActivity {

    private ImageView imageView_caidat;
    private ImageView imageView_hotro;
    private ImageView imageView_trove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);

        imageView_caidat = findViewById(R.id.img_caidat);
        imageView_caidat.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), CaiDatActivity.class))
        );

        imageView_hotro = findViewById(R.id.img_hotro);
        imageView_hotro.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), HoTroActivity.class))
        );
        imageView_trove = findViewById(R.id.img_trove);
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );
    }
}