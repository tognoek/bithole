package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class thongbao extends AppCompatActivity {

    private LinearLayout trangchu;
    private LinearLayout danhmuc;
    private LinearLayout giohang;
    private ImageView giohangic;
    private LinearLayout thongtin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongbao);
        trangchu = (LinearLayout) findViewById(R.id.f_muasam);
        danhmuc = (LinearLayout) findViewById(R.id.f_danhmuc);
        giohang = (LinearLayout) findViewById(R.id.f_giohang);
        giohangic = (ImageView) findViewById(R.id.giohang);
        thongtin = (LinearLayout) findViewById(R.id.f_toi);
        trangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),activity_trangchu.class);
                startActivity(intent);
            }
        });
        danhmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),select.class);
                startActivity(intent);
            }
        });
        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),GioHang.class);
                startActivity(intent);
            }
        });
        giohangic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),GioHang.class);
                startActivity(intent);
            }
        });
        thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),NguoiDungActivity.class);
                startActivity(intent);
            }
        });
    }
}