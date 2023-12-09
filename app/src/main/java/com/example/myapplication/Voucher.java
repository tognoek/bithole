package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.myapplication.thuvien.PublicFunciton;
import com.example.myapplication.entity.SanPham;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class Voucher extends AppCompatActivity {

    private ImageView imageView_caidat;
    private ImageView imageView_trove;
    private LinearLayout pushFireBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);

        anhXa();
        onClick();



    }

    private void pushSoLuong() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("SoLuong");
        databaseReference.child("SanPham").setValue(19);
    }

    private void pushFireBaseFunction() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("SanPham");
        List<SanPham> sanPhamList = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            SanPham sanPham = new SanPham(i, "Đồ áo nữ siêu xinh", "Đẹp vô cùng nhưng mà đừng có mua do gia qua la đắt", 222222, 222, "hinhanh", PublicFunciton.getIdUser(), "1");
            sanPhamList.add(sanPham);
        }
        databaseReference.setValue(sanPhamList);

    }

    private void onClick(){
        imageView_caidat.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), CaiDatActivity.class))
        );

        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );

    }

    private void anhXa(){
        pushFireBase = findViewById(R.id.pushFirebaseTest);
        imageView_caidat = findViewById(R.id.img_caidat);
        imageView_trove = findViewById(R.id.img_trove);
    }

}