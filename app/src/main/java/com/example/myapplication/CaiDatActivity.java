package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.nome.AboutUs;
import com.example.myapplication.taikhoan.NguoiDungActivity;
import com.example.myapplication.thuvien.PublicFunciton;
import com.google.firebase.auth.FirebaseAuth;

public class CaiDatActivity extends AppCompatActivity {

    private ImageView imageView_hotro, imageView_trove;
    private TextView textViewLogOut, textViewNameUser;

    private RelativeLayout relative_s_toi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_dat);

        anhXa();

        //Dang xuat
        textViewLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(CaiDatActivity.this, SplashActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

        setNameUser();

        imageView_hotro = findViewById(R.id.img_hotro);
        imageView_hotro.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), HoTroActivity.class))
        );

        imageView_trove = findViewById(R.id.img_trove);
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );


        relative_s_toi = findViewById(R.id.h_s_toi);
        relative_s_toi.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), NguoiDungActivity.class))
        );
        findViewById(R.id.aboutus).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), AboutUs.class)));
    }

    private void setNameUser() {
        textViewNameUser.setText(PublicFunciton.getNameUser());
    }

    private void anhXa(){
        textViewNameUser = findViewById(R.id.nameUser);
        textViewLogOut = findViewById(R.id.text_dangxuat);
    }
}