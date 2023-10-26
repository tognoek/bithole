package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class NguoiDungActivity extends AppCompatActivity {

    private ImageView imageView_caidat;
    private ImageView imageView_hotro;
    private ImageView imageView_trove;

    private ImageView imageView_giohang;
    private LinearLayout linear_trangchu;

    private LinearLayout linear_danhmuc;

    private LinearLayout linear_thongbao;

    private LinearLayout linear_giohang;
    private LinearLayout linear_vi;

    private LinearLayout linear_lichsumua;

    private RelativeLayout relative_donhangcuatoi;

    private Button button_dimuasam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);

        imageView_caidat = findViewById(R.id.img_caidat);
        imageView_caidat.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), CaiDatActivity.class))
        );

        imageView_hotro = findViewById(R.id.img_hotro);
        imageView_hotro.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), HoTroActivity.class))
        );

        imageView_giohang = findViewById(R.id.img_giohang);
        imageView_giohang.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), GioHang.class))
        );

        imageView_trove = findViewById(R.id.img_trove);
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );

        linear_trangchu = findViewById(R.id.f_muasam);
        linear_trangchu.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), activity_trangchu.class))
        );

        linear_danhmuc = findViewById(R.id.f_danhmuc);
        linear_danhmuc.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), select.class))
        );

        linear_thongbao = findViewById(R.id.f_thongbao);
        linear_thongbao.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), thongbao.class))
        );

        linear_giohang = findViewById(R.id.f_giohang);
        linear_giohang.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), GioHang.class))
        );

        linear_vi = findViewById(R.id.vi);
        linear_vi.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), Voucher.class))
        );

        linear_lichsumua = findViewById(R.id.lichsumua);
        linear_lichsumua.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), LichSuMuaHang.class))
        );

        relative_donhangcuatoi = findViewById(R.id.s_donhangcuatoi);
        relative_donhangcuatoi.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), thongtingiaohang.class))
        );

        button_dimuasam = findViewById(R.id.button_dimuasam);
        button_dimuasam.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), select.class))
        );
    }
}