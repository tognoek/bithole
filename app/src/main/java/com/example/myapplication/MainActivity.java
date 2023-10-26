package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private Integer bit = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonlogin = findViewById(R.id.btloginhome);
        TextView made = findViewById(R.id.made);

        made.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SQLITE myDB = new SQLITE(MainActivity.this);
                if (bit % 2 == 0){
                    myDB.Remove();
                }
                else{
                    myDB.Create();
                }
                bit++;
            }
        });
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user, mail, password;
                EditText usertext = findViewById(R.id.textuser);
                user = usertext.getText().toString();
                EditText passwordtext = findViewById(R.id.textpassword);
                password = passwordtext.getText().toString();
                if (user.isEmpty() || password.isEmpty()){
                    showToast("Please enter all required information");
                }else{
                    Account acc = new Account(user, password);
                    SQLITE myDb = new SQLITE(MainActivity.this);
                    if (myDb.Findaccount(acc)){
                        Intent intent = new Intent(MainActivity.this, Home.class);
                        startActivity(intent);
                    }else{
                        showToast("Incorrect username or password");
                    }
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
        findViewById(R.id.nextpage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, thongbao.class));
            }
        });
    }
}