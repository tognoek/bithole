package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class detail extends AppCompatActivity {
    private ImageView imageView_caidat;
    private ImageView imageView_trove;
    private LinearLayout linear_xemshop;
    private LinearLayout linear_muangay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageView_caidat = findViewById(R.id.img_caidat);
        imageView_caidat.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), CaiDatActivity.class))
        );

        imageView_trove = findViewById(R.id.img_trove);
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );

        linear_xemshop = findViewById(R.id.xemshop);
        linear_xemshop.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), detail_shop.class))
        );

        linear_muangay = findViewById(R.id.f_muasam);
        linear_muangay.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), activity_trangchu.class))
        );
        findViewById(R.id.f_giohang).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), GioHang.class)));
    }
}