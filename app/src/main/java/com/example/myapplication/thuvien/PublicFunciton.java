package com.example.myapplication.thuvien;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

    public static String getIdUser(){
        String id = "Bit Hole";
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            id = user.getUid();
        }
        return id;
    }
}
