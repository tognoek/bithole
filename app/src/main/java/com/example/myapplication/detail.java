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
import com.example.myapplication.thuvien.FormatVND;
import com.example.myapplication.thuvien.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class detail extends AppCompatActivity {
    private ImageView imageView_caidat, imageView_trove;
    private LinearLayout linear_xemshop, linear_muangay;
    private TextView name, mota, dongia, soluong, shop;
    private SanPham sanPham;
    private ExpandableHeightGridView gridView;
    private AdapterSanPham adapterSanPham;
    private ArrayList<SanPham> listSanPham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        anhXa();
        onClick();
        //Set Product Detail
        setDetailProduct();

        //GridView
        doDuLieuVaoAdapter();
        doDuLieu();
        onClickGridView();


    }

    private void setDetailProduct() {
        sanPham= getDetail();
        name.setText(sanPham.getName());
        mota.setText(sanPham.getMota());
        dongia.setText((new FormatVND(String.valueOf(sanPham.getDongia())).getVND()));
        soluong.setText("Đã bán: " + String.valueOf(sanPham.getSoluong()));
        shop.setText(sanPham.getShop());
    }

    private SanPham getDetail() {
        Intent intent = getIntent();
        return (SanPham) intent.getSerializableExtra("SanPham");
    }

    private void onClick() {
        imageView_caidat.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), CaiDatActivity.class))
        );

        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );

        linear_xemshop.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), detail_shop.class))
        );

        linear_muangay.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), activity_trangchu.class))
        );
        findViewById(R.id.f_giohang).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), GioHang.class)));
        findViewById(R.id.muangay).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), activity_thanhtoan.class)));
    }

    private void onClickGridView() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(detail.this, detail.class);
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
                Toast.makeText(detail.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void anhXa(){
        gridView = (ExpandableHeightGridView) findViewById(R.id.listSanPham);
        gridView.setExpanded(true);
        linear_muangay = findViewById(R.id.f_muasam);
        imageView_caidat = findViewById(R.id.img_caidat);
        imageView_trove = findViewById(R.id.img_trove);
        linear_xemshop = findViewById(R.id.xemshop);
        name = findViewById(R.id.namePrd);
        mota = findViewById(R.id.mota);
        dongia = findViewById(R.id.dongia);
        soluong = findViewById(R.id.soluong);
        shop = findViewById(R.id.textNameShop);
    }
}