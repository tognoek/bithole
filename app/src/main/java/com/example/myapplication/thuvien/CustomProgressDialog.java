package com.example.myapplication.thuvien;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import com.example.myapplication.R;

public class CustomProgressDialog extends Dialog {

    private Context context;

    public CustomProgressDialog(Context context) {
        super(context, R.style.CustomProgressDialog);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate the custom layout for the ProgressDialog
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.custom_progress_dialog_layout, null);
        setContentView(view);
    }
}

