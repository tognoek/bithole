package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class minigame extends AppCompatActivity {
    public TextView tvCountdown;
    private ImageView trove;
    public CountDownTimer timer;
    public Button start;
    private TextView txtthoigian;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minigame);
        tvCountdown = findViewById(R.id.tvCountdown);
        startTime();
        trove = (ImageView) findViewById(R.id.trove);
        txtthoigian = findViewById(R.id.thoigian);

        Calendar calendar = Calendar.getInstance();
//        // Thêm 1 ngày để có được ngày mai
//        calendar.add(Calendar.DAY_OF_YEAR, 1);
//        // Chuyển đổi thành đối tượng Date
        Calendar targetTime = Calendar.getInstance();
        targetTime.set(Calendar.HOUR_OF_DAY, 12);
        targetTime.set(Calendar.MINUTE, 0);
        targetTime.set(Calendar.SECOND, 0);
        targetTime.set(Calendar.MILLISECOND, 0);
        targetTime.add(Calendar.DAY_OF_MONTH, 1); // Thêm 1 ngày

        Date tomorrow = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedTomorrow = dateFormat.format(tomorrow);

        txtthoigian.setText("Phiên quay ngày "+formattedTomorrow);


        trove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),activity_trangchu.class);
                startActivity(intent);
            }
        });

    }
    private void startTime(){
        // Lấy thời điểm hiện tại
        Calendar currentTime = Calendar.getInstance();

        // Đặt thời điểm 12 giờ trưa ngày mai
        Calendar targetTime = Calendar.getInstance();
        targetTime.set(Calendar.HOUR_OF_DAY, 12);
        targetTime.set(Calendar.MINUTE, 0);
        targetTime.set(Calendar.SECOND, 0);
        targetTime.set(Calendar.MILLISECOND, 0);
        targetTime.add(Calendar.DAY_OF_MONTH, 1); // Thêm 1 ngày

        // Tính thời gian còn lại từ thời điểm hiện tại đến 12 giờ trưa ngày mai
        long countdownMillis = targetTime.getTimeInMillis() - currentTime.getTimeInMillis();
        timer = new CountDownTimer( countdownMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long hours = (millisUntilFinished / 1000) / 3600;
                long minutes = ((millisUntilFinished / 1000) % 3600) / 60;
                long seconds = (millisUntilFinished / 1000) % 60;
                String timeFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d",hours,minutes,seconds);
                tvCountdown.setText(timeFormatted);
            }
            @Override
            public void onFinish() {
                tvCountdown.setText("00:00:00");
                Toast.makeText(minigame.this,"Đã có kết quả",Toast.LENGTH_SHORT).show();
            }
        }.start();
    }
}