package com.example.myapplication.adapter;

import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_REF;

import android.app.Activity;
import android.content.Context;
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
import com.example.myapplication.thuvien.SanPham;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterSanPham extends ArrayAdapter {

    Activity context;
    int idLayout;
    ArrayList<SanPham> myList;


    public AdapterSanPham(@NonNull Activity context, int idLayout, ArrayList<SanPham> myList) {
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
        SanPham itemnew = myList.get(position);


        //Anh Xa

        TextView ten = convertView.findViewById(R.id.tensp);
        TextView dongia = convertView.findViewById(R.id.dongia);
        TextView soluong = convertView.findViewById(R.id.soluong);
        ImageView hinhAnhImageView = convertView.findViewById(R.id.hinhanh);

        ten.setText(itemnew.getName());
        dongia.setText(String.valueOf(itemnew.getDongia()));
        soluong.setText(String.valueOf(itemnew.getSoluong()));

        if (!itemnew.getHinhanh().equals("null"))
            PRODUCT_IMAGE_REF.child(itemnew.getHinhanh()).getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(hinhAnhImageView));

        return convertView;
    }

}
