package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.example.myapplication.taikhoan.MainActivity;
import com.example.myapplication.thuvien.FormatTime;
import com.example.myapplication.thuvien.PublicFunciton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class SplashActivity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        taoFireBase();
        gaCha();
        check();

    }

    private void gaCha() {
        Log.d("gacha", "gaCha: Start");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Log.d("gacha", "gaCha: True");
                } else {
                    Log.d("gacha", "gaCha: False");
                    databaseReference.setValue(randomNumber());

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
    public static int randomNumber() {
        Random random = new Random();
        return random.nextInt(100000);
    }

    private void check() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (LogIn()){
                    Intent intent = new Intent(SplashActivity.this, activity_trangchu.class);
                    startActivity(intent);
                    finishAffinity();
                }
                else{
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            }
        }, 2000);
    }

    private boolean LogIn() {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null){
            return false;
        }
        return true;
    }
    private void taoFireBase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Gacha").child("LuckyNumber")
                .child(String.valueOf(new FormatTime(PublicFunciton.getDay()).getTimeInteger()));
    }
}