package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Interface.OnItemChangeListener;
import com.example.myapplication.Interface.OnItemCheckedChangeListener;
import com.example.myapplication.adapter.AdapterCart;
import com.example.myapplication.entity.Shop;
import com.example.myapplication.shop.detail_shop;
import com.example.myapplication.thanhtoan.activity_thanhtoan;
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

public class GioHang extends AppCompatActivity implements OnItemCheckedChangeListener, OnItemChangeListener {

    private ImageView imageView_trove;
    private TextView textView_tongtien;
    private LinearLayout linear_thanhtoan, khongsanpham;
    private ExpandableHeightGridView gridView;
    private AdapterCart adapterSanPham;
    private ArrayList<Cart> listSanPham;
    private double tong;
    private int soluong;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        anhXa();
        onClick();
        doDuLieuVaoAdapter();
        layMaNguoiDung();

        thanhToan();

    }

    private void doDuLieuVaoAdapter() {
        listSanPham = new ArrayList<>();
        adapterSanPham = new AdapterCart(this, R.layout.layout_cart, listSanPham);
        adapterSanPham.setOnItemCheckedChangeListener(this);
        adapterSanPham.setOnItemChangeListener(this);
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
                if (listCards.isEmpty()){
                    khongsanpham.setVisibility(View.VISIBLE);
                }else{
                    khongsanpham.setVisibility(View.GONE);
                    doDuLieu(listCards);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GioHang.this, "Fail GetId", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void tongTien(){
        tong = adapterSanPham.tongTien();
        soluong = adapterSanPham.soLuong();
        Log.d("aaa", "tongTien: " + tong);
        String tongString = new FormatVND(String.valueOf(tong)).getVND();
        textView_tongtien.setText(tongString);
    }

    private void doDuLieu(List<ListCard> listCards) {
        listSanPham.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("SanPham");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    SanPham sanPham = postSnapshot.getValue(SanPham.class);
                    if (sanPham != null){
                        for (int t = 0; t < listCards.size(); t++){
                            if (sanPham.getId() == listCards.get(t).getId()){
                                Cart cart = new Cart(sanPham.getId(), sanPham.getName(), sanPham.getMota(), sanPham.getDongia(), listCards.get(t).getSoluong(), sanPham.getHinhanh(), sanPham.getIdshop(), false);
                                listSanPham.add(cart);
                            }
                        }

                    }
                }
                adapterSanPham.setOnDataSetChanged(() -> tongTien());
                adapterSanPham.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(GioHang.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void thanhToan(){
        linear_thanhtoan.setOnClickListener(view ->{
            if (tong == 0){
                Toast.makeText(getApplicationContext(), "Bạn chưa chọn sản phẩm nào",Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(getApplicationContext(), activity_thanhtoan.class);
                intent.putExtra("tong", (int) tong);
                intent.putExtra("soluong", soluong);
                intent.putExtra("listcart", adapterSanPham.getListCard());
                intent.putExtra("listsoluong", adapterSanPham.getListSoLuong());
                startActivity(intent);
                finish();
            }
        });
    }
    private void onClick(){
        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );
    }

    private void anhXa(){
        gridView = (ExpandableHeightGridView) findViewById(R.id.listSanPham);
        gridView.setExpanded(true);
        linear_thanhtoan = findViewById(R.id.thanhtoan);
        imageView_trove = findViewById(R.id.img_trove);
        textView_tongtien = findViewById(R.id.tongtien);
        khongsanpham = findViewById(R.id.khongsanpham);
    }

    @Override
    public void onItemCheckedChanged(int position, boolean isChecked) {
        tongTien();
    }

    @Override
    public void onItemChanged(int position, int check) {
        switch (check){
            case 1 -> {
                Intent intent = new Intent(GioHang.this, detail_shop.class);
                intent.putExtra("shop", new Shop(listSanPham.get(position).getIdShop()));
                startActivity(intent);

            }
            case 2 -> {
                Intent intent = new Intent(GioHang.this, detail.class);
                Cart cart = listSanPham.get(position);
                intent.putExtra("SanPham", new SanPham(cart.getId(), cart.getName(), cart.getMota(), cart.getDongia(), cart.getSoluong(), cart.getHinhanh(), cart.getIdShop(), "1"));
                startActivity(intent);
            }
        }
    }
}