package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.admin.activity_quanlybaidang;
import com.example.myapplication.admin.thongke;
import com.example.myapplication.detail_shop;

public class thongkedoanhthu extends AppCompatActivity {
    private LinearLayout tknx;
    private LinearLayout qlbd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongkedoanhthu);
        tknx = (LinearLayout) findViewById(R.id.thongkenx);
        qlbd = (LinearLayout) findViewById(R.id.qlbaidang);
        tknx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), thongke.class);
                startActivity(intent);
            }
        });
        qlbd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_quanlybaidang.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.backhomeshop).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), detail_shop.class)));
    }
}