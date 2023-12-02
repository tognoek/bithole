package com.example.myapplication.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.entity.Cart;

import java.util.ArrayList;

public class AdapterCart extends ArrayAdapter {

    Activity context;
    int idLayout;
    ArrayList<Cart> myList;

    public AdapterCart(@NonNull Activity context, int idLayout, ArrayList<Cart> myList) {
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
        Cart itemnew = myList.get(position);


        //Anh Xa
        TextView tenShop = convertView.findViewById(R.id.tenShop);
        TextView ten = convertView.findViewById(R.id.tenSanPham);
        TextView dongia = convertView.findViewById(R.id.donGia);
        TextView soluong = convertView.findViewById(R.id.soLuong);

        tenShop.setText(itemnew.getShop());
        ten.setText(itemnew.getName());
        dongia.setText(String.valueOf(itemnew.getDongia()));
        soluong.setText(String.valueOf(itemnew.getSoluong()));


        return convertView;
    }
}
