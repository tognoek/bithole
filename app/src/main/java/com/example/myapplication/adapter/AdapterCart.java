package com.example.myapplication.adapter;

import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_REF;
import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_USER_REF;

import android.app.Activity;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.entity.Cart;
import com.example.myapplication.thuvien.FormatVND;
import com.example.myapplication.thuvien.PublicFunciton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterCart extends ArrayAdapter {

    Activity context;
    int idLayout;
    ArrayList<Cart> myList;
    private Runnable onDataSetChanged;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("GioHang").child(PublicFunciton.getIdUser());

    public AdapterCart(@NonNull Activity context, int idLayout, ArrayList<Cart> myList) {
        super(context, idLayout, myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
    }

    @NonNull
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        LayoutInflater myInflactor = context.getLayoutInflater();
        convertView = myInflactor.inflate(idLayout,null);

        final Cart itemnew = myList.get(position);


        //Anh Xa
        TextView tenShop = convertView.findViewById(R.id.tenShop);
        TextView ten = convertView.findViewById(R.id.tenSanPham);
        TextView dongia = convertView.findViewById(R.id.donGia);
        TextView soluong = convertView.findViewById(R.id.soLuong);
        ImageView anh = convertView.findViewById(R.id.hinhanhSanPham);
        ImageView logo = convertView.findViewById(R.id.logoShop);
        ImageView xoa = convertView.findViewById(R.id.img_xoa);
        xoa.setOnClickListener(v -> removeCart(position));

        tenShop.setText(itemnew.getShop());
        ten.setText(itemnew.getName());
        dongia.setText(new FormatVND(String.valueOf(itemnew.getDongia())).getVND());
        soluong.setText("Số lượng: " + String.valueOf(itemnew.getSoluong()));
        PRODUCT_IMAGE_REF.child(itemnew.getHinhanh()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (uri != null){
                    Picasso.get().load(uri).into(anh);
                }
            }
        });

        PRODUCT_IMAGE_USER_REF.child(itemnew.getIdShop()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (uri != null){
                    Picasso.get().load(uri).into(logo);
                }
            }
        });

        return convertView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        if (onDataSetChanged != null)
            onDataSetChanged.run();
    }

    public void setOnDataSetChanged(Runnable onDataSetChanged) {
        this.onDataSetChanged = onDataSetChanged;
    }
    public int getCount(){
        return super.getCount();
    }

    private void removeCart(int position) {
        final Cart cart = myList.remove(position);
        databaseReference.orderByChild("id").equalTo(cart.getId()).limitToFirst(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren())
                    snapshot1.getRef().removeValue().addOnSuccessListener(nothing -> notifyDataSetChanged());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
