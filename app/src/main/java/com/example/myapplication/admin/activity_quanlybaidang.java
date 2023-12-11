package com.example.myapplication.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Interface.OnItemChangeListener;
import com.example.myapplication.R;
import com.example.myapplication.activity_trangchu;
import com.example.myapplication.adapter.AdapterSanPham;
import com.example.myapplication.adapter.AdapterSanPhamQuanLy;
import com.example.myapplication.detail;
import com.example.myapplication.entity.SanPham;
import com.example.myapplication.thuvien.ExpandableHeightGridView;
import com.example.myapplication.thuvien.PublicFunciton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class activity_quanlybaidang extends AppCompatActivity implements OnItemChangeListener {

    private ImageView imageView_trove;
    private LinearLayout linear_dangbai, linear_doanhthu, linear_nhapxuat;
    private ExpandableHeightGridView expandableHeightGridView;
    private AdapterSanPhamQuanLy adapterSanPhamQuanLy;
    private ArrayList<SanPham> listSanPham;
    private String idShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quanlybaidang);

        anhXa();
        oneClick();
        doDuLieuVaoAdapter();
        doDuLieu();

    }
    private void doDuLieuVaoAdapter() {
        idShop = PublicFunciton.getIdUser();
        listSanPham = new ArrayList<>();
        adapterSanPhamQuanLy = new AdapterSanPhamQuanLy(this, R.layout.layout_item_quanly, listSanPham);
        adapterSanPhamQuanLy.setOnItemChangeListener(this);
        expandableHeightGridView.setAdapter(adapterSanPhamQuanLy);
    }

    private void doDuLieu() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("SanPham");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSanPham.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    SanPham sanPham = postSnapshot.getValue(SanPham.class);
                    if (sanPham != null){
                        if (sanPham.getIdshop().equals(idShop)){
                            listSanPham.add(sanPham);
                        }
                    }
                }
                adapterSanPhamQuanLy.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(activity_quanlybaidang.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemChanged(int position, int check) {
        switch (check) {
            case 1 -> {
                Log.d("check", "Value: " + check);
                Intent intent = new Intent(activity_quanlybaidang.this, detail.class);
                intent.putExtra("SanPham", listSanPham.get(position));
                startActivity(intent);
            }
            case 2 -> {
                Intent intent = new Intent(activity_quanlybaidang.this, UpdateDetailProduct.class);
                intent.putExtra("SanPham", listSanPham.get(position));
                startActivity(intent);
                finish();
                Log.d("check", "Value: " + check);
            }
            case 3 -> {
                openYesNo("Bạn có muốn xóa sản phẩm này không?", position);
                Log.d("check", "Value: " + check);
            }
            default -> {
            }
        }
    }

    private void openYesNo(String text, int position){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView( R.layout.layout_yes_no);

        Window window = dialog.getWindow();
        if (window != null){
            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            WindowManager.LayoutParams windowAttributes = window.getAttributes();
            windowAttributes.gravity = Gravity.CENTER;
            window.setAttributes(windowAttributes);

            dialog.setCancelable(true);


        }
        dialog.show();

        TextView textView = dialog.findViewById(R.id.textView);
        textView.setText(text);

        Button buttonYes = dialog.findViewById(R.id.yes);
        Button buttonNo = dialog.findViewById(R.id.no);
        buttonYes.setOnClickListener(v -> {
            XoaSanPham(position);
            dialog.dismiss();
        });
        buttonNo.setOnClickListener(v -> dialog.dismiss());
    }

    private void XoaSanPham(int position) {
        int idSanPhamDelete = listSanPham.get(position).getId();
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference childRemove = firebaseDatabase.getReference("SanPham").child(String.valueOf(idSanPhamDelete));
        childRemove.removeValue().addOnSuccessListener(v -> Toast.makeText(activity_quanlybaidang.this, "Đã xóa sán phẩm", Toast.LENGTH_SHORT).show());
    }

    private void oneClick() {
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );

        linear_dangbai.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), DangSanPhamActivity.class))
        );

        linear_doanhthu.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), thongkedoanhthu.class))
        );

        linear_nhapxuat.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), thongke.class))
        );
    }
    private void anhXa(){
        expandableHeightGridView = (ExpandableHeightGridView)findViewById(R.id.listSanPham);
        expandableHeightGridView.setExpanded(true);
        imageView_trove = findViewById(R.id.img_trove);
        linear_dangbai = findViewById(R.id.f_dangbai);
        linear_doanhthu = findViewById(R.id.f_doanhthu_k);
        linear_nhapxuat = findViewById(R.id.f_nhapxuat);
    }

}