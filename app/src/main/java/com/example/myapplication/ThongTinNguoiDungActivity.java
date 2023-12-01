package com.example.myapplication;

import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_REF;
import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_USER_REF;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.myapplication.thuvien.PublicFunciton;
import com.example.myapplication.thuvien.User;
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

public class ThongTinNguoiDungActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private RelativeLayout relativeLayout_tenuser;
    private EditText editText_tenuser;
    private Button button_luu;
    private ImageView imageView_trove, imageView_user;
    Uri anhsanpham;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nguoi_dung);

        relativeLayout_tenuser = findViewById(R.id.tenuser);
        editText_tenuser = findViewById(R.id.edittenuser);
        imageView_trove = findViewById(R.id.img_trove);
        imageView_user = findViewById(R.id.imageuser);
        PRODUCT_IMAGE_USER_REF.child(PublicFunciton.getIdUser()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (uri != null){
                    Picasso.get().load(uri).into(imageView_user);
                }
            }
        });
        button_luu =findViewById(R.id.btnluu);

        imageView_trove.setOnClickListener(view ->
                getOnBackPressedDispatcher().onBackPressed()
        );
        button_luu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setValueFirebase();
            }
        });

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

        imageView_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
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
            anhsanpham=data.getData();
            imageView_user.setImageURI(selectImage);
        }
    }
    public void setValueFirebase() {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        String imageName = PublicFunciton.getIdUser();
        StorageReference imageRef = storageRef.child("images/" + imageName);
        imageRef.putFile(anhsanpham)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        imageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUri) {
                                String imageUrl = PublicFunciton.getIdUser();
                                String ten = editText_tenuser.getText().toString().trim();
                                String idUser = PublicFunciton.getIdUser();
                                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                                FirebaseUser fUser = firebaseAuth.getCurrentUser();
                                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                        .setDisplayName(ten)
                                        .build();
                                if (fUser != null){
                                    fUser.updateProfile(profileUpdates);
                                    Log.d("aaaaaaaa", "onSuccess: " + ten);
                                }
                                String name = PublicFunciton.getNameUser();
                                User user = new User(imageUrl,name,"","");
                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference userRef = database.getReference("User");
                                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                        userRef.child(idUser).setValue(user).addOnSuccessListener(aVoid -> {

                                        }).addOnFailureListener(e -> {
                                        });
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Xử lý lỗi nếu có
                                        Log.e("Firebase", "Lỗi khi đọc dữ liệu từ Firebase: " + databaseError.getMessage());
                                    }
                                });
                            }
                        });
                    }
                });
    }


}