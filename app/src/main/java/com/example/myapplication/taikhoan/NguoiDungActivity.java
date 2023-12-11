package com.example.myapplication.taikhoan;

import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_USER_REF;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.myapplication.CaiDatActivity;
import com.example.myapplication.GioHang;
import com.example.myapplication.HoTroActivity;
import com.example.myapplication.LichSuMuaHang;
import com.example.myapplication.MaGiamGia;
import com.example.myapplication.R;
import com.example.myapplication.Voucher;
import com.example.myapplication.activity_trangchu;
import com.example.myapplication.admin.DangSanPhamActivity;
import com.example.myapplication.admin.taoVoucher;
import com.example.myapplication.admin.thongkedoanhthu;
import com.example.myapplication.minigame;
import com.example.myapplication.select;
import com.example.myapplication.thongbao;
import com.example.myapplication.thongtingiaohang;
import com.example.myapplication.thuvien.PublicFunciton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

public class NguoiDungActivity extends AppCompatActivity {

    private ImageView imageView_caidat, imageView_hotro, imageView_trove, imageView_giohang, image_user, theodoi;

    private LinearLayout linear_trangchu, linear_danhmuc, linear_thongbao, linear_giohang, linear_vi, linear_lichsumua,
            linear_ttuser, linear_dangbai, linear_magiamgia, linear_minigame, linear_thongkedoanhthu;

    private TextView textViewNameUser;
    private RelativeLayout relative_donhangcuatoi;

    private Button button_dimuasam;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nguoi_dung);

        anhXa();
        onClick();

    }
    @Override
    protected void onResume() {
        super.onResume();

        setDetails();
    }

    private void setDetails() {
        textViewNameUser.setText(PublicFunciton.getNameUser());
        PRODUCT_IMAGE_USER_REF.child(PublicFunciton.getIdUser()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (uri != null){
                    Picasso.get().load(uri).into(image_user);
                }
            }
        });
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
        linear_dangbai.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), DangSanPhamActivity.class))
        );
        linear_magiamgia.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), MaGiamGia.class))
        );
        linear_minigame.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), minigame.class))
        );
        linear_thongkedoanhthu.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), thongkedoanhthu.class))
        );
        linear_ttuser.setOnClickListener(new View.OnClickListener() {
                                             @Override
                                             public void onClick(View view) {
                                                 startActivity(new Intent(NguoiDungActivity.this, ThongTinNguoiDungActivity.class));
                                             }
                                         }

        );

        relative_donhangcuatoi.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), thongtingiaohang.class))
        );

        button_dimuasam.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), select.class))
        );
        theodoi.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), taoVoucher.class)));
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
        linear_ttuser = findViewById(R.id.ttuser);
        linear_dangbai = findViewById(R.id.f_dangbai);
        linear_magiamgia = findViewById(R.id.f_magiamgia);
        linear_minigame = findViewById(R.id.santhuong);
        linear_thongkedoanhthu = findViewById(R.id.thongkedoanhthu);
        imageView_trove = findViewById(R.id.img_trove);
        imageView_giohang = findViewById(R.id.img_giohang);
        imageView_hotro = findViewById(R.id.img_hotro);
        imageView_caidat = findViewById(R.id.img_caidat);
        image_user = findViewById(R.id.circle_user);
        theodoi = findViewById(R.id.newVoucher);
    }
}