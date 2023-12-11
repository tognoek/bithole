package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.example.myapplication.adapter.AdapterLichSu;
import com.example.myapplication.entity.LichSuCart;
import com.example.myapplication.entity.SanPham;
import com.example.myapplication.entity.SanPhamLichSu;
import com.example.myapplication.thuvien.ExpandableHeightGridView;
import com.example.myapplication.thuvien.PublicFunciton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LichSuMuaHang extends AppCompatActivity {
    private ExpandableHeightGridView expandableHeightGridView;
    private ImageView imageView_trove;
    private ArrayList<SanPhamLichSu> sanPhamLichSus;
    private AdapterLichSu adapterLichSu;
    private LichSuCart lichSuCart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lich_su_mua_hang);

        anhXa();
        dodulieu();
        dodulieuadapter();

        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );
    }
    private void dodulieu(){
        sanPhamLichSus = new ArrayList<>();
        adapterLichSu = new AdapterLichSu(this, R.layout.layout_item_lichsu, sanPhamLichSus);
        expandableHeightGridView.setAdapter(adapterLichSu);
    }
    private void dodulieuadapter(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("LichSu").child(PublicFunciton.getIdUser());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    lichSuCart = dataSnapshot.getValue(LichSuCart.class);
                    Log.d("cay", "onDataChange: " + lichSuCart.getIdSanPham());
                    DatabaseReference databaseReferenceSanPham = firebaseDatabase.getReference("SanPham").child(String.valueOf(lichSuCart.getIdSanPham()));
                    databaseReferenceSanPham.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            SanPham sanPham = snapshot.getValue(SanPham.class);
                            assert sanPham != null;
                            sanPhamLichSus.add(new SanPhamLichSu(
                                    sanPham.getId(),
                                    sanPham.getName(),
                                    sanPham.getMota(),
                                    sanPham.getDongia(),
                                    lichSuCart.getSoluong(),
                                    sanPham.getHinhanh(),
                                    sanPham.getIdshop(),
                                    lichSuCart.getDate()));
                            adapterLichSu.notifyDataSetChanged();
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void anhXa(){
        expandableHeightGridView = (ExpandableHeightGridView) findViewById(R.id.listlichsu);
        expandableHeightGridView.setExpanded(true);
        imageView_trove = findViewById(R.id.img_trove);
    }
}