package com.example.myapplication.taikhoan;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.activity_trangchu;
import com.example.myapplication.thuvien.CustomProgressDialog;
import com.example.myapplication.thuvien.PublicFunciton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private EditText email, passWord;
    private  Button signUp, logIn;
    private CustomProgressDialog customProgressDialog;

    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    RelativeLayout googleBtn;
    private TextView resetPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();

        goResetPassword();
        getEmailReset();

        googleBtn = findViewById(R.id.logingoogle);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            navigateToSecondActivity();
        }

        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();
            }
        });

        signUp.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), Signup.class))
        );
        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkInput()){
                    checkLogIn();
                }
                else{
                    Toast.makeText(MainActivity.this, "Chưa nhập đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean checkInput() {
        return !email.getText().toString().trim().isEmpty() && !passWord.getText().toString().trim().isEmpty();
    }

    private void goResetPassword() {
        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ResetPassword.class);
                String emailUser = email.getText().toString().trim();
                intent.putExtra("emailUser", emailUser);
                startActivity(intent);
            }
        });
    }

    private void SignIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();

            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "something went wrong ", Toast.LENGTH_SHORT).show();
            }
        }
    }


    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(MainActivity.this, activity_trangchu.class);
        startActivity(intent);
    }
    private void checkLogIn() {
        customProgressDialog.show();
        String textEmail = email.getText().toString().trim();
        String textPassWord = passWord.getText().toString().trim();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(textEmail, textPassWord)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        customProgressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, activity_trangchu.class);
                            startActivity(intent);
                            Log.d("idUserSignup", "onComplete: " + PublicFunciton.getIdUser());
                            finishAffinity();
                        } else {
                            Toast.makeText(MainActivity.this, "Login failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    private void getEmailReset() {
        Intent intent = getIntent();
        if (intent.hasExtra("emailReset")){
            String emailReset = intent.getStringExtra("emailReset");
            if (!emailReset.isEmpty()){
                email.setText(emailReset);
            }
        }
    }
    private void anhXa(){
        customProgressDialog = new CustomProgressDialog(this);
        email = findViewById(R.id.textuser);
        passWord = findViewById(R.id.textpassword);
        signUp = findViewById(R.id.btsignup);
        logIn = findViewById(R.id.btlogin);
        resetPassword = findViewById(R.id.nextpage);
    }

}