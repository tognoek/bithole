package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DatailMember extends AppCompatActivity {
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datail_member);
        findViewById(R.id.trove).setOnClickListener(view -> getOnBackPressedDispatcher().onBackPressed());
        Intent getIntent = getIntent();
        meber PP = (meber) getIntent.getSerializableExtra("member");
        String[] name = PP.getListName();
        String[] link = PP.getListView();
        ImageView image = findViewById(R.id.image);
        image.setImageResource(PP.getImage());
        TextView textname = findViewById(R.id.name);
        textname.setText(PP.getName());
        TextView textmasv = findViewById(R.id.masv);
        textmasv.setText(PP.getMasinhvien());
        ArrayList<ViewUs> data = new ArrayList<>();
        for (int i = 0; i < name.length; i++){
            data.add(new ViewUs(name[i], link[i]));
        }
        ArrayViewUs listAdapter;
        listAdapter = new ArrayViewUs(DatailMember.this, data);
        ListView listview = findViewById(R.id.listview);
        listview.setAdapter(listAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                try {
                    String activityClassName = "com.example.myapplication." + data.get(i).getLink();
                    Class<?> activityClass = Class.forName(activityClassName);
                    Intent intent = new Intent(DatailMember.this, activityClass);
                    startActivity(intent);
                } catch (ClassNotFoundException e) {
                    showToast("com.example.myapplication." + data.get(i).getLink());
                }
            }
        });

    }
}