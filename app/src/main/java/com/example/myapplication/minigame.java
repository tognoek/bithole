package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class minigame extends AppCompatActivity {
    public TextView countdownTimer;
    private ImageView trove;
    public CountDownTimer timer;
    public Button start;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minigame);
        countdownTimer = findViewById(R.id.countdown_timer);
        startTime();
        trove = (ImageView) findViewById(R.id.trove);
        trove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),activity_trangchu.class);
                startActivity(intent);
            }
        });

    }
    private void startTime(){
        timer = new CountDownTimer( 6*360*10000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hours = (millisUntilFinished / 1000) / 3600;
                long minutes = ((millisUntilFinished / 1000) % 3600) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes,seconds);
                countdownTimer.setText(timeFormatted);
            }
            @Override
            public void onFinish() {
                countdownTimer.setText("00:00:00");
                Toast.makeText(minigame.this,"Đã có kết quả",Toast.LENGTH_SHORT).show();
            }
        }.start();
    }
}