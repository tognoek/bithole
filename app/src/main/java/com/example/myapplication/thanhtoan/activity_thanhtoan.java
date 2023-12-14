package com.example.myapplication.thanhtoan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.GioHang;
import com.example.myapplication.R;
import com.example.myapplication.adapter.AdapterVoucher;
import com.example.myapplication.entity.User;
import com.example.myapplication.entity.VoucherItem;
import com.example.myapplication.taikhoan.MainActivity;
import com.example.myapplication.thuvien.ExpandableHeightGridView;
import com.example.myapplication.thuvien.FormatVND;
import com.example.myapplication.thuvien.PublicFunciton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activity_thanhtoan extends AppCompatActivity {
    private Button momo, newVoucher;
    private ScrollView scrollvoucher;
    private ExpandableHeightGridView gripview;
    private TextView selectvoucher, texttongtien, textsoluong, texttientra;
    private AdapterVoucher adapterVoucher;
    private ArrayList<VoucherItem> voucherItemArrayList;
    private int tongtien, tiengiam, tientra, soluong;
    private EditText nameuser, phonenumberuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanhtoan);

        anhXa();
        getDetail();
        update();
        chonVoucher();
        chuanbi();
        dodulieu();
        adpaterclick();
        momo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("Money").child(PublicFunciton.getIdUser());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()){
                            Integer tienUser = snapshot.getValue(Integer.class);
                            if (tientra > tienUser){
                                Toast.makeText(getApplicationContext(), "Tài khoản bạn không đủ tiền", Toast.LENGTH_SHORT).show();
                            }else{
                                dathang();
                                PublicFunciton.updateTien(tientra * (-1));
                            }
                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Tài khoản bạn không đủ tiền", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }

        });
        onClick();
    }
    private void dathang() {
        Intent intent = getIntent();
        if (intent.hasExtra("listsoluong")){
            ArrayList<Integer> listCards;
            listCards = intent.getIntegerArrayListExtra("listcart");
            ArrayList<Integer> listsoluong;
            listsoluong = intent.getIntegerArrayListExtra("listsoluong");
            if (listCards == null || listsoluong == null){
                Log.d("Get Intent", "fail");
            }else{
                Log.d("Size", "Size: " + listCards.size() + " " + listsoluong.size());
                Intent intent1 = new Intent(activity_thanhtoan.this, thucThiThanhToan.class);
                intent1.putExtra("tong", tientra);
                intent1.putExtra("listcart", listCards);
                intent1.putExtra("listsoluong", listsoluong);
                startActivity(intent1);
                finish();
            }
        }
        else{
            Log.d("Get Intent", "fail");
        }
    }
    private void update(){
        tientra = tongtien - tiengiam;
        tientra = Math.max(tientra, 0);
        texttientra.setText(new FormatVND(String.valueOf(tientra)).getVND());
    }

    private void getDetail() {
        Intent intent = getIntent();
        tongtien = intent.getIntExtra("tong", 110);
        soluong = intent.getIntExtra("soluong", 0);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(PublicFunciton.getIdUser());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                nameuser.setText(user.getTen());
                phonenumberuser.setText(user.getSdt());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        tiengiam = 0;
        texttientra.setText(new FormatVND(String.valueOf(tongtien)).getVND());
        texttongtien.setText(new FormatVND(String.valueOf(tongtien)).getVND());
        textsoluong.setText(String.valueOf(soluong));
    }

    private void adpaterclick() {
        gripview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                VoucherItem voucherItem = voucherItemArrayList.get(i);
                selectvoucher.setText(voucherItem.getName());
                if (voucherItem.getLoai() == 0){
                    if (tongtien >= voucherItem.getMinGia()){
                        tiengiam = voucherItem.getGiaGiam();
                    }else{
                        Toast.makeText(activity_thanhtoan.this, "Đơn hàng chưa đủ yêu cầu", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    if (tongtien >= voucherItem.getMinGia()){
                        tiengiam = tongtien / 100 * voucherItem.getGiaGiam();
                        if (tiengiam > voucherItem.getMaxGia()){
                            tiengiam = voucherItem.getMaxGia();
                        }
                    }else{
                        Toast.makeText(activity_thanhtoan.this, "Đơn hàng chưa đủ yêu cầu", Toast.LENGTH_SHORT).show();
                    }
                }
                update();
                scrollvoucher.setVisibility(View.GONE);
            }
        });
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

    private void chuanbi() {
        voucherItemArrayList = new ArrayList<>();
        adapterVoucher = new AdapterVoucher(this, R.layout.layout_voucher, voucherItemArrayList);
        gripview.setAdapter(adapterVoucher);
    }

    private void chonVoucher() {
        newVoucher.setOnClickListener(v -> {
            if (scrollvoucher.getVisibility() == View.GONE){
                scrollvoucher.setVisibility(View.VISIBLE);
            }
            else{
                scrollvoucher.setVisibility(View.GONE);
            }
        });
    }

    private void momoClick() {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo("vn.momo", PackageManager.GET_ACTIVITIES);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("momo://"));
            startActivity(intent);

        } catch (PackageManager.NameNotFoundException e) {
            Log.d("momoclick", "momoClick: Fail");
        }
    }

    private void onClick() {
        findViewById(R.id.icback).setOnClickListener(view ->{
                    startActivity(new Intent(getApplicationContext(), GioHang.class));
                    finish();
                }
                );
    }

    private void anhXa() {
        momo = findViewById(R.id.thanhtoan_momo);
        newVoucher = findViewById(R.id.newVoucher);
        scrollvoucher = findViewById(R.id.scrollvoucher);
        selectvoucher = findViewById(R.id.selectvoucher);
        texttongtien = findViewById(R.id.tongtien);
        textsoluong = findViewById(R.id.soluong);
        texttientra = findViewById(R.id.tientra);
        nameuser = findViewById(R.id.nameuser);
        phonenumberuser = findViewById(R.id.phonenumberuser);
        gripview = (ExpandableHeightGridView) findViewById(R.id.voucher);
        gripview.setExpanded(true);
    }
}