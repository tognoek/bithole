package com.example.myapplication.adapter;

import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_REF;
import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_USER_REF;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Interface.OnItemChangeListener;
import com.example.myapplication.Interface.OnItemCheckedChangeListener;
import com.example.myapplication.R;
import com.example.myapplication.entity.Cart;
import com.example.myapplication.entity.ListCard;
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

    private OnItemCheckedChangeListener onItemCheckedChangeListener;
    private OnItemChangeListener onItemChangeListener;
    private final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("GioHang").child(PublicFunciton.getIdUser());

    public AdapterCart(@NonNull Activity context, int idLayout, ArrayList<Cart> myList) {
        super(context, idLayout, myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
    }

    public void setOnItemCheckedChangeListener(OnItemCheckedChangeListener listener) {
        this.onItemCheckedChangeListener = listener;
    }

    public OnItemChangeListener getOnItemChangeListener() {
        return onItemChangeListener;
    }

    public void setOnItemChangeListener(OnItemChangeListener onItemChangeListener) {
        this.onItemChangeListener = onItemChangeListener;
    }

    @NonNull
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        RecyclerView.ViewHolder viewHolder;
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
        CheckBox check = convertView.findViewById(R.id.checkbox);
        LinearLayout shop = convertView.findViewById(R.id.shop);
        LinearLayout sanpham = convertView.findViewById(R.id.sanpham);

        xoa.setOnClickListener(v -> removeCart(position));

        check.setChecked(itemnew.isCheck());

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReferenceUser = firebaseDatabase.getReference("User").child(itemnew.getIdShop());
        databaseReferenceUser.child("ten").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (task.isSuccessful()){
                    tenShop.setText(task.getResult().getValue().toString());
                }else{
                    Log.d("get name shop on cart", "Fail");
                }
            }
        });
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
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Log.d("check", "onClick: " + check.isChecked());
//                check.setChecked(!check.isChecked());
                Log.d("checkBoxCart", "Value: " + check.isChecked());
                myList.get(position).setCheck(check.isChecked());
                if (onItemCheckedChangeListener != null) {
                    Log.d("checkOnItemCheckedChangeListener", "True");
                    onItemCheckedChangeListener.onItemCheckedChanged(position, check.isChecked());
                }
            }
        });

        shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemChangeListener.onItemChanged(position, 1);
            }
        });

        sanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemChangeListener.onItemChanged(position, 2);
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
                for (DataSnapshot snapshotValue : snapshot.getChildren())
                {
                    snapshotValue.getRef().removeValue().addOnSuccessListener(nothing -> notifyDataSetChanged());
                    tongTien();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public double tongTien(){
        double returnSum = 0.0;
        for (int i = 0 ; i < myList.size(); i++){
            Cart itemCart = myList.get(i);
            if (itemCart.isCheck()){
                returnSum = returnSum + itemCart.getDongia() * itemCart.getSoluong();
            }
        }
        return returnSum;
    }

    public int soLuong(){
        int returnSum = 0;
        for (int i = 0 ; i < myList.size(); i++){
            Cart itemCart = myList.get(i);
            if (itemCart.isCheck()){
                returnSum = returnSum + itemCart.getSoluong();
            }
        }
        return returnSum;
    }
    public ArrayList<Integer> getListCard(){
        ArrayList<Integer> listCards = new ArrayList<>();
        for (int i = 0; i < myList.size(); i++){
            Cart itemCart = myList.get(i);
            if (itemCart.isCheck()){
                listCards.add(itemCart.getId());
            }
        }
        return listCards;
    }
    public ArrayList<Integer> getListSoLuong(){
        ArrayList<Integer> listCards = new ArrayList<>();
        for (int i = 0; i < myList.size(); i++){
            Cart itemCart = myList.get(i);
            if (itemCart.isCheck()){
                listCards.add(itemCart.getSoluong());
            }
        }
        return listCards;
    }
}
