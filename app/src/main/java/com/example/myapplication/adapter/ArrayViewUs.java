package com.example.myapplication.adapter;

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
import com.example.myapplication.ViewUs;

import java.util.ArrayList;

public class ArrayViewUs extends ArrayAdapter<ViewUs> {

    public ArrayViewUs(@NonNull Context context, ArrayList<ViewUs> dataArrayList) {
        super(context, R.layout.layout_page, dataArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        ViewUs ViewUs = getItem(position);
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_page, parent, false);
        }
        TextView name = view.findViewById(R.id.name);
        name.setText(ViewUs.getName());
        return view;
    }
}
