package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.thuvien.PublicFunciton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NguoiDungActivity extends AppCompatActivity {

    private ImageView imageView_caidat, imageView_hotro, imageView_trove, imageView_giohang;

    private LinearLayout linear_trangchu, linear_danhmuc, linear_thongbao, linear_giohang, linear_vi, linear_lichsumua;

    private TextView textViewNameUser;
    private RelativeLayout relative_donhangcuatoi;

    private Button button_dimuasam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);

        anhXa();
        onClick();
        setNameUser();

    }

    private void setNameUser() {
        textViewNameUser.setText(PublicFunciton.getNameUser());
    }

    private void onClick(){
        imageView_caidat.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), CaiDatActivity.class))
        );

        imageView_hotro.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), HoTroActivity.class))
        );

        imageView_giohang.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), GioHang.class))
        );

        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );

        linear_trangchu.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), activity_trangchu.class))
        );

        linear_danhmuc.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), select.class))
        );

        linear_thongbao.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), thongbao.class))
        );

        linear_giohang.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), GioHang.class))
        );

        linear_vi.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), Voucher.class))
        );

        linear_lichsumua.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), LichSuMuaHang.class))
        );

        relative_donhangcuatoi.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), thongtingiaohang.class))
        );

        button_dimuasam.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), select.class))
        );
    }

    private void anhXa(){
        textViewNameUser = findViewById(R.id.nameUser);
        button_dimuasam = findViewById(R.id.button_dimuasam);
        relative_donhangcuatoi = findViewById(R.id.s_donhangcuatoi);
        linear_lichsumua = findViewById(R.id.lichsumua);
        linear_vi = findViewById(R.id.vi);
        linear_thongbao = findViewById(R.id.f_thongbao);
        linear_trangchu = findViewById(R.id.f_muasam);
        linear_giohang = findViewById(R.id.f_giohang);
        linear_danhmuc = findViewById(R.id.f_danhmuc);
        imageView_trove = findViewById(R.id.img_trove);
        imageView_giohang = findViewById(R.id.img_giohang);
        imageView_hotro = findViewById(R.id.img_hotro);
        imageView_caidat = findViewById(R.id.img_caidat);
    }
}