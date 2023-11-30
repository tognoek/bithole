package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.thuvien.ExpandableHeightGridView;
import com.example.myapplication.thuvien.GridViewScrollable;
import com.example.myapplication.thuvien.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class select extends AppCompatActivity {
    private ImageView imageView_caidat;
    private ImageView imageView_trove;
    private LinearLayout linear_chitietsp;
    private EditText inputText;
    private ExpandableHeightGridView gridView;
    private AdapterSanPham adapterSanPham;
    private ArrayList<SanPham> listSanPham;
    private ArrayList<SanPham> listTimKiem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        anhXa();
        nutNhan();
        doDuLieuVaoAdapter();
        doDuLieu();
        onClickGridView();
    }
    private void onClickGridView() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(select.this, detail.class);
                intent.putExtra("SanPham", listSanPham.get(i));
                startActivity(intent);
            }
        });
    }

    private void doDuLieuVaoAdapter() {
        listSanPham = new ArrayList<>();
//        adapterSanPham = new AdapterSanPham(this, R.layout.layout_item, listSanPham);
//        gridView.setAdapter(adapterSanPham);
        listTimKiem = new ArrayList<>(listSanPham);
        adapterSanPham = new AdapterSanPham(this, R.layout.layout_item, listTimKiem);
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
//                adapterSanPham.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(select.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void nutNhan() {

        imageView_caidat.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), CaiDatActivity.class))
        );

        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );
        inputText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                filter(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void anhXa(){
        gridView = (ExpandableHeightGridView) findViewById(R.id.listSanPham);
        gridView.setExpanded(true);
        imageView_caidat = findViewById(R.id.img_caidat);
        imageView_trove = findViewById(R.id.img_trove);
        linear_chitietsp = findViewById(R.id.chitiet_sp);
        inputText = findViewById(R.id.editTextInput);
    }
    private void filter(CharSequence text){
        listTimKiem.clear();
        String query = text.toString().toLowerCase().trim();
        if(query.isEmpty()){
            listTimKiem.addAll(listSanPham);
        }else {
            for(SanPham sanPham : listSanPham){
                boolean isMatch = true;
                String[] keys = query.split("\\s+");
                for(String key : keys){
                    if(!sanPham.getName().toLowerCase().contains(key)){
                        isMatch = false;
                        break;
                    }
                }
                if (isMatch){
                    listTimKiem.add(sanPham);
                }

            }
        }
        adapterSanPham.notifyDataSetChanged();
    }

}