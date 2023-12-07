package com.example.myapplication.taikhoan;

import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_USER_REF;
import static com.example.myapplication.thuvien.PublicFunciton.getNameUser;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.thuvien.PublicFunciton;
import com.example.myapplication.entity.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class ThongTinNguoiDungActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final long ONE_YEAR_MILISECONDS = 31536000000L;
    private RelativeLayout relativeLayout_tenuser, relativeLayout_gioitinh, relativeLayout_sdt;
    private EditText editText_tenuser, editText_sdt;
    private Button button_luu;
    private RadioButton radioButton_gioitinh, radioButton_gioitinh1;
    private RadioGroup radioGroup;
    private ImageView imageView_trove, imageView_user, imageView_calendar;
    private TextView textView_tenuserBandau, textView_ngaysinh, textView_sdt;
    private DatePickerDialog datePickerDialog;
    private static final DatabaseReference USER_DATABASE_REF = FirebaseDatabase.getInstance().getReference("User");

    private int currentBirthday;
    Uri anhsanpham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nguoi_dung);

        anhXa();

        pushDetails();

        onClick();

        updateInf();

        hienThi();
        loadImage();

    }

    private void onClick() {

        imageView_trove.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View view) {
                                                   getOnBackPressedDispatcher().onBackPressed();
                                                   finish();
                                               }
                                           }
        );
        imageView_calendar.setOnClickListener(nothing -> { if (!datePickerDialog.isShowing()) datePickerDialog.show(); });
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis() - ONE_YEAR_MILISECONDS);
        datePickerDialog.setOnDateSetListener(this::setBirthday);
    }

    private void pushDetails() {
        PRODUCT_IMAGE_USER_REF.child(PublicFunciton.getIdUser()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (uri != null){
                    Picasso.get().load(uri).into(imageView_user);
                }
            }
        });
        editText_tenuser.setText(PublicFunciton.getNameUser());
        USER_DATABASE_REF.child(PublicFunciton.getIdUser()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                textView_tenuserBandau.setText(getNameUser());
                setBirthday(null, user.getNgaysinh());
                int length = user.getSdt().length();
                if (length > 3)
                    textView_sdt.setText("*".repeat(length - 3).concat(user.getSdt().substring(length - 3)));
                else
                    textView_sdt.setText(user.getSdt());
                if (user.getGioitinh())
                    radioButton_gioitinh.setChecked(true);
                else
                    radioButton_gioitinh1.setChecked(true);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void setBirthday(@Nullable View v, int date) {
        int year = date / 10000;
        int month = (date % 10000) / 100;
        int day = date % 100;
        setBirthday(v, year, month, day);
    }

    private void setBirthday(@Nullable View v, int year, int month, int day) {
        if (year == 0)
            return;
        Calendar birthday = Calendar.getInstance();
        birthday.set(year, month, day);
        datePickerDialog.getDatePicker().updateDate(year, month, day);
        textView_ngaysinh.setText(android.text.format.DateFormat.getMediumDateFormat(this).format(birthday.getTime()));
        currentBirthday = year * 10000 + month * 100 + day;
    }
    private void updateInf() {
        button_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValueFirebase();
            }
        });
    }

    private void loadImage() {
        imageView_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });
    }

    private void hienThi() {
        relativeLayout_tenuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(editText_tenuser.getVisibility() == View.VISIBLE){
                    editText_tenuser.setVisibility(View.GONE);
                }
                else
                {
                    editText_tenuser.setVisibility(View.VISIBLE);
                }
            }
        });
        relativeLayout_gioitinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(radioGroup.getVisibility() == View.VISIBLE){
                    radioGroup.setVisibility(View.GONE);
                }
                else
                {
                    radioGroup.setVisibility(View.VISIBLE);
                }
            }
        });
        relativeLayout_sdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText_sdt.getVisibility() == View.VISIBLE){
                    editText_sdt.setVisibility(View.GONE);
                }
                else
                {
                    editText_sdt.setVisibility(View.VISIBLE);
                }
            }
        });


    }

    public void openGallery(){
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PICK_IMAGE_REQUEST && data != null){
            Uri selectImage = data.getData();
            anhsanpham = data.getData();
            imageView_user.setImageURI(selectImage);
        }
    }
    public void setValueFirebase() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        String imageName = PublicFunciton.getIdUser();
        StorageReference imageRef = storageRef.child("images/" + imageName);
        Log.d("imageName", "Name Image: " + anhsanpham);
        if (anhsanpham != null){
            imageRef.putFile(anhsanpham)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri downloadUri) {
                                    String imageUrl = PublicFunciton.getIdUser();
                                    String ten = editText_tenuser.getText().toString().trim();
                                    boolean gioitinh = radioButton_gioitinh.isChecked();
                                    String sdt = editText_sdt.getText().toString();
                                    int ngaySinh = currentBirthday;
                                    String idUser = PublicFunciton.getIdUser();
                                    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                    FirebaseUser fUser = firebaseAuth.getCurrentUser();
                                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                            .setDisplayName(ten)
                                            .build();
                                    if (fUser != null){
                                        fUser.updateProfile(profileUpdates);
                                    }
                                    User user = new User(imageUrl, ten, gioitinh, sdt, ngaySinh);
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference userRef = database.getReference("User");
                                    userRef.child(idUser).setValue(user);
                                    Toast.makeText(ThongTinNguoiDungActivity.this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
        }
        else{
            String imageUrl = PublicFunciton.getIdUser();
            String ten = editText_tenuser.getText().toString().trim();
            boolean gioitinh = radioButton_gioitinh.isChecked();
            String sdt = editText_sdt.getText().toString();
            int ngaySinh = currentBirthday;
            String idUser = PublicFunciton.getIdUser();
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            FirebaseUser fUser = firebaseAuth.getCurrentUser();
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(ten)
                    .build();
            if (fUser != null){
                fUser.updateProfile(profileUpdates);
            }
            User user = new User(imageUrl, ten, gioitinh, sdt, ngaySinh);
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference userRef = database.getReference("User");
            userRef.child(idUser).setValue(user);
            Toast.makeText(ThongTinNguoiDungActivity.this, "Đã cập nhật thông tin", Toast.LENGTH_SHORT).show();
        }


    }

    private void anhXa(){
        relativeLayout_tenuser = findViewById(R.id.tenuser);
        relativeLayout_gioitinh = findViewById(R.id.gioitinh);
        relativeLayout_sdt = findViewById(R.id.sdt);
        editText_tenuser = findViewById(R.id.edittenuser);
        editText_sdt = findViewById(R.id.editsdt);
        imageView_trove = findViewById(R.id.img_trove);
        imageView_user = findViewById(R.id.imageuser);
        button_luu = findViewById(R.id.btnluu);
        textView_tenuserBandau = findViewById(R.id.tennguoidung);
        radioButton_gioitinh = findViewById(R.id.radio_gioitinh);
        radioButton_gioitinh1 = findViewById(R.id.radio_gioitinh1);
        radioGroup = findViewById(R.id.rabutton_gioitinh);
        textView_ngaysinh = findViewById(R.id.hienthi_Ngaysinh);
        textView_sdt = findViewById(R.id.hienthi_sdt);
        imageView_calendar = findViewById(R.id.calendar);
        datePickerDialog = new DatePickerDialog(this);
    }
}