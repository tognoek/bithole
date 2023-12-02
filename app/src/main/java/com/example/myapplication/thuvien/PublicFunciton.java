package com.example.myapplication.thuvien;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class PublicFunciton {
    public static final StorageReference PRODUCT_IMAGE_USER_REF = FirebaseStorage.getInstance("gs://thong-ab907.appspot.com").getReference("images");
    public static final StorageReference PRODUCT_IMAGE_REF = FirebaseStorage.getInstance("gs://thong-ab907.appspot.com").getReference("image");
    public PublicFunciton() {
    }
    public static String getNameUser() {
        String name = "Bit Hole";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            name = user.getDisplayName();
        }
        return name;
    }

    public static String getNameById(String id){
        String returnString = "";
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        DatabaseReference user = databaseReference.child(id);
        user.child("id").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.d("firebase", "Error getting data", task.getException());
                }
                else {
//                    returnString = String.valueOf(task.getResult().getValue());
                }
            }
        });
        return returnString;
    }

    public static String getIdUser(){
        String id = "Bit Hole";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            id = user.getUid();
        }
        return id;
    }
}
