package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.adapter.AdapterComment;
import com.example.myapplication.adapter.AdapterThongBao;
import com.example.myapplication.taikhoan.NguoiDungActivity;
import com.example.myapplication.thuvien.ExpandableHeightGridView;
import com.example.myapplication.entity.ThongBao;
import com.example.myapplication.thuvien.PublicFunciton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class thongbao extends AppCompatActivity {

    private LinearLayout trangchu;
    private LinearLayout danhmuc;
    private LinearLayout giohang;
    private LinearLayout thongtin;
    private ExpandableHeightGridView gridViewThongbao;

    private AdapterThongBao adapterThongBao;

    private ArrayList<ThongBao> listThongbao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongbao);

        AnhXa();
        OnClick();

        doDuLieuVaoAdapterThongBao();
        thongBao();

    }
    private void doDuLieuVaoAdapterThongBao() {
        listThongbao = new ArrayList<>();
        adapterThongBao= new AdapterThongBao(thongbao.this, R.layout.layout_thongbao, listThongbao);
        gridViewThongbao.setAdapter(adapterThongBao);
    }
    private void thongBao() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("ThongBao").child(PublicFunciton.getIdUser());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listThongbao.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ThongBao thongBao = postSnapshot.getValue(ThongBao.class);
                    listThongbao.add(thongBao);
                }
               adapterThongBao.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(thongbao.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void OnClick(){
        trangchu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),activity_trangchu.class);
                startActivity(intent);
            }
        });
        danhmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),select.class);
                startActivity(intent);
            }
        });
        giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),GioHang.class);
                startActivity(intent);
            }
        });
        thongtin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), NguoiDungActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.thongbao).setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed());
    }
    private void AnhXa(){
        trangchu = (LinearLayout) findViewById(R.id.f_muasam);
        danhmuc = (LinearLayout) findViewById(R.id.f_danhmuc);
        giohang = (LinearLayout) findViewById(R.id.f_giohang);
        thongtin = (LinearLayout) findViewById(R.id.f_toi);
        gridViewThongbao = (ExpandableHeightGridView) findViewById(R.id.gridThongBao);
        gridViewThongbao.setExpanded(true);
    }
}