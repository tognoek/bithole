package com.example.myapplication.thuvien;

import android.app.ProgressDialog;

import androidx.annotation.NonNull;

import com.example.myapplication.entity.SanPham;
import com.example.myapplication.entity.ThongBao;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicReference;

public class PublicFunciton {
    public static final StorageReference PRODUCT_IMAGE_USER_REF = FirebaseStorage.getInstance("gs://thong-ab907.appspot.com").getReference("images");
    public static final StorageReference PRODUCT_IMAGE_REF = FirebaseStorage.getInstance("gs://thong-ab907.appspot.com").getReference("image");


    public static ProgressDialog progressDialog = null;
    public PublicFunciton() {
    }
    public static String getNameUser() {
        String name = "Bit Hole";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            name = user.getDisplayName();
        }
        return name;
    }


    public static String getIdUser(){
        String id = "Bit Hole";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            id = user.getUid();
        }
        return id;
    }

    public static String getEmailUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        assert user != null;
        return user.getEmail();
    }
    public static String getDay(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return currentDateTime.format(formatter);
    }
    public static String getYesterday() {
        LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime yesterdayDateTime = currentDateTime.minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return yesterdayDateTime.format(formatter);
    }
    public static void taoThongBao(int idProduct, @NonNull String idShop, int loaiTb){
        if (idShop.equals(PublicFunciton.getIdUser())){
            return;
        }
        String idNguoiDang = PublicFunciton.getIdUser();
        String ngayBL = PublicFunciton.getDay();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("ThongBao");
        DatabaseReference databaseReferenceTB = databaseReference.child(idShop);
        databaseReferenceTB.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ThongBao thongbao = new ThongBao();
                thongbao.setId(1);
                thongbao.setNgay(new FormatTime(ngayBL).getTimeInteger());
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    ThongBao thongbaoTmp = postSnapshot.getValue(ThongBao.class);
                    assert thongbaoTmp != null;
                    if (loaiTb == thongbaoTmp.getLoaiTb() && loaiTb == 2) {
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

    public static void updateTien(int tien) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Money").child(PublicFunciton.getIdUser());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Integer tienTong = snapshot.getValue(Integer.class);
                    Integer sumTien = tien + tienTong;
                    if (sumTien < 0){
                        sumTien = 0;
                    }
                    sumTien = Math.min(sumTien, 1000000000);
                    databaseReference.setValue(sumTien);
                }
                else{
                    databaseReference.setValue(tien);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
