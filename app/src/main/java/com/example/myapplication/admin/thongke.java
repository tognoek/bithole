package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.myapplication.R;
import com.example.myapplication.taikhoan.NguoiDungActivity;

public class thongke extends AppCompatActivity {
    private LinearLayout linear_dangbai;
    private LinearLayout linear_doanhthu;

    private LinearLayout linear_qlbaidang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongkr);

        linear_dangbai = findViewById(R.id.f_dangbai);
        linear_dangbai.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), DangSanPhamActivity.class))
        );

        linear_doanhthu = findViewById(R.id.f_doanhthu_k);
        linear_doanhthu.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), thongkedoanhthu.class))
        );


        linear_qlbaidang = findViewById(R.id.f_qlbaidang);
        linear_qlbaidang.setOnClickListener(view -> {
                    startActivity(new Intent(getApplicationContext(), activity_quanlybaidang.class));
                    finish();
                }
        );
        findViewById(R.id.icontrove).setOnClickListener(view ->{
            startActivity(new Intent(getApplicationContext(), NguoiDungActivity.class));
        });
    }
}