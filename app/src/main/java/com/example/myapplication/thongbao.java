package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.adapter.AdapterThongBao;
import com.example.myapplication.taikhoan.NguoiDungActivity;
import com.example.myapplication.thuvien.ExpandableHeightGridView;
import com.example.myapplication.entity.ThongBao;
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

    private ArrayList<ThongBao> listThongbao = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thongbao);

        thongBao();

        trangchu = (LinearLayout) findViewById(R.id.f_muasam);
        danhmuc = (LinearLayout) findViewById(R.id.f_danhmuc);
        giohang = (LinearLayout) findViewById(R.id.f_giohang);
        thongtin = (LinearLayout) findViewById(R.id.f_toi);
        gridViewThongbao = (ExpandableHeightGridView) findViewById(R.id.gridThongBao);
        gridViewThongbao.setExpanded(true);
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
    private void doDuLieuVaoAdapterThongBao(ArrayList<ThongBao> tblist) {

    }
    private void thongBao() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("ThongBao");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(listThongbao != null)
                {
                    listThongbao.clear();
                }

                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ThongBao thongBao = postSnapshot.getValue(ThongBao.class);
                    listThongbao.add(thongBao);
                    Log.d("motttmt", "onDataChange: " + thongBao.getTieude());
                }
                adapterThongBao= new AdapterThongBao(thongbao.this, R.layout.layout_thongbao, listThongbao);
                gridViewThongbao.setAdapter(adapterThongBao);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(thongbao.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}