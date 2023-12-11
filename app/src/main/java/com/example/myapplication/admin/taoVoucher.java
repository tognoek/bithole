package com.example.myapplication.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;

import com.example.myapplication.R;
import com.example.myapplication.entity.VoucherItem;
import com.example.myapplication.taikhoan.NguoiDungActivity;
import com.example.myapplication.thuvien.CustomProgressDialog;
import com.example.myapplication.thuvien.PublicFunciton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class taoVoucher extends AppCompatActivity {

    private EditText name, detail, free, max, min;
    private RadioButton check;
    private Button add, back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tao_voucher);
        anhXa();
        onClick();
        addVoucher();
    }

    private void addVoucher() {
        add.setOnClickListener(v ->{
            CustomProgressDialog customProgressDialog = new CustomProgressDialog(this);
            customProgressDialog.show();
            String nameVoucher = name.getText().toString().trim();
            String detailVoucher = detail.getText().toString().trim();
            String maxGia = max.getText().toString().trim().isEmpty() ? "999999999" :  max.getText().toString().trim();
            String minGia = min.getText().toString().trim().isEmpty() ? "-1" :  min.getText().toString().trim();
            int checkVoucher = check.isChecked() ? 1 : 0;
            int freeVoucher = Integer.parseInt(free.getText().toString().trim());
            FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = firebaseDatabase.getReference("Voucher");
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    VoucherItem voucher = new VoucherItem();
                    voucher.setId(1);
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        VoucherItem voucherItemLst = dataSnapshot.getValue(VoucherItem.class);
                        voucher.setId(voucherItemLst.getId() + 1);
                    }
                    voucher.setName(nameVoucher);
                    voucher.setMota(detailVoucher);
                    voucher.setLoai(checkVoucher);
                    voucher.setGiaGiam(freeVoucher);
                    voucher.setMaxGia(Integer.parseInt(maxGia));
                    voucher.setMinGia(Integer.parseInt(minGia));
                    voucher.setIdShop(PublicFunciton.getIdUser());
                    databaseReference.child(String.valueOf(voucher.getId())).setValue(voucher);
                    customProgressDialog.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        });
    }

    private void onClick() {
        back.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), NguoiDungActivity.class)));
    }

    private void anhXa(){
        name = findViewById(R.id.name);
        detail = findViewById(R.id.detail);
        add = findViewById(R.id.add);
        back = findViewById(R.id.back);
        free = findViewById(R.id.free);
        max = findViewById(R.id.max);
        min = findViewById(R.id.min);
        check = findViewById(R.id.check);
    }
}