package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Signup extends AppCompatActivity {

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Button buttonregister = findViewById(R.id.btregister);
        buttonregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user, mail, password;
                Integer group;
                group = 1;
                EditText usertext = findViewById(R.id.textuser);
                user = usertext.getText().toString();
                EditText mailtext = findViewById(R.id.textmail);
                mail = mailtext.getText().toString();
                EditText passwordtext = findViewById(R.id.textpassword);
                password = passwordtext.getText().toString();
                if (user.isEmpty() || mail.isEmpty() || password.isEmpty()){
                    showToast("Please enter all required information");
                }else{
                    Account account = new Account(user, mail, password, group);
                    SQLITE myDb = new SQLITE(Signup.this);
                    if (myDb.Findauser(account)){
                        showToast("Account already exists");
                    }
                    else {
                        myDb.insert(account);
                        usertext.setText("");
                        mailtext.setText("");
                        passwordtext.setText("");
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(Signup.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }, 3000);
                    }
                }

            }
        });


        Button buttonlogin = findViewById(R.id.btlogin);
        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }
}