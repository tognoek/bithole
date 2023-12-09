package com.example.myapplication.taikhoan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPassword extends AppCompatActivity {

    private Button btlogin, senemail;
    private TextView textmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        anhXa();
        oneClick();
        getEmailUser();
        if (checkEmail()){
            senEmail();
        }
        
    }

    private boolean checkEmail() {
        return !textmail.getText().toString().isEmpty();
    }

    private void senEmail() {
        senemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.sendPasswordResetEmail(textmail.getText().toString().trim())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ResetPassword.this, "Thư đã gửi về Email của bạn", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(ResetPassword.this, "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }

    private void getEmailUser() {
        Intent intent = getIntent();
        String emailUser = intent.getStringExtra("emailUser");
        if (!emailUser.isEmpty()){
            textmail.setText(emailUser);
        }
    }

    private void oneClick() {
        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResetPassword.this, MainActivity.class);
                intent.putExtra("emailReset", textmail.getText().toString().trim());
                startActivity(intent);
            }
        });
    }

    private void anhXa(){
        btlogin = findViewById(R.id.btlogin);
        textmail = findViewById(R.id.textmail);
        senemail = findViewById(R.id.senemail);
    }
}