package com.example.myapplication.nome;

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

import java.util.ArrayList;

public class MyArray extends ArrayAdapter<meber> {
    public MyArray(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public MyArray(@NonNull Context context, ArrayList<meber> dataArrayList) {
        super(context, R.layout.layout_about_us, dataArrayList);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        meber meber = getItem(position);
        if (view == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_about_us, parent, false);
        }
        ImageView image = view.findViewById(R.id.image);
        TextView name = view.findViewById(R.id.name);
        TextView masv = view.findViewById(R.id.masv);
        image.setImageResource(meber.getImage());
        name.setText(meber.getName());
        masv.setText(meber.getMasinhvien());
        return view;
    }
}
