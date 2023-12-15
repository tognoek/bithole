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
import com.example.myapplication.entity.ThongBao;
import com.example.myapplication.shop.detail_shop;
import com.example.myapplication.thanhtoan.activity_thanhtoan;
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

        MuaNgay();
        adapterComment.setHanderButton(new AdapterComment.HanderButton() {
            @Override
            public void setOnlickLike(int positon) {
                Log.d("vi tri like", "setOnlickLike: " + positon);
//                thongBao(2);
                PublicFunciton.taoThongBao(sanPham.getId(), listComMent.get(positon).getId(), 2);
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
                if (sanPham.getSoluong() > 0){
                    addCart();
                }else{
                    Toast.makeText(detail.this, "Hàng đã hết!! Xin lỗi bạn", Toast.LENGTH_SHORT).show();
                }
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
                String formattedDateTime = PublicFunciton.getDay();
                String time = new FormatTime(formattedDateTime).getTime();
                ComMent comMent = new ComMent(PublicFunciton.getNameUser(), noidung, PublicFunciton.getIdUser(),0,  time);
                addComMent(comMent);
//                thongBao(1);
                PublicFunciton.taoThongBao(sanPham.getId(), sanPham.getIdshop(), 1);
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
                    ComMent comMentTmp = postSnapshot.getValue(ComMent.class);
                    listComMent.add(comMentTmp);
                }
                comMent.setStt(listComMent.size() + 1);
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
                customProgressDialog.dismiss();
                boolean Update = false;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    ListCard card = postSnapshot.getValue(ListCard.class);
                    listCards.add(card);
                    assert card != null;
                    if (card.getId() == idProduct && !Update){
                        Update = true;
                        if (sanPham.getSoluong() > card.getSoluong()){
                            card.setSoluong(card.getSoluong() + 1);
                            databaseReferenceUser.child(String.valueOf(card.getId())).setValue(card);
                            Toast.makeText(detail.this, "Đã thêm thành công sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(detail.this, "Số lượng đơn hàng tương tự của bạn đã vượt quá số lượng còn lại của sản phẩm!!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                if (!Update){
                    ListCard card = new ListCard(idProduct, 1);
                    databaseReferenceUser.child(String.valueOf(card.getId())).setValue(card);
                    Toast.makeText(detail.this, "Đã thêm thành công sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(detail.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });

    }
    private void loadIntoImageView(String imageUrl, ImageView imageView) {
        Picasso.get().load(imageUrl).into(imageView);
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
                loadIntoImageView(String.valueOf(uri), image1);
                loadIntoImageView(String.valueOf(uri), image2);
                loadIntoImageView(String.valueOf(uri), image3);
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
                finish();
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
                ArrayList<SanPham> sanPhamArrayList = new ArrayList<>();
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    SanPham sanPham = postSnapshot.getValue(SanPham.class);
                    sanPhamArrayList.add(sanPham);
                }
                for (SanPham sanPhamTmp : sanPhamArrayList){
                    if (listSanPham.size() > 39){
                        break;
                    }
                    if (sanPhamTmp.getIddanhmuc().equals(sanPham.getIddanhmuc()) && sanPhamTmp.getId() != sanPham.getId()){
                        listSanPham.add(sanPhamTmp);
                    }
                }
                for (SanPham sanPhamTmp : sanPhamArrayList){
                    if (listSanPham.size() > 39){
                        break;
                    }
                    boolean check = false;
                    for (SanPham sanPhamCheck : listSanPham){
                        if (sanPhamCheck.getId() == sanPhamTmp.getId()){
                            check = true;
                            break;
                        }
                    }
                    if (!check && sanPhamTmp.getIdshop().equals(sanPham.getIdshop()) && sanPhamTmp.getId() != sanPham.getId()){
                        listSanPham.add(sanPhamTmp);
                    }
                }
                adapterSanPham.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(detail.this, "Fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void thongBao(int loaiTb){
        int idProduct = sanPham.getId();
        String idNguoiDang = PublicFunciton.getIdUser();
        String ngayBL = PublicFunciton.getDay();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("ThongBao");
        DatabaseReference databaseReferenceTB = databaseReference.child(sanPham.getIdshop());
        databaseReferenceTB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ThongBao thongbao = new ThongBao();
                thongbao.setId(1);
                thongbao.setNgay(new FormatTime(ngayBL).getTimeInteger());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                   ThongBao thongbaoTmp = postSnapshot.getValue(ThongBao.class);
                    assert thongbaoTmp != null;
                    if (loaiTb == thongbaoTmp.getLoaiTb()) {
                       if (thongbaoTmp.getIdNguoiDang().equals(idNguoiDang) && thongbaoTmp.getNgay() == thongbao.getNgay()) {
                           thongbao.setId(thongbaoTmp.getId());
                           break;
                       }
                   }
                    thongbao.setId(thongbaoTmp.getId() + 1);
                }
                thongbao.setIdNguoiDang(idNguoiDang);
                thongbao.setIdSanPham(String.valueOf(idProduct));
                thongbao.setLoaiTb(loaiTb);
                databaseReferenceTB.child(String.valueOf(thongbao.getId())).setValue(thongbao);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void doDuLieuVaoAdapterComMent() {
        listComMent = new ArrayList<>();
        adapterComment= new AdapterComment(this, R.layout.layout_comment, listComMent, sanPham.getId());
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

    private void MuaNgay(){
        findViewById(R.id.muangay).setOnClickListener(view -> {
            if (sanPham.getSoluong() > 0){
                Intent intent = new Intent(getApplicationContext(), activity_thanhtoan.class);
                intent.putExtra("tong", (int) sanPham.getDongia());
                intent.putExtra("soluong", 1);
                ArrayList<Integer> listCard = new ArrayList<>();
                listCard.add(sanPham.getId());
                ArrayList<Integer> listSoLuong = new ArrayList<>();
                listSoLuong.add(-10);
                intent.putExtra("listcart", listCard);
                intent.putExtra("listsoluong", listSoLuong);
                startActivity(intent);
                finish();
            }
            else{
                Toast.makeText(getApplicationContext(), "Sản phẩm đã hết hàng", Toast.LENGTH_SHORT).show();
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