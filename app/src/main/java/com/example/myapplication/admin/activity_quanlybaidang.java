package com.example.myapplication.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.R;

public class activity_quanlybaidang extends AppCompatActivity {

    private ImageView imageView_trove;

    private LinearLayout linear_dangbai;
    private LinearLayout linear_doanhthu;

    private LinearLayout linear_nhapxuat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlybaidang);

        imageView_trove = findViewById(R.id.img_trove);
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );

        linear_dangbai = findViewById(R.id.f_dangbai);
        linear_dangbai.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), DangSanPhamActivity.class))
        );

        linear_doanhthu = findViewById(R.id.f_doanhthu_k);
        linear_doanhthu.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), thongkedoanhthu.class))
        );

        linear_nhapxuat = findViewById(R.id.f_nhapxuat);
        linear_nhapxuat.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), thongke.class))
        );
    }
}