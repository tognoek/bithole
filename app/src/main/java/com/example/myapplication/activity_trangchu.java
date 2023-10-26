package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class activity_trangchu extends AppCompatActivity {

    private ImageView minigame;

    private ImageView imageView;
    private LinearLayout thongbao;
    private LinearLayout linear_danhmuc;
    private LinearLayout linear_giohang;
    private LinearLayout linear_toi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);
        minigame = (ImageView) findViewById(R.id.minigame);
        thongbao = (LinearLayout) findViewById(R.id.f_thongbao);
        minigame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),minigame.class);
                startActivity(intent);
            }
        });
        thongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),thongbao.class);
                startActivity(intent);
            }
        });

        linear_danhmuc = findViewById(R.id.f_danhmuc);
        linear_danhmuc.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), select.class))
        );

        linear_giohang = findViewById(R.id.f_giohang);
        linear_giohang.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), GioHang.class))
        );

        linear_toi = findViewById(R.id.f_toi);
        linear_toi.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), NguoiDungActivity.class))
        );

        imageView = findViewById(R.id.f_magiamgia);
        imageView.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), MaGiamGia.class))
        );
    }
}