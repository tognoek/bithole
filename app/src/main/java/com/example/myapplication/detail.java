package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.AdapterComment;
import com.example.myapplication.adapter.AdapterSanPham;
import com.example.myapplication.thuvien.ComMent;
import com.example.myapplication.thuvien.ExpandableHeightGridView;
import com.example.myapplication.thuvien.FormatVND;
import com.example.myapplication.thuvien.ListCard;
import com.example.myapplication.thuvien.PublicFunciton;
import com.example.myapplication.thuvien.SanPham;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class detail extends AppCompatActivity {
    private ImageView imageView_caidat, imageView_trove, imageView_clickDanhgia;
    private LinearLayout linear_xemshop, linear_muangay, liner_giohang, liner_danhgia;
    private TextView name, mota, dongia, soluong, shop;
    private EditText binhluan;

    private Button dangbinhluan;
    private SanPham sanPham;
    private ExpandableHeightGridView gridView, gridViewComment;
    private AdapterSanPham adapterSanPham;
    private AdapterComment adapterComment;
    private ArrayList<SanPham> listSanPham;
    private ArrayList<ComMent> listComMent;
    private ArrayList<ListCard> listCards;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        anhXa();
        onClick();
        //Get Product Detail
        getDetail();

        //Set Product Detail
        setProductDetail();

        //GridView
        doDuLieuVaoAdapter();
        doDuLieu();
        onClickGridView();
        doDuLieuVaoAdapterComMent();
        comMent();

        //AddProductToCart
        liner_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCart();
            }

        });
        imageView_clickDanhgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(liner_danhgia.getVisibility() == View.VISIBLE){
                    liner_danhgia.setVisibility(View.GONE);
                }
                else
                {
                    liner_danhgia.setVisibility(View.VISIBLE);
                }
            }
        });

        dangBinhLuan();

    }

    private void dangBinhLuan() {
        dangbinhluan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noidung= binhluan.getText().toString().trim();
                ComMent comMent = new ComMent(PublicFunciton.getNameUser(), noidung);
                addComMent(comMent);
            }
        });
    }

    private void addComMent(ComMent comMent) {
        int idProduct = sanPham.getId();
        listComMent.clear();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("BinhLuan");
        DatabaseReference databaseReferenceUser = databaseReference.child(String.valueOf(idProduct));
        databaseReferenceUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ComMent comMent = postSnapshot.getValue(ComMent.class);
                    listComMent.add(comMent);
                }
                listComMent.add(comMent);
                databaseReference.child(String.valueOf(idProduct)).child(String.valueOf(listComMent.size())).setValue(comMent);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(detail.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addCart() {
        String idUser = PublicFunciton.getIdUser();
        int idProduct = sanPham.getId();
        listCards = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("GioHang");
        DatabaseReference databaseReferenceUser = databaseReference.child(idUser);

        databaseReferenceUser.addListenerForSingleValueEvent (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                boolean Update = false;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ListCard card = postSnapshot.getValue(ListCard.class);
                    listCards.add(card);
                    if (card.getId() == idProduct && !Update){
                        Update = true;
                        card.setSoluong(card.getSoluong() + 1);
                        databaseReferenceUser.child(String.valueOf(i)).setValue(card);
                    }
                    i = i + 1;
                }
                if (!Update){
                    ListCard card = new ListCard(idProduct, 1);
                    databaseReferenceUser.child(String.valueOf(i)).setValue(card);
                }
                Toast.makeText(detail.this, "" + listCards.size(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(detail.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
//        for (int i = 0; i < listCards.size(); i++){
//            if (listCards.get(i).getId() == idProduct){
//                ListCard cartAdd = listCards.get(i);
//                cartAdd.setSoluong(listCards.get(i).getSoluong() + 1);
//                databaseReferenceUser.setValue(cartAdd);
//            }
//        }
//        databaseReferenceUser.child(String.valueOf(idProduct)).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (!task.isSuccessful()) {
//                    Toast.makeText(detail.this, "Faild", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    if (task.getResult().getValue() != null){
//                        Toast.makeText(detail.this, "Cập nhật vào giỏ hàng", Toast.LENGTH_SHORT).show();
//                        int soLuong = Integer.parseInt(task.getResult().getValue().toString());
//                        databaseReferenceUser.child(String.valueOf(idProduct)).setValue(soLuong + 1);
//                    }
//                    else{
//                        Toast.makeText(detail.this, "Thêm mới vào giỏ hàng", Toast.LENGTH_SHORT).show();
//                        databaseReferenceUser.child(String.valueOf(idProduct)).setValue(1);
//                    }
//                }
//            }
//        });

    }

    private void setProductDetail() {
        name.setText(sanPham.getName());
        mota.setText(sanPham.getMota());
        dongia.setText((new FormatVND(String.valueOf(sanPham.getDongia())).getVND()));
        soluong.setText("Đã bán: " + String.valueOf(sanPham.getSoluong()));
        shop.setText(sanPham.getShop());
    }

    private void getDetail() {
        Intent intent = getIntent();
        sanPham =  (SanPham) intent.getSerializableExtra("SanPham");
    }

    private void onClick() {
        imageView_caidat.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), CaiDatActivity.class))
        );

        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );

        linear_xemshop.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), detail_shop.class))
        );

        linear_muangay.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), activity_trangchu.class))
        );
        findViewById(R.id.f_giohang).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), GioHang.class)));
        findViewById(R.id.muangay).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), activity_thanhtoan.class)));
    }

    private void onClickGridView() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(detail.this, detail.class);
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
                    listSanPham.add(sanPham);
                }
                adapterSanPham.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(detail.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void doDuLieuVaoAdapterComMent() {
        listComMent = new ArrayList<>();
        adapterComment= new AdapterComment(this, R.layout.layout_comment, listComMent);
        gridViewComment.setAdapter(adapterComment);
    }
    private void comMent() {
        int idProduct = sanPham.getId();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("BinhLuan");
        DatabaseReference databaseReferenceUser = databaseReference.child(String.valueOf(idProduct));
        databaseReferenceUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listComMent.clear();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ComMent comMent = postSnapshot.getValue(ComMent.class);
                    listComMent.add(comMent);
                }
                Log.d("size list comment", "" + listComMent.size());
                adapterComment.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(detail.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void anhXa(){
        gridView = (ExpandableHeightGridView) findViewById(R.id.listSanPham);
        gridView.setExpanded(true);
        gridViewComment = (ExpandableHeightGridView) findViewById(R.id.gridComment);
        gridViewComment.setExpanded(true);
        linear_muangay = findViewById(R.id.f_muasam);
        imageView_caidat = findViewById(R.id.img_caidat);
        imageView_trove = findViewById(R.id.img_trove);
        linear_xemshop = findViewById(R.id.xemshop);
        name = findViewById(R.id.namePrd);
        mota = findViewById(R.id.mota);
        dongia = findViewById(R.id.dongia);
        soluong = findViewById(R.id.soluong);
        shop = findViewById(R.id.textNameShop);
        liner_giohang = findViewById(R.id.f_giohang);
        imageView_clickDanhgia = findViewById(R.id.clickDanhgia);
        liner_danhgia = findViewById(R.id.space11);
        binhluan = findViewById(R.id.noidungBL);
        dangbinhluan = findViewById(R.id.btnDangBL);

    }
}