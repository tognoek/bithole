package com.example.myapplication.adapter;

import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_REF;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.thuvien.ThongBao;
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
        ThongBao itemnew = myList.get(position);
        //Anh Xa

        TextView tieude = convertView.findViewById(R.id.tieude);
        TextView mota = convertView.findViewById(R.id.mota);
        ImageView hinhAnhImageView = convertView.findViewById(R.id.hinhanh);

        tieude.setText(itemnew.getTieude());
        mota.setText(itemnew.getMota());
        if (!itemnew.getHinhanh().equals("null"))
            PRODUCT_IMAGE_REF.child(itemnew.getHinhanh()).getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(hinhAnhImageView));
        return convertView;
    }

}
