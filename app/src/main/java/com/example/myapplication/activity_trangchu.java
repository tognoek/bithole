package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class activity_trangchu extends AppCompatActivity {

    private ImageView minigame;
    private LinearLayout thongbao;
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
    }
}