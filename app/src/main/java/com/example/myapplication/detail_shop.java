package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.thuvien.ExpandableHeightGridView;
import com.example.myapplication.thuvien.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class detail_shop extends AppCompatActivity {
    private ImageView imageView_caidat, imageView_trove;
    private LinearLayout linear_trangchu, linear_danhmuc, linear_thongbao, linear_giohang, linear_toi;
    private TextView textView_doanhthu;
    private ExpandableHeightGridView gridView;
    private AdapterSanPham adapterSanPham;
    private ArrayList<SanPham> listSanPham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_shop);
        anhXa();
        onClick();

        //GridView
        doDuLieuVaoAdapter();
        doDuLieu();
        onClickGridView();
    }

    private void onClickGridView() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(detail_shop.this, detail.class);
                intent.putExtra("SanPham", listSanPham.get(i));
                startActivity(intent);
            }
        });
    }
    private void doDuLieuVaoAdapter() {
        listSanPham = new ArrayList<>();
        adapterSanPham = new AdapterSanPham(this, R.layout.layout_item, listSanPham);
        gridView.setAdapter(adapterSanPham);
    }

    private void doDuLieu() {
        listSanPham.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("SanPham");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    SanPham sanPham = postSnapshot.getValue(SanPham.class);
                    listSanPham.add(sanPham);
                }
                adapterSanPham.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(detail_shop.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void onClick(){
        imageView_caidat.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), CaiDatActivity.class))
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

        linear_toi.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), NguoiDungActivity.class))
        );

        textView_doanhthu.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), thongkedoanhthu.class))
        );
    }

    private void anhXa(){
        gridView = (ExpandableHeightGridView) findViewById(R.id.listSanPham);
        gridView.setExpanded(true);
        textView_doanhthu = findViewById(R.id.textNameShop);
        linear_toi = findViewById(R.id.f_toi);
        linear_giohang = findViewById(R.id.f_giohang);
        linear_thongbao = findViewById(R.id.f_thongbao);
        linear_danhmuc = findViewById(R.id.f_danhmuc);
        linear_trangchu = findViewById(R.id.f_muasam);
        imageView_caidat = findViewById(R.id.img_caidat);
        imageView_trove = findViewById(R.id.img_trove);
    }
}