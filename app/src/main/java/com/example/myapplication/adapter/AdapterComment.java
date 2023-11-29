package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.thuvien.ComMent;
import com.example.myapplication.thuvien.SanPham;

import java.util.ArrayList;

public class AdapterComment extends ArrayAdapter {

    Activity context;
    int idLayout;
    ArrayList<ComMent> myList;

    public AdapterComment(@NonNull Activity context, int idLayout, ArrayList<ComMent> myList) {
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
        ComMent itemnew = myList.get(position);
        //Anh Xa

        TextView noidung = convertView.findViewById(R.id.noidungbl);
        TextView user = convertView.findViewById(R.id.tenUserBL);
        noidung.setText(itemnew.getComment());
        user.setText(itemnew.getIduser());
        return convertView;
    }

}
