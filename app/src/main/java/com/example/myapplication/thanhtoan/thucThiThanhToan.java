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
    private ArrayList<LichSuCart> lichSuCarts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuc_thi_thanh_toan);
        customProgressDialog = new CustomProgressDialog(this);
        getList();
        xoaDanhSach();
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
                            databaseReferenceSanPham.child("soluong").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    Integer soluognsanpham = snapshot.getValue(Integer.class);
                                    if (soluognsanpham <= soluong){
                                        databaseReferenceSanPham.removeValue();
                                    }else{
                                        databaseReferenceSanPham.child("soluong").setValue(soluognsanpham - soluong);
                                    }
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