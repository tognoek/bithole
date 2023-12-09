package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.myapplication.adapter.AdapterDanhMuc;
import com.example.myapplication.adapter.AdapterSanPham;
import com.example.myapplication.admin.DangSanPhamActivity;
import com.example.myapplication.entity.DanhMuc;
import com.example.myapplication.taikhoan.NguoiDungActivity;
import com.example.myapplication.thuvien.ExpandableHeightGridView;
import com.example.myapplication.entity.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class select extends AppCompatActivity {
    private ImageView imageView_caidat;
    private ImageView imageView_trove, sort;
    private LinearLayout linear_thongbao, linear_giohang, linear_toi, linear_trangchu, sapxeptheogia;
    private EditText inputText;
    private ExpandableHeightGridView gridView;
    private AdapterSanPham adapterSanPham;
    private ArrayList<SanPham> listSanPham;
    private ArrayList<SanPham> listSPLuuTru;
    private boolean sortGiaTF;
    private Spinner danhmuc;
    private AdapterDanhMuc adapterDanhMuc;
    private ArrayList<DanhMuc> danhMucArrayList;
    private String danhMucId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        anhXa();
        nutNhan();
        onClick();
        doDuLieuVaoAdapter();
        doDuLieu();
        onClickGridView();
        sortGia();

        danhMuc();
        doDuLieuVaoDanhMuc();
        getDanhMuc();
    }

    private void onClick(){
        linear_trangchu.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), activity_trangchu.class)));
        linear_thongbao.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), thongbao.class)));
        linear_giohang.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), GioHang.class)));
        linear_toi.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), NguoiDungActivity.class)));
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


    private void getDanhMuc() {
        danhmuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                danhMucId = String.valueOf(danhMucArrayList.get(i).getId());
                String keySelect = inputText.getText().toString().trim();
                ArrayList<SanPham> timkiem = new ArrayList<>();
                if (!keySelect.isEmpty()){
                    String query = keySelect.toLowerCase().trim();
                    for (SanPham s : listSPLuuTru){
                        boolean isMatch = true;
                        String[] keys = query.split("\\s+");
                        for(String key : keys){
                            if(!s.getName().toLowerCase().contains(key)){
                                isMatch = false;
                                break;
                            }
                        }
                        if (isMatch){
                            timkiem.add(s);
                        }
                    }
                }
                else{
                    timkiem.addAll(listSPLuuTru);
                }
                CheckDanhMuc(danhMucId, timkiem);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void CheckDanhMuc(String danhmucID, ArrayList<SanPham> listDf){
        if (!danhmucID.equals("0")){
            ArrayList<SanPham> sanPhamArrayListDanhMuc = new ArrayList<>();
            for (SanPham s : listDf){
                if (s.getIddanhmuc().equals(danhmucID)){
                    sanPhamArrayListDanhMuc.add(s);
                }
            }
            listSanPham.clear();
            listSanPham.addAll(sanPhamArrayListDanhMuc);
        }
        else{
            listSanPham.clear();
            listSanPham.addAll(listDf);
        }
        funSort(sortGiaTF);
    }

    private void danhMuc() {
        danhMucId = "0";
        danhMucArrayList = new ArrayList<>();
        adapterDanhMuc = new AdapterDanhMuc(select.this, R.layout.layout_select_dropdown, danhMucArrayList);
        danhmuc.setAdapter(adapterDanhMuc);
    }

    private void doDuLieuVaoDanhMuc() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("DanhMuc");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                danhMucArrayList.clear();
                danhMucArrayList.add(new DanhMuc(0, "All"));
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    DanhMuc danhMuc = postSnapshot.getValue(DanhMuc.class);
                    danhMucArrayList.add(danhMuc);
                }
                adapterDanhMuc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(select.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doDuLieuVaoAdapter() {
        listSanPham = new ArrayList<>();
        adapterSanPham = new AdapterSanPham(this, R.layout.layout_item, listSanPham);
        gridView.setAdapter(adapterSanPham);
//        listTimKiem = new ArrayList<>(listSanPham);
//        adapterSanPham = new AdapterSanPham(this, R.layout.layout_item, listTimKiem);
//        gridView.setAdapter(adapterSanPham);
    }

    private void doDuLieu() {
        sortGiaTF = false;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("SanPham");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSanPham.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    SanPham sanPham = postSnapshot.getValue(SanPham.class);
                    listSanPham.add(sanPham);
                }
                listSPLuuTru.clear();
                listSPLuuTru.addAll(listSanPham);
                adapterSanPham.notifyDataSetChanged();
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
    private void filter(@NonNull CharSequence text){
        ArrayList<SanPham> listTimKiem = new ArrayList<>();
        String query = text.toString().toLowerCase().trim();
        if(query.isEmpty()){
            listTimKiem.addAll(listSPLuuTru);
        }else {
            for(SanPham sanPham : listSPLuuTru){
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
        CheckDanhMuc(danhMucId, listTimKiem);
//        listSanPham.clear();
//        listSanPham.addAll(listTimKiem);
//        adapterSanPham.notifyDataSetChanged();
    }
    private void funSort(boolean tmp){
        for (int i = 0; i < listSanPham.size() - 1; i++){
            for (int t = i + 1; t < listSanPham.size(); t++){
                if (listSanPham.get(i).getDongia() < listSanPham.get(t).getDongia() != tmp){
                    SanPham tmpSp = listSanPham.get(i);
                    listSanPham.set(i, listSanPham.get(t));
                    listSanPham.set(t, tmpSp);
                }
            }
        }
        adapterSanPham.notifyDataSetChanged();
    }

    private void sortGia(){
        sapxeptheogia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortGiaTF = !sortGiaTF;
                funSort(sortGiaTF);
                float rotation = sort.getRotation();
                sort.setRotation(rotation + 180 - 360);
            }
        });
    }

    private void anhXa(){
        gridView = (ExpandableHeightGridView) findViewById(R.id.listSanPham);
        gridView.setExpanded(true);
        imageView_caidat = findViewById(R.id.img_caidat);
        imageView_trove = findViewById(R.id.img_trove);
        linear_giohang = findViewById(R.id.f_giohang);
        linear_toi = findViewById(R.id.f_toi);
        linear_thongbao = findViewById(R.id.f_thongbao);
        linear_trangchu = findViewById(R.id.f_muasam);
        inputText = findViewById(R.id.editTextInput);
        listSPLuuTru = new ArrayList<>();
        sapxeptheogia = findViewById(R.id.sapxeptheogia);
        sort = findViewById(R.id.sort);
        danhmuc = findViewById(R.id.danhmuc);
    }

}