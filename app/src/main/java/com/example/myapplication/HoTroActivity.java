package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class HoTroActivity extends AppCompatActivity {

    private ImageView imageView_trove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ho_tro);

        Intent intent2 = new Intent();
        intent2.setAction(Intent.ACTION_CALL);
        intent2.setData(Uri.parse("tel:0943152171"));
        findViewById(R.id.phone).setOnClickListener(view -> startActivity(intent2));
        imageView_trove = findViewById(R.id.img_trove);
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed());
    }
}