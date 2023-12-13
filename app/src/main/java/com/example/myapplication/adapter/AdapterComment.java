package com.example.myapplication.adapter;

import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_USER_REF;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.R;
import com.example.myapplication.entity.ComMent;
import com.example.myapplication.thuvien.PublicFunciton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterComment extends ArrayAdapter {

    Activity context;
    int idLayout;

    private HanderButton handerButton;
    ArrayList<ComMent> myList;

    private final int like = R.drawable.icon_like;
    private final int like_truoc = R.drawable.icon_liketruoc;
    private int IdSanPham;

    public AdapterComment(@NonNull Activity context, int idLayout, ArrayList<ComMent> myList, int IdSanPham) {
        super(context, idLayout, myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
        this.IdSanPham = IdSanPham;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        LayoutInflater myInflactor = context.getLayoutInflater();
        convertView = myInflactor.inflate(idLayout,null);
        ComMent itemnew = myList.get(position);
        //Anh Xa
        ImageView imageUser = convertView.findViewById(R.id.imageuser);
        TextView noidung = convertView.findViewById(R.id.noidungbl);
        TextView time = convertView.findViewById(R.id.timeBL);
        TextView user = convertView.findViewById(R.id.tenUserBL);
        ImageView buttonlike = convertView.findViewById(R.id.btnlike);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Like")
                .child(PublicFunciton.getIdUser())
                        .child(String.valueOf(this.IdSanPham))
                                .child(String.valueOf(itemnew.getStt()));
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int check = snapshot.getValue(Integer.class);
                    Log.d("check", "check: True: " + position );
                    if (check == 1){
                        buttonlike.setImageResource(R.drawable.icon_like);
                        buttonlike.setTag(like);
                    }
                    else{
                        buttonlike.setImageResource(R.drawable.icon_liketruoc);
                        buttonlike.setTag(like_truoc);
                    }

                }
                else{
                    Log.d("check", "check: False: " + position);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//        databaseReference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DataSnapshot> task) {
//                if (task.isSuccessful()){
//                    if (Integer.parseInt(String.valueOf(task.getResult().getValue())) == 1){
//                        buttonlike.setImageResource(R.drawable.icon_like);
//                        buttonlike.setTag(like);
//                    }
//                    else{
//                        buttonlike.setImageResource(R.drawable.icon_liketruoc);
//                        buttonlike.setTag(like_truoc);
//                    }
//                }
//            }
//        });

        PRODUCT_IMAGE_USER_REF.child(itemnew.getId()).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                if (uri != null){
                    Picasso.get().load(uri).into(imageUser);
                }
            }
        });
        buttonlike.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                handerButton.setOnlickLike(position);
                if (buttonlike.getTag() != null){
                    if (Integer.parseInt(String.valueOf(buttonlike.getTag())) == like_truoc){
                        buttonlike.setImageResource(R.drawable.icon_like);
                        Log.d("like", "onClick: like" );
                        SetLike(String.valueOf(itemnew.getStt()), 1);
                        buttonlike.setTag(like);
                    }
                    else{
                        buttonlike.setImageResource(R.drawable.icon_liketruoc);
                        SetLike(String.valueOf(itemnew.getStt()), 0);
                        buttonlike.setTag(like_truoc);
                        Log.d("like", "onClick: dislike" );
                    }
                }
                else{
                    buttonlike.setImageResource(R.drawable.icon_like);
                    SetLike(String.valueOf(itemnew.getStt()), 1);
                    buttonlike.setTag(like);
                    Log.d("like", "onClick: like" );
                }
            }
        });

        noidung.setText(itemnew.getComment());
        time.setText(itemnew.getTime());
//        UserRecord userRecord = FirebaseAuth.getInstance().(itemnew.getId());
//        user.setText(itemnew.getName());

        databaseReference = firebaseDatabase.getReference("User");
        DatabaseReference userRef = databaseReference.child(itemnew.getId());
        userRef.child("ten").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.d("firebase", "Error getting data", task.getException());
                }
                else {
                    user.setText(String.valueOf(task.getResult().getValue()));
//                    returnString = String.valueOf(task.getResult().getValue());
                }
            }
        });
        return convertView;
    }

    public void setHanderButton(HanderButton handerButton){
        this.handerButton = handerButton;
    }

    private void SetLike(String stt, int value){
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("Like")
                .child(PublicFunciton.getIdUser())
                .child(String.valueOf(this.IdSanPham))
                .child(stt);
        databaseReference.setValue(value);
    }

    public interface HanderButton{
        void setOnlickLike(int positon);
    }
}
