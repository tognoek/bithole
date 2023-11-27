package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
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
    private EditText email, passWord;
    private  Button signUp, logIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Signup.class);
                startActivity(intent);
            }
        });

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkLogIn()){
                    Intent intent = new Intent(MainActivity.this, activity_trangchu.class);
                }
                else{
                    showToast("Tài khoản hoặc mật khẩu không chính xác");
                }
            }
        });



    }

    private boolean checkLogIn() {
        return true;
    }


    private void anhXa(){
        email = findViewById(R.id.textuser);
        passWord = findViewById(R.id.textpassword);
        signUp = findViewById(R.id.btsignup);
        logIn = findViewById(R.id.btlogin);
    }
}