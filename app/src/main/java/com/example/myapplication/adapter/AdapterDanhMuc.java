package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.entity.DanhMuc;

import java.util.List;

public class AdapterDanhMuc extends ArrayAdapter<DanhMuc> {
    public AdapterDanhMuc(@NonNull Context context, int resource, @NonNull List<DanhMuc> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_select_dropdown, parent, false);
        TextView nameDanhMuc = convertView.findViewById(R.id.nameDanhMuc);

        DanhMuc danhMuc = this.getItem(position);
        if (danhMuc != null){
            nameDanhMuc.setText(danhMuc.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_drop, parent, false);
        TextView nameDanhMuc = convertView.findViewById(R.id.nameDanhMuc);

        DanhMuc danhMuc = this.getItem(position);
        if (danhMuc != null){
            nameDanhMuc.setText(danhMuc.getName());
        }
        return convertView;
    }
}
