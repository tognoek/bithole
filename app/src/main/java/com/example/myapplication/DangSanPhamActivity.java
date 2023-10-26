package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class DangSanPhamActivity extends AppCompatActivity {

    private ImageView imageView_trove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_san_pham);
        imageView_trove = findViewById(R.id.img_trove);
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed());
    }
}