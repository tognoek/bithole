package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.AdapterCart;
import com.example.myapplication.thuvien.ExpandableHeightGridView;
import com.example.myapplication.thuvien.FormatVND;
import com.example.myapplication.entity.ListCard;
import com.example.myapplication.thuvien.PublicFunciton;
import com.example.myapplication.entity.SanPham;
import com.example.myapplication.entity.Cart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GioHang extends AppCompatActivity {

    private ImageView imageView_trove;
    private TextView textView_tongtien;
    private LinearLayout linear_thanhtoan;
    private ExpandableHeightGridView gridView;
    private AdapterCart adapterSanPham;
    private ArrayList<Cart> listSanPham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        anhXa();
        onClick();
        doDuLieuVaoAdapter();
        layMaNguoiDung();

    }

    private void doDuLieuVaoAdapter() {
        listSanPham = new ArrayList<>();
        adapterSanPham = new AdapterCart(this, R.layout.layout_cart, listSanPham);
        gridView.setAdapter(adapterSanPham);
    }

    private void layMaNguoiDung(){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("GioHang").child(PublicFunciton.getIdUser());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<ListCard> listCards = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()){
                    ListCard listCard = postSnapshot.getValue(ListCard.class);
                    listCards.add(listCard);
                }
                doDuLieu(listCards);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GioHang.this, "Fail GetId", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tongTien(){
        double tong = 0;
        for (Cart sanPham :listSanPham){
            tong += sanPham.getSoluong() * sanPham.getDongia();
        }
        Log.d("aaa", "tongTien: " + tong);
        String tongString = new FormatVND(String.valueOf(tong)).getVND();
        textView_tongtien.setText(tongString);
    }

    private void doDuLieu(List<ListCard> listCards) {
        listSanPham.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("SanPham");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int k = 0;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    SanPham sanPham = postSnapshot.getValue(SanPham.class);
                    if (sanPham != null){
                        for (int t = 0; t < listCards.size(); t++){
                            if (sanPham.getId() == listCards.get(t).getId()){
                                Cart cart = new Cart(sanPham.getId(), sanPham.getName(), sanPham.getMota(), sanPham.getDongia(), listCards.get(t).getSoluong(), sanPham.getHinhanh(), sanPham.getShop());
                                listSanPham.add(cart);
                            }
                        }

                    }
                }
                tongTien();
                adapterSanPham.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GioHang.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void onClick(){
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );
        linear_thanhtoan.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), activity_thanhtoan.class)));
    }

    private void anhXa(){
        gridView = (ExpandableHeightGridView) findViewById(R.id.listSanPham);
        gridView.setExpanded(true);
        linear_thanhtoan = findViewById(R.id.thanhtoan);
        imageView_trove = findViewById(R.id.img_trove);
        textView_tongtien = findViewById(R.id.tongtien);
    }
}