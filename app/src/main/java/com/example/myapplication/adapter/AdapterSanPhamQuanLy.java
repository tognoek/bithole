package com.example.myapplication.adapter;

import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_REF;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Interface.OnItemChangeListener;
import com.example.myapplication.R;
import com.example.myapplication.entity.SanPham;
import com.example.myapplication.thuvien.FormatVND;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterSanPhamQuanLy extends ArrayAdapter {

    Activity context;
    int idLayout;
    ArrayList<SanPham> myList;
    private OnItemChangeListener onItemChangeListener;

    public AdapterSanPhamQuanLy(@NonNull Activity context, int idLayout, ArrayList<SanPham> myList) {
        super(context, idLayout, myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
    }

    public OnItemChangeListener getOnItemChangeListener() {
        return onItemChangeListener;
    }

    public void setOnItemChangeListener(OnItemChangeListener onItemChangeListener) {
        this.onItemChangeListener = onItemChangeListener;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        LayoutInflater myInflactor = context.getLayoutInflater();
        convertView = myInflactor.inflate(idLayout,null);
        SanPham itemnew = myList.get(position);


        //Anh Xa

        TextView ten = convertView.findViewById(R.id.tensp);
        LinearLayout chiTiet = convertView.findViewById(R.id.chitiet_sp);
        TextView dongia = convertView.findViewById(R.id.dongia);
        TextView soluong = convertView.findViewById(R.id.soluong);
        ImageView hinhAnhImageView = convertView.findViewById(R.id.hinhanh);
        Button edit = convertView.findViewById(R.id.edit);
        Button delete = convertView.findViewById(R.id.delete);

        chiTiet.setOnClickListener(v -> onItemChangeListener.onItemChanged(position, 1));
        edit.setOnClickListener(v -> onItemChangeListener.onItemChanged(position, 2));
        delete.setOnClickListener(v -> onItemChangeListener.onItemChanged(position, 3));
        ten.setText(itemnew.getName());
        dongia.setText("Đơn giá: " + new FormatVND(String.valueOf(itemnew.getDongia())).getVND());
        soluong.setText("Số lượng: " + itemnew.getSoluong());

        if (!itemnew.getHinhanh().equals("null"))
            PRODUCT_IMAGE_REF.child(itemnew.getHinhanh()).getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(hinhAnhImageView));

        return convertView;
    }

}
