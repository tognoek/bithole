package com.example.myapplication.thuvien;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PublicFunciton {
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
