package com.example.myapplication.taikhoan;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity_trangchu;
import com.example.myapplication.entity.User;
import com.example.myapplication.thuvien.CustomProgressDialog;
import com.example.myapplication.thuvien.FormatTime;
import com.example.myapplication.thuvien.GetNameEmail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Signup extends AppCompatActivity {

    private EditText user, mail, password, passwordRequest;
    private Button buttonLogin, buttonRegister;

    private CustomProgressDialog customProgressDialog;

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
                if (checkInput()){
                    if (checkPasswordConfirm()){
                        onClickSignUp();
                    }
                    else{
                        Toast.makeText(Signup.this, "Nhập sai mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(Signup.this, "Nhập thiếu thông tin", Toast.LENGTH_SHORT).show();
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

    private boolean checkInput() {
        if (!password.getText().toString().trim().isEmpty() && !mail.getText().toString().trim().isEmpty() && !passwordRequest.getText().toString().trim().isEmpty()){
            return true;
        }
        return false;
    };

    private boolean checkPasswordConfirm(){
        String passWordOne = password.getText().toString().trim();
        String passWordTwo = passwordRequest.getText().toString().trim();
        if (!passWordOne.isEmpty() && !passWordTwo.isEmpty() && passWordTwo.equals(passWordOne)){
            return true;
        }
        return  false;
    }
    private void onClickSignUp() {
        String emailText = mail.getText().toString().trim();
        String passwordText = password.getText().toString().trim();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        customProgressDialog.show();
        mAuth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser fUser = mAuth.getCurrentUser();
                            String nameUser = user.getText().toString().trim();
                            if (nameUser.isEmpty()){
                                nameUser = new GetNameEmail(emailText).getRealNameEmail();
                            }
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(nameUser)
                                    .build();
                            customProgressDialog.dismiss();
                            if (fUser != null){
                                fUser.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {

                                                    LocalDateTime currentDateTime = LocalDateTime.now();

                                                    // Format the date and time (optional)
                                                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                                                    String formattedDateTime = currentDateTime.format(formatter);
                                                    int time = new FormatTime(formattedDateTime).getTimeInteger();
                                                    User user = new User("", fUser.getDisplayName(), true, "", time);
                                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                                    DatabaseReference userRef = database.getReference("User");
                                                    userRef.child(fUser.getUid()).setValue(user);
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
                            customProgressDialog.dismiss();
                            Toast.makeText(Signup.this, "Tài khoản đã tồn tại",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private void anhXa(){
        customProgressDialog = new CustomProgressDialog(this);
        user = findViewById(R.id.textuser);
        mail = findViewById(R.id.textmail);
        password = findViewById(R.id.textpassword);
        passwordRequest = findViewById(R.id.textconfirm);

        buttonRegister = findViewById(R.id.btregister);
        buttonLogin = findViewById(R.id.btlogin);
    }
}