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
    private LinearLayout tknx, qlbaidang, dangbai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongkedoanhthu);
        tknx = (LinearLayout) findViewById(R.id.f_nhapxuat);
        qlbaidang = (LinearLayout) findViewById(R.id.f_qlbaidang);
        dangbai = (LinearLayout) findViewById(R.id.f_dangbai);
        tknx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), thongke.class);
                startActivity(intent);
            }
        });
        qlbaidang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), activity_quanlybaidang.class);
                startActivity(intent);
            }
        });
        dangbai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), DangSanPhamActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.icontrove).setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
    }
}