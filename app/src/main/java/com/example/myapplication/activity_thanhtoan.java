package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class activity_thanhtoan extends AppCompatActivity {
    private Button momo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);

        anhXa();

        momo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                momoClick();
            }
        });
        onClick();
    }

    private void momoClick() {
        Log.d("momoclick", "momoClick: ");
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo("vn.momo", PackageManager.GET_ACTIVITIES);

            // Mở ứng dụng Momo
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("momo://"));
            startActivity(intent);

        } catch (PackageManager.NameNotFoundException e) {
            Log.d("momoclick", "momoClick: Fail");
            // Nếu Momo chưa được cài đặt, bạn có thể xử lý theo cách nào đó
            // Ví dụ: Hiển thị thông báo yêu cầu cài đặt Momo
        }
    }

    private void onClick() {
        findViewById(R.id.icback).setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed());
    }

    private void anhXa() {
        momo = findViewById(R.id.thanhtoan_momo);
    }
}