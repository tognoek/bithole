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
import com.example.myapplication.entity.VoucherItem;
import com.example.myapplication.thuvien.FormatVND;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterVoucher extends ArrayAdapter {

    Activity context;
    int idLayout;
    ArrayList<VoucherItem> myList;


    public AdapterVoucher(@NonNull Activity context, int idLayout, ArrayList<VoucherItem> myList) {
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
        VoucherItem itemnew = myList.get(position);


        //Anh Xa

        TextView name = convertView.findViewById(R.id.name);
        TextView detail = convertView.findViewById(R.id.detail);
//        ImageView hinhAnhImageView = convertView.findViewById(R.id.hinhanh);

        name.setText(itemnew.getName());
        detail.setText(itemnew.getMota());

//        if (!itemnew.getHinhanh().equals("null"))
//            PRODUCT_IMAGE_REF.child(itemnew.getHinhanh()).getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(hinhAnhImageView));

        return convertView;
    }

}
