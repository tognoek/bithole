package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
                EditText usertext = findViewById(R.id.textuser);
                user = usertext.getText().toString();
                EditText mailtext = findViewById(R.id.textmail);
                mail = mailtext.getText().toString();
                EditText passwordtext = findViewById(R.id.textpassword);
                password = passwordtext.getText().toString();
                String input = user + ", " + mail + ", " + password;
                WriteFile.Write(input);
                showToast("Create a successful account!!");
                usertext.setText("");
                mailtext.setText("");
                passwordtext.setText("");
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