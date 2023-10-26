package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class GioHang extends AppCompatActivity {

    private ImageView imageView_trove;
    private LinearLayout linear_thanhtoan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);

        imageView_trove = findViewById(R.id.img_trove);
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );

        linear_thanhtoan = findViewById(R.id.thanhtoan);
        linear_thanhtoan.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), activity_thanhtoan.class)));
    }
}