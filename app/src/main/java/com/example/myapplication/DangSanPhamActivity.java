package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DangSanPhamActivity extends AppCompatActivity {

    private ImageView imageView_trove;
    private EditText editTextName, editTextMoTa, editTextDonGia;
    private
    List<Integer> listId = new ArrayList<>();
    private TextView buttonPush;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_san_pham);

        anhXa();
        onClick();

        onClickPush();


    }


    private void pushProductFunction(int idProduct) {
        //Set Values Product
        String name = editTextName.getText().toString().trim();
        String mota = editTextMoTa.getText().toString().trim();
        double dongia = Double.parseDouble(editTextDonGia.getText().toString().trim());
        SanPham sanPham = new SanPham();
        int id = idProduct;
        sanPham.setId(id);
        sanPham.setName(name);
        sanPham.setMota(mota);
        sanPham.setDongia(dongia);
        sanPham.setHinhanh("null");
        sanPham.setSoluong(23);
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
        listId.clear();
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
        imageView_trove = findViewById(R.id.img_trove);
        editTextName = findViewById(R.id.name);
        editTextMoTa = findViewById(R.id.mota);
        editTextDonGia = findViewById(R.id.donGia);
        buttonPush = findViewById(R.id.push);
    }
}