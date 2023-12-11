package com.example.myapplication.adapter;

import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_REF;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.entity.SanPham;
import com.example.myapplication.entity.SanPhamLichSu;
import com.example.myapplication.thuvien.FormatTime;
import com.example.myapplication.thuvien.FormatVND;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterLichSu extends ArrayAdapter {

    Activity context;
    int idLayout;
    ArrayList<SanPhamLichSu> myList;


    public AdapterLichSu(@NonNull Activity context, int idLayout, ArrayList<SanPhamLichSu> myList) {
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
        SanPhamLichSu itemnew = myList.get(position);


        //Anh Xa

        TextView ten = convertView.findViewById(R.id.tensp);
        TextView dongia = convertView.findViewById(R.id.dongia);
        TextView soluong = convertView.findViewById(R.id.soluong);
        TextView date = convertView.findViewById(R.id.date);
        ImageView hinhAnhImageView = convertView.findViewById(R.id.hinhanh);

        ten.setText(itemnew.getName());
        dongia.setText(new FormatVND(String.valueOf(itemnew.getDongia())).getVND());
        soluong.setText("Số lượng: " + itemnew.getSoluong());
        date.setText(new FormatTime(String.valueOf(itemnew.getDate())).getTimeTwo());

        if (!itemnew.getHinhanh().equals("null"))
            PRODUCT_IMAGE_REF.child(itemnew.getHinhanh()).getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(hinhAnhImageView));

        return convertView;
    }

}
