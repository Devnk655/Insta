package com.example.insta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.util.List;

public class UsersPosts extends AppCompatActivity {
private LinearLayout linearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_posts);
        linearLayout = findViewById(R.id.linearLayout);
        Intent recievedIntentObject = getIntent();
        final String receivedUserName = recievedIntentObject.getStringExtra("username");
        FancyToast.makeText(this,receivedUserName,FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
        setTitle(receivedUserName +"'s posts");
        ParseQuery<ParseObject> parseQuery =  new ParseQuery<ParseObject>("Photo");
        parseQuery.whereEqualTo("username",receivedUserName);
        parseQuery.orderByDescending("createdAt");
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();
        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(objects.size() >0 && e== null){
                    for(ParseObject post:objects) {
                        TextView postDescription = new TextView(UsersPosts.this);
                        postDescription.setText(post.get("image_des")+"");
                        ParseFile postPicture = (ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(data!=null && e==null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data,0,data.length);
                                    ImageView postImageview = new ImageView(UsersPosts.this);
                                    LinearLayout.LayoutParams imageView_params =
                                            new LinearLayout.
                                                    LayoutParams(ViewGroup.
                                                    LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageView_params.setMargins(5,5,5,5);
                                    postImageview.setLayoutParams(imageView_params);
                                    postImageview.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageview.setImageBitmap(bitmap);
                                    LinearLayout.LayoutParams des_params =  new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
                                    des_params.setMargins(5,5,5,15);
                                    postDescription.setLayoutParams(des_params);
                                    postDescription.setGravity(Gravity.CENTER);
                                    postDescription.setBackgroundColor(Color.RED);
                                    postDescription.setTextColor(Color.WHITE);
                                    postDescription.setTextSize(30f);

                                    linearLayout.addView(postImageview);
                                    linearLayout.addView(postDescription);

                                }
                            }
                        });

                    }

                } else{
                    FancyToast.makeText(UsersPosts.this,receivedUserName +" does'nt have any posts", FancyToast.LENGTH_SHORT,FancyToast.INFO,true).show();
                    finish();
                }
            dialog.dismiss();
            }
        });

    }
}