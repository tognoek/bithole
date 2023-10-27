package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class CaiDatActivity extends AppCompatActivity {

    private ImageView imageView_hotro;
    private ImageView imageView_trove;
    private TextView textView_dangxuat;

    private RelativeLayout relative_s_toi;

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
                getOnBackPressedDispatcher().onBackPressed()
        );

        textView_dangxuat = findViewById(R.id.text_dangxuat);
        textView_dangxuat.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), MainActivity.class))
        );

        relative_s_toi = findViewById(R.id.h_s_toi);
        relative_s_toi.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), NguoiDungActivity.class))
        );
    }
}