package com.example.myapplication;

import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_REF;
import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_USER_REF;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.adapter.AdapterComment;
import com.example.myapplication.adapter.AdapterSanPham;
import com.example.myapplication.entity.ComMent;
import com.example.myapplication.shop.detail_shop;
import com.example.myapplication.thuvien.CustomProgressDialog;
import com.example.myapplication.thuvien.ExpandableHeightGridView;
import com.example.myapplication.thuvien.FormatTime;
import com.example.myapplication.thuvien.FormatVND;
import com.example.myapplication.entity.ListCard;
import com.example.myapplication.thuvien.PublicFunciton;
import com.example.myapplication.entity.SanPham;
import com.example.myapplication.entity.Shop;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class detail extends AppCompatActivity {
    private ImageView imageView_trove, imageView_clickDanhgia, imageshop, image1, image2, image3;
    private LinearLayout linear_xemshop, linear_muangay, liner_giohang, liner_danhgia;
    private FrameLayout frame_vaogiohang;
    private TextView name, mota, dongia, soluong, shop, textView_soluongtronggio;
    private EditText binhluan;
    private Button dangbinhluan;
    private SanPham sanPham;
    private ExpandableHeightGridView gridView, gridViewComment;
    private AdapterSanPham adapterSanPham;
    private AdapterComment adapterComment;
    private ArrayList<SanPham> listSanPham;
    private ArrayList<ComMent> listComMent;
    private ArrayList<ListCard> listCards;
    private CustomProgressDialog customProgressDialog;
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
        xemShop();
        danhGia();
        dangBinhLuan();

        //AddProductToCart
        gioHang();
        getSizeCartUser();
        adapterComment.setHanderButton(new AdapterComment.HanderButton() {
            @Override
            public void setOnlickLike(int positon) {
                Log.d("aaaa", "setOnlickLike: " + positon);
            }
        });

    }

    private void xemShop() {
        linear_xemshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(detail.this, detail_shop.class);
                intent.putExtra("shop", new Shop(sanPham.getIdshop()));
                startActivity(intent);
            }
        });
    }

    private void gioHang() {
        liner_giohang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCart();
            }

        });
    }

    private void danhGia() {
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
    }

    private void dangBinhLuan() {
        dangbinhluan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noidung= binhluan.getText().toString().trim();
                LocalDateTime currentDateTime = LocalDateTime.now();

                // Format the date and time (optional)
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String formattedDateTime = currentDateTime.format(formatter);
                String time = new FormatTime(formattedDateTime).getTime();
                ComMent comMent = new ComMent(PublicFunciton.getNameUser(), noidung, PublicFunciton.getIdUser(), time);
                addComMent(comMent);

                binhluan.setText("");
            }
        });
    }

    private void getSizeCartUser(){
        String idUser = PublicFunciton.getIdUser();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("GioHang");
        DatabaseReference databaseReferenceCart = databaseReference.child(idUser);
        databaseReferenceCart.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<ListCard> listNumberCards = new ArrayList<>();
                for (DataSnapshot snapshotvalues : snapshot.getChildren()){
                    ListCard listCard = snapshotvalues.getValue(ListCard.class);
                    listNumberCards.add(listCard);
                }
                textView_soluongtronggio.setText(String.valueOf(listNumberCards.size()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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
        customProgressDialog.show();
        databaseReferenceUser.addListenerForSingleValueEvent (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int i = 0;
                boolean Update = false;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ListCard card = postSnapshot.getValue(ListCard.class);
                    listCards.add(card);
                    assert card != null;
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
                customProgressDialog.dismiss();
                Toast.makeText(detail.this, "Đã thêm thành công sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(detail.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setProductDetail() {
        name.setText(sanPham.getName());
        mota.setText(sanPham.getMota());
        dongia.setText((new FormatVND(String.valueOf(sanPham.getDongia())).getVND()));
        soluong.setText("Số lượng: " + sanPham.getSoluong());
        PRODUCT_IMAGE_USER_REF.child(sanPham.getIdshop()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (uri != null){
                    Picasso.get().load(uri).into(imageshop);
                }
            }
        });
        PRODUCT_IMAGE_REF.child(sanPham.getHinhanh()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                RequestCreator uriImage = Picasso.get().load(uri);
                uriImage.into(image1);
                Drawable drawable = image1.getDrawable();

                if (drawable != null) {
                    Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
                    image2.setImageBitmap(bitmap);
                }
            }
        });

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User");
        DatabaseReference user = databaseReference.child(sanPham.getIdshop());
        user.child("ten").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.d("firebase", "Error getting data", task.getException());
                }
                else {
                    Log.d("name Shop", "onComplete: " + task.getResult().getValue());
                    shop.setText(String.valueOf(task.getResult().getValue()));
//                    returnString = String.valueOf(task.getResult().getValue());
                }
            }
        });
    }

    private void getDetail() {
        Intent intent = getIntent();
        sanPham =  (SanPham) intent.getSerializableExtra("SanPham");
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

    private void onClick() {
        frame_vaogiohang.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), GioHang.class))
        );

        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );
        findViewById(R.id.f_giohang).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), GioHang.class)));
        findViewById(R.id.muangay).setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), activity_thanhtoan.class)));
        linear_muangay.setOnClickListener(view -> startActivity(new Intent(getApplicationContext(), activity_trangchu.class)));
    }
    private void anhXa(){
        customProgressDialog = new CustomProgressDialog(this);
        gridView = (ExpandableHeightGridView) findViewById(R.id.listSanPham);
        gridView.setExpanded(true);
        gridViewComment = (ExpandableHeightGridView) findViewById(R.id.gridComment);
        gridViewComment.setExpanded(true);
        linear_muangay = findViewById(R.id.f_muasam);
        frame_vaogiohang = findViewById(R.id.img_giohang);
        imageView_trove = findViewById(R.id.img_trove);
        linear_xemshop = findViewById(R.id.xemshop);
        name = findViewById(R.id.namePrd);
        mota = findViewById(R.id.mota);
        dongia = findViewById(R.id.dongia);
        soluong = findViewById(R.id.soluong);
        shop = findViewById(R.id.text_name_shop);
        liner_giohang = findViewById(R.id.f_giohang);
        imageView_clickDanhgia = findViewById(R.id.clickDanhgia);
        liner_danhgia = findViewById(R.id.space11);
        binhluan = findViewById(R.id.noidungBL);
        dangbinhluan = findViewById(R.id.btnDangBL);
        imageshop = findViewById(R.id.imageshop);
        image1 = findViewById(R.id.image1);
        image2 = findViewById(R.id.image2);
        image3 = findViewById(R.id.image3);
        textView_soluongtronggio = findViewById(R.id.soluong_giohang);
    }

}