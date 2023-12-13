package com.example.myapplication.adapter;

import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_REF;
import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_USER_REF;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.detail;
import com.example.myapplication.entity.SanPham;
import com.example.myapplication.entity.ThongBao;
import com.example.myapplication.thuvien.FormatTime;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterThongBao extends ArrayAdapter {

    Activity context;
    int idLayout;
    ArrayList<ThongBao> myList;

    public AdapterThongBao(@NonNull Activity context, int idLayout, ArrayList<ThongBao> myList) {
        super(context, idLayout, myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        LayoutInflater myInflactor = context.getLayoutInflater();
        convertView = myInflactor.inflate(idLayout,null);
        final View convertView1 = convertView;
        ThongBao itemnew = myList.get(position);
        //Anh Xa
        TextView tieude = convertView.findViewById(R.id.tieude);
        TextView ngaybl = convertView.findViewById(R.id.ngayBL);
        ImageView hinhAnhImageView = convertView.findViewById(R.id.hinhanh);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("User").child(itemnew.getIdNguoiDang()).child("ten");
        DatabaseReference databaseReference1 = firebaseDatabase.getReference("SanPham").child(itemnew.getIdSanPham());
        databaseReference1.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference1.removeEventListener(this);
                SanPham sanPham = snapshot.getValue(SanPham.class);
                convertView1.setOnClickListener(v -> {
                    if (itemnew.getLoaiTb() != 3){
                        Intent intent = new Intent(v.getContext(), detail.class);
                        intent.putExtra("SanPham", sanPham);
                        v.getContext().startActivity(intent);
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String ten = snapshot.getValue(String.class);
                switch (itemnew.getLoaiTb()){
                    case 1 -> {
                        tieude.setText(ten + " đã bình luận vào bài đăng của bạn");
                    }
                    case 2 -> {
                        tieude.setText(ten + " đã like bình luận của bạn");
                    }
                    case 3 -> {
                        tieude.setText("Bạn có đơn hàng từ: " + ten);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ngaybl.setText(new FormatTime(String.valueOf(itemnew.getNgay())).getTimeTwo());
        if (!itemnew.getIdNguoiDang().equals("null"))
            PRODUCT_IMAGE_USER_REF.child(itemnew.getIdNguoiDang()).getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(hinhAnhImageView));

        return convertView;
    }

}
