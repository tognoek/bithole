package com.example.myapplication;

import androidx.annotation.NonNull;
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

import com.example.myapplication.entity.Gacha;
import com.example.myapplication.thuvien.FormatTime;
import com.example.myapplication.thuvien.PublicFunciton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class minigame extends AppCompatActivity {
    public TextView tvCountdown,start,soluot;
    private EditText txt1,txt2,txt3,txt4,txt5;
    private LinearLayout them;
    private ImageView trove;
    public CountDownTimer timer;
    private TextView txtthoigian, ketqua;
    private String soLuaChon;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private int sotrungthuong;
    private int soluongtrungthuong;
    private ArrayList<Gacha> gachaArrayList = new ArrayList<>();
    int i=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_minigame);
        tvCountdown = findViewById(R.id.tvCountdown);
        anhXa();
        soluot.setText(String.valueOf(i));

        setTime();


        onClick();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });
        them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i = i + 1;
                soluot.setText(String.valueOf(i));
                start.setEnabled(true);
            }
        });
        congChuoi();
        startTime();
        taoFireBase();
        check();


    }
    private void check() {
        databaseReference = firebaseDatabase.getReference("Gacha").child("LuckyNumber")
                .child(String.valueOf(new FormatTime(PublicFunciton.getYesterday()).getTimeInteger()));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    sotrungthuong = snapshot.getValue(Integer.class);
                    String soString = String.format("%05d", sotrungthuong);
                    ketqua.setText(soString);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference = firebaseDatabase.getReference("Gacha").child(PublicFunciton.getIdUser())
                .child(String.valueOf(new FormatTime(PublicFunciton.getYesterday()).getTimeInteger()));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    gachaArrayList.clear();
                    soluongtrungthuong = 0;
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                        Gacha gachatmp = dataSnapshot.getValue(Gacha.class);
                        assert gachatmp != null;
                        gachaArrayList.add(gachatmp);
                        if (gachatmp.getSo() == sotrungthuong){
                            soluongtrungthuong = soluongtrungthuong + 1;
                        }
                    }
                    if (true){
                        databaseReference.removeValue();
                        String newDay = String.valueOf(new FormatTime(PublicFunciton.getYesterday()).getTimeInteger() * 10 + 5);
                        DatabaseReference databaseReferenceNew = firebaseDatabase.getReference("Gacha").child(PublicFunciton.getIdUser())
                                .child(newDay);
                        databaseReferenceNew.setValue(gachaArrayList);
                    }
                    Toast.makeText(getApplicationContext(), "Hôm qua bạn trung: " + soluongtrungthuong + " số!!!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void taoFireBase() {
        firebaseDatabase = FirebaseDatabase.getInstance();
    }

    private void themLuaChon(){
        databaseReference = firebaseDatabase.getReference("Gacha").child(PublicFunciton.getIdUser())
                .child(String.valueOf(new FormatTime(PublicFunciton.getDay()).getTimeInteger()));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Gacha gacha = new Gacha();
                gacha.setId(1);
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Gacha gachaTmp = dataSnapshot.getValue(Gacha.class);
                    assert gachaTmp != null;
                    gacha.setId(gachaTmp.getId() + 1);
                }
                gacha.setSo(Integer.parseInt(soLuaChon));
                databaseReference.child(String.valueOf(gacha.getId())).setValue(gacha);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void setTime() {
        Calendar calendar = Calendar.getInstance();

        Calendar targetTime = Calendar.getInstance();
        targetTime.set(Calendar.HOUR_OF_DAY, 12);
        targetTime.set(Calendar.MINUTE, 0);
        targetTime.set(Calendar.SECOND, 0);
        targetTime.set(Calendar.MILLISECOND, 0);


        Date today = calendar.getTime();

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String formattedToday = dateFormat.format(today);

        txtthoigian.setText("Phiên quay ngày " + formattedToday);
    }

    private void onClick() {
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
    }

    private void startTime(){
        Calendar currentTime = Calendar.getInstance();

        Calendar targetTime = Calendar.getInstance();
        targetTime.set(Calendar.HOUR_OF_DAY, 12);
        targetTime.set(Calendar.MINUTE, 0);
        targetTime.set(Calendar.SECOND, 0);
        targetTime.set(Calendar.MILLISECOND, 0);
        targetTime.add(Calendar.DAY_OF_MONTH, 1); 
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
        AlertDialog.Builder builder = new AlertDialog.Builder(minigame.this);

        builder.setMessage("Bạn có chắc muốn chọn số "+congChuoi()+" không?");
        builder.setPositiveButton("Chắc chắn", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int position) {
                txt1.setText("");
                txt2.setText("");
                txt3.setText("");
                txt4.setText("");
                txt5.setText("");
                if(i < 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(minigame.this);

                    builder.setMessage("Bạn cần tìm thêm lượt");

                    builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else{
                    dialogInterface.dismiss();
                    i = i - 1;
                    if (i < 1){
                        start.setEnabled(false);
                    }
                    txt1.requestFocus();
                    themLuaChon();
                    soluot.setText(String.valueOf(i));
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
        soLuaChon = chuoi1 + chuoi2 + chuoi3 + chuoi4 + chuoi5;
        if (soLuaChon.trim().isEmpty()){
            soLuaChon = "17203";
        }
        return soLuaChon;

    }
    private void anhXa(){
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
        ketqua = findViewById(R.id.ketqua);
    }

}