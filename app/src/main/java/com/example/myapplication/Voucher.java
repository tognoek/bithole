package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.thuvien.FormatTime;
import com.example.myapplication.thuvien.FormatVND;
import com.example.myapplication.thuvien.PublicFunciton;
import com.example.myapplication.entity.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Voucher extends AppCompatActivity {

    private ImageView imageView_caidat;
    private TextView tienView;
    private ImageView imageView_trove, qr;
    private LinearLayout pushFireBase, themtien, trutien, tangtienthem, giamtienthem, camera;
    private int Tien;

    private final int REQUEST_CODE = 5823;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voucher);

        anhXa();
        onClick();
        Tien = 50000;
        setTien();
        tangtienthem.setOnClickListener(v -> {
            Tien = Tien + 50000;
            Tien = Math.min(Tien, 500000);
        });
        giamtienthem.setOnClickListener(v -> {
            Tien = Tien - 50000;
            Tien = Tien > 40000 ? Tien : 50000;
        });
        themtien.setOnClickListener(v -> {
            updateTien(Tien);
        });
        trutien.setOnClickListener(v -> {
            updateTien(Tien * (-1));
        });
        hienQr();
        Camera();
    }

    private void Camera() {
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void hienQr() {
        pushFireBase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (qr.getVisibility() == View.GONE){
                    qr.setVisibility(View.VISIBLE);
                }else{
                    qr.setVisibility(View.GONE);
                }
            }
        });
    }

    private void setTien() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Money").child(PublicFunciton.getIdUser());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Integer tienTong = snapshot.getValue(Integer.class);
                    tienView.setText(new FormatVND(String.valueOf(tienTong)).getVND());
                }
                else{
                    tienView.setText("0 VNĐ");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void updateTien(int tien) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Money").child(PublicFunciton.getIdUser());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Integer tienTong = snapshot.getValue(Integer.class);
                    Integer sumTien = tien + tienTong;
                    if (sumTien < 0){
                        sumTien = 0;
                    }
                    sumTien = Math.min(sumTien, 1000000000);
                    databaseReference.setValue(sumTien);
                    tienView.setText(new FormatVND(String.valueOf(sumTien)).getVND());
                }
                else{
                    databaseReference.setValue(Math.abs(tien));
                    tienView.setText(new FormatVND(String.valueOf(Math.abs(tien))).getVND());
                }
                setTien();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
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
        themtien = findViewById(R.id.themtien);
        trutien = findViewById(R.id.trutien);
        tangtienthem = findViewById(R.id.tangtienthem);
        giamtienthem = findViewById(R.id.giamtienthem);
        tienView = findViewById(R.id.tien);
        qr = findViewById(R.id.qr);
        camera = findViewById(R.id.camera);
    }

}