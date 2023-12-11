package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class minigame extends AppCompatActivity {
    public TextView tvCountdown,start,soluot;
    private EditText txt1,txt2,txt3,txt4,txt5;
    private LinearLayout them;
    private ImageView trove;
    public CountDownTimer timer;
    private TextView txtthoigian;
    int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minigame);
        tvCountdown = findViewById(R.id.tvCountdown);

        trove = (ImageView) findViewById(R.id.trove);
        txtthoigian = findViewById(R.id.thoigian);
        txt1 = findViewById(R.id.text1);
        txt2 = findViewById(R.id.text2);
        txt3 = findViewById(R.id.text3);
        txt4 = findViewById(R.id.text4);
        txt5 = findViewById(R.id.text5);
        start = findViewById(R.id.start);
        soluot = findViewById(R.id.soLuot);
        them = findViewById(R.id.themLuot);
        soluot.setText(""+i);

        Calendar calendar = Calendar.getInstance();
//        // Thêm 1 ngày để có được ngày mai
//        calendar.add(Calendar.DAY_OF_YEAR, 1);
//        // Chuyển đổi thành đối tượng Date
        Calendar targetTime = Calendar.getInstance();
        targetTime.set(Calendar.HOUR_OF_DAY, 12);
        targetTime.set(Calendar.MINUTE, 0);
        targetTime.set(Calendar.SECOND, 0);
        targetTime.set(Calendar.MILLISECOND, 0);
//        targetTime.add(Calendar.DAY_OF_MONTH, 1); // Thêm 1 ngày

        Date today = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedToday = dateFormat.format(today);

        txtthoigian.setText("Phiên quay ngày "+formattedToday);


        trove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),activity_trangchu.class);
                startActivity(intent);
            }
        });
        txt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txt1.length() > 0) {
                    txt2.requestFocus();
                }
            }
        });
        txt2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txt2.length() > 0) {
                    txt3.requestFocus();
                }
                else {
                    txt1.requestFocus();
                }
            }
        });
        txt3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txt3.length() > 0) {
                    txt4.requestFocus();
                }
                else {
                    txt2.requestFocus();
                }
            }
        });
        txt4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (txt4.length() > 0) {
                    txt5.requestFocus();
                }
                else {
                    txt3.requestFocus();
                }
            }
        });
        txt5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(txt5.length()==0){
                    txt4.requestFocus();
                }
            }
        });
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        if(txt1.getText().equals("") || txt2.getText().equals("") || txt3.getText().equals("") || txt4.getText().equals("") || txt5.getText().equals("")){
            start.setEnabled(false);
        }else {
            start.setEnabled(true);
        }
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i=i+1;
                soluot.setText(""+i);
                start.setEnabled(true);
            }
        });
        congChuoi();
        startTime();


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
    public void showAlertDialog() {
        // Tạo một đối tượng AlertDialog.Builder
        AlertDialog.Builder builder = new AlertDialog.Builder(minigame.this);

        // Cài đặt tiêu đề và nội dung của AlertDialog
        builder.setMessage("Bạn có chắc muốn chọn số "+congChuoi()+" không?");

        // Cài đặt nút "Đồng ý"
        builder.setPositiveButton("Chắc chắn", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                dialogInterface.dismiss();
                txt1.setText("");
                txt2.setText("");
                txt3.setText("");
                txt4.setText("");
                txt5.setText("");
                soluot.setText(""+(i-=1));
                if(i==0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(minigame.this);

                    // Cài đặt tiêu đề và nội dung của AlertDialog
                    builder.setMessage("Bạn cần tìm thêm lượt");

                    // Cài đặt nút "Đồng ý"
                    builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Xử lý khi người dùng nhấn nút "Cancel"
                            dialog.dismiss();
                            start.setEnabled(false);

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }
        });
        builder.setNegativeButton("Chọn lại", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Xử lý khi người dùng nhấn nút "Cancel"
                dialog.dismiss();

            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
    public String congChuoi(){
        String chuoi1 = txt1.getText().toString();
        String chuoi2 = txt2.getText().toString();
        String chuoi3 = txt3.getText().toString();
        String chuoi4 = txt4.getText().toString();
        String chuoi5 = txt5.getText().toString();
        String chuoi=chuoi1+chuoi2+chuoi3+chuoi4+chuoi5;
        return chuoi;

    }

}