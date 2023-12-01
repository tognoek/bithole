package com.example.myapplication.adapter;

import static com.example.myapplication.thuvien.PublicFunciton.PRODUCT_IMAGE_USER_REF;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;

import com.example.myapplication.R;
import com.example.myapplication.thuvien.ComMent;
import com.example.myapplication.thuvien.PublicFunciton;
import com.example.myapplication.thuvien.SanPham;
import com.google.android.gms.tasks.OnSuccessListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterComment extends ArrayAdapter {

    Activity context;
    int idLayout;

    private HanderButton handerButton;
    ArrayList<ComMent> myList;

    private final int like = R.drawable.icon_like;
    private final int like_truoc = R.drawable.icon_liketruoc;

    public AdapterComment(@NonNull Activity context, int idLayout, ArrayList<ComMent> myList) {
        super(context, idLayout, myList);
        this.context = context;
        this.idLayout = idLayout;
        this.myList = myList;
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
                buttonlike.setImageResource(R.drawable.icon_like);
                buttonlike.setTag(R.drawable.like);
            }
        });

        noidung.setText(itemnew.getComment());
        time.setText(itemnew.getTime());
        user.setText(itemnew.getName());
        return convertView;
    }

    public void setHanderButton(HanderButton handerButton){
        this.handerButton = handerButton;
    }

    public interface HanderButton{
        void setOnlickLike(int positon);
    }
}
