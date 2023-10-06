package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Home extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ListView view = (ListView) findViewById(R.id.view);
        ArrayList<String> array = new ArrayList<>();
        array.add("H");
        array.add("K");
        ArrayAdapter adapter = new ArrayAdapter(Home.this, android.R.layout.simple_list_item_1, array);
        view.setAdapter(adapter);
    }
}