package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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

    private ProgressDialog progressDialog;

    private Button button_dangky;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    ImageView googleBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();

        googleBtn = findViewById(R.id.google);

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

        button_dangky = findViewById(R.id.btsignup);
        button_dangky.setOnClickListener(view ->
                startActivity(new Intent(getApplicationContext(), activity_dangky.class))
        );
    }

    void SignIn(){
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent,1000);
    }

//        logIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkLogIn();
//            }
//        });
//    }


    private void anhXa(){
        progressDialog = new ProgressDialog(this);
        email = findViewById(R.id.textuser);
        passWord = findViewById(R.id.textpassword);
        signUp = findViewById(R.id.btsignup);
        logIn = findViewById(R.id.btlogin);
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
        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
        startActivity(intent);
    }
    private void checkLogIn() {
        progressDialog.show();
        String textEmail = email.getText().toString().trim();
        String textPassWord = passWord.getText().toString().trim();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(textEmail, textPassWord)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(MainActivity.this, activity_trangchu.class);
                            startActivity(intent);
                            finishAffinity();
                        } else {
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}