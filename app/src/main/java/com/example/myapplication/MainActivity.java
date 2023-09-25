package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonlogin = findViewById(R.id.btloginhome);
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user, mail, password;
                EditText usertext = findViewById(R.id.textuser);
                user = usertext.getText().toString();
                EditText passwordtext = findViewById(R.id.textpassword);
                password = passwordtext.getText().toString();
                Account acc = new Account(user, password);
                if (acc.getUser().equals("tognoek") && acc.getPassword().equals("28102003")){
                    showToast("Login successful!!");
                }
            }
        });

        Button buttonsignup = findViewById(R.id.btsignup);
        buttonsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
            }
        });
    }
}