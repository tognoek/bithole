package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.thuvien.PublicFunciton;
import com.example.myapplication.thuvien.SanPham;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DangSanPhamActivity extends AppCompatActivity {
    private ImageView imageView_trove, imageView;
    private EditText editTextName, editTextMoTa, editTextDonGia, luuyName, luuyMota, luuyDonGia, editTextSoLuong;
    private TextView loadiamge;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private TextView buttonPush;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_san_pham);

        anhXa();
        onClick();
        loadImage();
        onClickPush();

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

    private void pushProductFunction(int idProduct) {
        //Set Values Product
        String name = editTextName.getText().toString().trim();
        String mota = editTextMoTa.getText().toString().trim();
        double dongia = Double.parseDouble(editTextDonGia.getText().toString().trim());
        int soluong = Integer.parseInt(editTextSoLuong.getText().toString().trim());
        SanPham sanPham = new SanPham();
        int id = idProduct;
        sanPham.setId(id);
        sanPham.setName(name);
        sanPham.setMota(mota);
        sanPham.setDongia(dongia);
        if (filePath != null){
            sanPham.setHinhanh(createNameImage(id));
            upLoadImageFireBase(id);
        }
        else{
            sanPham.setHinhanh("null");
        }
        sanPham.setSoluong(soluong);
        sanPham.setShop(PublicFunciton.getNameUser());

        //Firebase
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("SanPham");
        int pathObject = sanPham.getId();
        databaseReference.child(String.valueOf(pathObject)).setValue(sanPham);
        Toast.makeText(DangSanPhamActivity.this, "Đăng bài thành công", Toast.LENGTH_SHORT).show();
    }

    private void onClickPush(){
        buttonPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFireBase();
            }
        });
    }

    private void getFireBase(){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("SoLuong");
        databaseReference.child("SanPham").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(DangSanPhamActivity.this, "Error getting data", Toast.LENGTH_SHORT).show();
                }
                else {
//                    Toast.makeText(DangSanPhamActivity.this, String.valueOf(task.getResult().getValue()), Toast.LENGTH_SHORT).show();
                    pushProductFunction(Integer.parseInt(String.valueOf(task.getResult().getValue())) + 1);
                    databaseReference.child("SanPham").setValue(Integer.parseInt(String.valueOf(task.getResult().getValue())) + 1);
                }

            }
        });
    }


    private void onClick(){
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed());
    }



    private void anhXa(){
        filePath = null;
        imageView_trove = findViewById(R.id.img_trove);
        editTextName = findViewById(R.id.name);
        editTextMoTa = findViewById(R.id.mota);
        editTextDonGia = findViewById(R.id.donGia);
        buttonPush = findViewById(R.id.push);
        loadiamge = findViewById(R.id.loadiamge);
        imageView = findViewById(R.id.imageview);
        editTextSoLuong = findViewById(R.id.soluong);
    }
}