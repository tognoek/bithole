package com.example.myapplication.thanhtoan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.GioHang;
import com.example.myapplication.R;
import com.example.myapplication.SplashActivity;
import com.example.myapplication.activity_trangchu;
import com.example.myapplication.detail;
import com.example.myapplication.entity.LichSuCart;
import com.example.myapplication.entity.ListCard;
import com.example.myapplication.entity.SanPham;
import com.example.myapplication.taikhoan.MainActivity;
import com.example.myapplication.thuvien.CustomProgressDialog;
import com.example.myapplication.thuvien.FormatTime;
import com.example.myapplication.thuvien.PublicFunciton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class thucThiThanhToan extends AppCompatActivity {

    private ArrayList<Integer> listcard;
    private ArrayList<Integer> listsoluong;
    private int soluong;
    private ListCard listCard;
    private CustomProgressDialog customProgressDialog;
    private SanPham sanPham;
    private ArrayList<LichSuCart> lichSuCarts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuc_thi_thanh_toan);
        customProgressDialog = new CustomProgressDialog(this);
        getList();

        if (listsoluong.get(0) > 0){
            xoaDanhSach();
        }else{
            themLichSu();
        }
    }

    private void themLichSu() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceLichSu = firebaseDatabase.getReference("LichSu").child(PublicFunciton.getIdUser());
        LichSuCart lichSuCart = new LichSuCart(listcard.get(0), listcard.get(0), 1, new FormatTime(PublicFunciton.getDay()).getTimeInteger());
        databaseReferenceLichSu.child(String.valueOf(lichSuCart.getId())).setValue(lichSuCart);

        DatabaseReference databaseReferenceSanPham = firebaseDatabase.getReference("SanPham").child(String.valueOf(listcard.get(0)));
        databaseReferenceSanPham.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sanPham = snapshot.getValue(SanPham.class);
                assert sanPham != null;
                int soluognsanpham = sanPham.getSoluong();
                if (soluognsanpham <= 1){
                    databaseReferenceSanPham.child("soluong").setValue(0);
                }else{
                    databaseReferenceSanPham.child("soluong").setValue(soluognsanpham - 1);
                }
                PublicFunciton.taoThongBao(listcard.get(0), sanPham.getIdshop(), 3);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), detail.class);
                sanPham.setSoluong(sanPham.getSoluong() - 1);
                intent.putExtra("SanPham", sanPham);
                startActivity(intent);
                finish();
            }
        }, 1000);
    }

    private void getList(){
        Intent intent = getIntent();
        if (intent.hasExtra("listsoluong")){
            listcard = intent.getIntegerArrayListExtra("listcart");
            listsoluong = intent.getIntegerArrayListExtra("listsoluong");
        }
    }
    private void xoaDanhSach(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("GioHang").child(PublicFunciton.getIdUser());
        customProgressDialog.show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    listCard = dataSnapshot.getValue(ListCard.class);
                    for (int i = 0; i < listcard.size(); i++){
                        assert listCard != null;
                        if (listCard.getId() == listcard.get(i)){
                            soluong = listsoluong.get(i);
                            databaseReference.child(String.valueOf(listCard.getId())).removeValue();
                            DatabaseReference databaseReferenceSanPham = firebaseDatabase.getReference("SanPham").child(String.valueOf(listCard.getId()));
                            databaseReferenceSanPham.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    SanPham sanPham = snapshot.getValue(SanPham.class);
                                    assert sanPham != null;
                                    int soluognsanpham = sanPham.getSoluong();
                                    if (soluognsanpham <= soluong){
                                        databaseReferenceSanPham.child("soluong").setValue(0);
                                    }else{
                                        databaseReferenceSanPham.child("soluong").setValue(soluognsanpham - soluong);
                                    }
                                    PublicFunciton.taoThongBao(listCard.getId(), sanPham.getIdshop(), 3);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                            DatabaseReference databaseReferenceLichSu = firebaseDatabase.getReference("LichSu").child(PublicFunciton.getIdUser());
                            LichSuCart lichSuCart = new LichSuCart(listCard.getId(), listCard.getId(), soluong, new FormatTime(PublicFunciton.getDay()).getTimeInteger());
                            databaseReferenceLichSu.child(String.valueOf(lichSuCart.getId())).setValue(lichSuCart);
                        }
                    }
                }
                customProgressDialog.dismiss();
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(new Intent(getApplicationContext(), GioHang.class));
                        finish();
                    }
                }, 1000);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}