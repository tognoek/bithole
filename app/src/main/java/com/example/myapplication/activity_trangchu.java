package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
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

public class activity_trangchu extends AppCompatActivity {

    private ImageView miniGame, imageView;
    private EditText editText;
    private LinearLayout thongbao, linear_danhmuc, linear_giohang, linear_toi;


    private ExpandableHeightGridView gridView;
    private AdapterSanPham adapterSanPham;
    private ArrayList<SanPham> listSanPham;
    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trangchu);
        id = 2;
        anhXa();
        onClick();
        doDuLieuVaoAdapter();
        doDuLieu();
        onClickGridView();

    }

    private void onClickGridView() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(activity_trangchu.this, detail.class);
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
                    if (sanPham != null){
                        if (sanPham.getId() % id == 0){
                            listSanPham.add(sanPham);
                        }
                    }
                }
                adapterSanPham.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(activity_trangchu.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void onClick(){
        miniGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),minigame.class);
                startActivity(intent);
            }
        });
        thongbao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),thongbao.class);
                startActivity(intent);
            }
        });

        linear_danhmuc.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), select.class))
        );

        linear_giohang = findViewById(R.id.f_giohang);
        linear_giohang.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), GioHang.class))
        );

        linear_toi.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), NguoiDungActivity.class))
        );

        imageView.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), MaGiamGia.class))
        );

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_NULL) {
                    startActivity(new Intent(getApplicationContext(), select.class));
                    return true;
                }
                return false;
            }
        });
    }

    private void anhXa(){
        gridView = (ExpandableHeightGridView)findViewById(R.id.listSanPham);
        gridView.setExpanded(true);
        miniGame = (ImageView) findViewById(R.id.minigame);
        thongbao = (LinearLayout) findViewById(R.id.f_thongbao);
        imageView = findViewById(R.id.f_magiamgia);
        linear_toi = findViewById(R.id.f_toi);
        linear_danhmuc = findViewById(R.id.f_danhmuc);
        editText = findViewById(R.id.editTextInput);

    }
}