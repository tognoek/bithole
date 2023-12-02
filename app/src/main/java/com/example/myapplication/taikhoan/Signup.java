package com.example.myapplication.taikhoan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity_trangchu;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Signup extends AppCompatActivity {

    private EditText user, mail, password, passwordRequest;
    private Button buttonLogin, buttonRegister;
    private ProgressDialog progressDialog;

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        anhXa();
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPasswordConfirm()){
                    onClickSignUp();
                }
                else{
                    Toast.makeText(Signup.this, "Sai mật khẩu nhập lần 2", Toast.LENGTH_SHORT).show();
                }
            }
        });


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean checkPasswordConfirm(){
        String passWordOne = password.getText().toString().trim();
        String passWordTwo = passwordRequest.getText().toString().trim();
        if (!passWordOne.isEmpty() && !passWordTwo.isEmpty() && passWordTwo.equals(passWordOne)){
            return true;
        }
        return  false;
    }
    private void anhXa(){
        progressDialog = new ProgressDialog(this);
        user = findViewById(R.id.textuser);
        mail = findViewById(R.id.textmail);
        password = findViewById(R.id.textpassword);
        passwordRequest = findViewById(R.id.textconfirm);



        buttonRegister = findViewById(R.id.btregister);
        buttonLogin = findViewById(R.id.btlogin);
    }
    private void onClickSignUp() {
        String emailText = mail.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser fUser = mAuth.getCurrentUser();
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(user.getText().toString().trim())
                                    .build();
                            progressDialog.dismiss();
                            if (fUser != null){
                                fUser.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Intent intent = new Intent(Signup.this, activity_trangchu.class);
                                                    startActivity(intent);
                                                    finishAffinity();
                                                }
                                                else{
                                                    Toast.makeText(Signup.this, "Authentication failed.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }
                        } else {
                            Toast.makeText(Signup.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}