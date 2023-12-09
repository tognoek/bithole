package com.example.myapplication.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.adapter.AdapterDanhMuc;
import com.example.myapplication.entity.DanhMuc;
import com.example.myapplication.shop.detail_shop;
import com.example.myapplication.thuvien.CustomProgressDialog;
import com.example.myapplication.thuvien.PublicFunciton;
import com.example.myapplication.entity.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.io.IOException;
import java.util.ArrayList;

public class DangSanPhamActivity extends AppCompatActivity {
    private ImageView imageView_trove, imageView, imageView_qlbaidang;
    private EditText editTextName, editTextMoTa, editTextDonGia, editTextSoLuong;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private TextView buttonPush;
    private CustomProgressDialog customProgressDialog;
    private Spinner danhmuc;
    private AdapterDanhMuc adapterDanhMuc;
    private ArrayList<DanhMuc> danhMucArrayList;
    private String danhMucId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_san_pham);

        anhXa();
        onClick();
        loadImage();
        onClickPush();
        danhMuc();
        doDuLieuVaoDanhMuc();
        getDanhMuc();
    }

    private void getDanhMuc() {
        danhmuc.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                danhMucId = String.valueOf(danhMucArrayList.get(i).getId());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void danhMuc() {
        danhMucId = "1";
        danhMucArrayList = new ArrayList<>();
        adapterDanhMuc = new AdapterDanhMuc(DangSanPhamActivity.this, R.layout.layout_select_dropdown, danhMucArrayList);
        danhmuc.setAdapter(adapterDanhMuc);
    }

    private void doDuLieuVaoDanhMuc() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("DanhMuc");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                danhMucArrayList.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    DanhMuc danhMuc = postSnapshot.getValue(DanhMuc.class);
                    danhMucArrayList.add(danhMuc);
                }
                adapterDanhMuc.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(DangSanPhamActivity.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void loadImage(){
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }

        });
    }

    private String createNameImage(int idProduct){
        String retur = "imageProduct" + String.valueOf(idProduct);
        return retur;
    }
    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    private void upLoadImageFireBase(int idProdcut){
        FirebaseStorage.getInstance("gs://thong-ab907.appspot.com").getReference("image/" + createNameImage(idProdcut)).putFile(filePath).addOnCompleteListener(nothing -> {
            Log.d("Uploaded", "onActivityResult: OK");
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null )
        {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    private void pushProductFunction() {
        //Set Values Product
        customProgressDialog = new CustomProgressDialog(this);
        String name = editTextName.getText().toString().trim();
        String mota = editTextMoTa.getText().toString().trim();
        double dongia = Double.parseDouble(editTextDonGia.getText().toString().trim());
        int soluong = Integer.parseInt(editTextSoLuong.getText().toString().trim());
        SanPham sanPham = new SanPham();

        //Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("SanPham");
        customProgressDialog.show();
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                sanPham.setId(Integer.parseInt(String.valueOf(snapshot.getChildrenCount())) + 1);
                Log.d("Size listSanPham", "Size: " + snapshot.getChildrenCount());
                sanPham.setName(name);
                sanPham.setMota(mota);
                sanPham.setDongia(dongia);
                if (filePath != null){
                    sanPham.setHinhanh(createNameImage(sanPham.getId()));
                    upLoadImageFireBase(sanPham.getId());
                }
                else{
                    sanPham.setHinhanh("null");
                }
                sanPham.setSoluong(soluong);
                sanPham.setIdshop(PublicFunciton.getIdUser());
                sanPham.setIddanhmuc(danhMucId);
                int pathObject = sanPham.getId();
                databaseReference.child(String.valueOf(pathObject)).setValue(sanPham);
                customProgressDialog.dismiss();
                Toast.makeText(DangSanPhamActivity.this, "Đăng bài thành công", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void onClickPush(){
        buttonPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pushProductFunction();
            }
        });
    }
    private void onClick(){
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed());
        imageView_qlbaidang.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), activity_quanlybaidang.class)));
    }



    private void anhXa(){
        filePath = null;
        imageView_trove = findViewById(R.id.img_trove);
        editTextName = findViewById(R.id.name);
        editTextMoTa = findViewById(R.id.mota);
        editTextDonGia = findViewById(R.id.donGia);
        buttonPush = findViewById(R.id.push);
        imageView = findViewById(R.id.imageview);
        imageView_qlbaidang = findViewById(R.id.img_qldangbai);
        editTextSoLuong = findViewById(R.id.soluong);
        danhmuc = findViewById(R.id.danhmuc);
    }
}