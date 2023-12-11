package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.myapplication.adapter.AdapterVoucher;
import com.example.myapplication.entity.VoucherItem;
import com.example.myapplication.thuvien.ExpandableHeightGridView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MaGiamGia extends AppCompatActivity {
    private ExpandableHeightGridView expandableHeightGridView;
    private ArrayList<VoucherItem> voucherItemArrayList;
    private AdapterVoucher adapterVoucher;
    private ImageView imageView_trove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ma_giam_gia);
        anhXa();
        oneClick();
        chuanbi();
        dodulieu();
    }

    private void chuanbi() {
        voucherItemArrayList = new ArrayList<>();
        adapterVoucher = new AdapterVoucher(this, R.layout.layout_item_voucher_show, voucherItemArrayList);
        expandableHeightGridView.setAdapter(adapterVoucher);
    }

    private void dodulieu() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Voucher");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    VoucherItem voucherItem = dataSnapshot.getValue(VoucherItem.class);
                    voucherItemArrayList.add(voucherItem);
                }
                adapterVoucher.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void oneClick() {
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed());
    }

    private void anhXa(){
        expandableHeightGridView = (ExpandableHeightGridView) findViewById(R.id.listvoucher);
        expandableHeightGridView.setExpanded(true);
        imageView_trove = findViewById(R.id.img_trove);
    }
}