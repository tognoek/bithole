package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
        findViewById(R.id.sanpham).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), detail.class)));
        EditText editText = findViewById(R.id.editTextInput); // Thay thế bằng ID của EditText trong layout của bạn

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    startActivity(new Intent(getApplicationContext(), select.class));
                    return true;
                }
                return false;
            }
        });
    }
}