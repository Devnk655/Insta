package com.example.insta;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;

import java.io.ByteArrayOutputStream;

public class ShareImageTab extends Fragment implements View.OnClickListener {
    private ImageView imgShare;
    private EditText edtDescription;
    private Button btnshareimage;
    Bitmap recievedImageBitmap;



    public ShareImageTab() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view= inflater.inflate(R.layout.fragment_share_image_tab, container, false);
        imgShare = view.findViewById(R.id.imageshare);
        edtDescription = view.findViewById(R.id.edtDescription);
        btnshareimage = view.findViewById(R.id.btnshareiimage);
        imgShare.setOnClickListener(ShareImageTab.this);
        btnshareimage.setOnClickListener(ShareImageTab.this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageshare:
            if(Build.VERSION.SDK_INT>=23 &&
                    ActivityCompat.checkSelfPermission(getContext(),
                            Manifest.permission.READ_EXTERNAL_STORAGE)
                            !=PackageManager.PERMISSION_GRANTED){
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1000);
            }else{
                getChosenImage();
            }
            break;
            case R.id.btnshareiimage:
                if(recievedImageBitmap!=null){
                    if(edtDescription.getText().toString().equals("")){
                        FancyToast.makeText(getContext(),
                                "Error:You should sepcify a description",
                                FancyToast.LENGTH_LONG, FancyToast.ERROR,
                                true).show();

                    } else{
                        ByteArrayOutputStream byteArrayOutputStream =  new ByteArrayOutputStream();
                        recievedImageBitmap.compress(Bitmap.CompressFormat.PNG,100,byteArrayOutputStream);
                        byte[]bytes=byteArrayOutputStream.toByteArray();
                        ParseFile parseFile = new ParseFile("faceebook.png",bytes);
                        ParseObject parseObject = new ParseObject("Photo");
                        parseObject.put("picture",parseFile);
                        parseObject.put("image_des",edtDescription.getText().toString());
                        parseObject.put("username", ParseUser.getCurrentUser().getUsername());
                        final ProgressDialog dialog = new ProgressDialog(getContext());
                        dialog.setMessage("Loading");
                        dialog.show();
                        parseObject.saveInBackground(new SaveCallback() {
                            @Override
                            public void done(ParseException e) {

                                if (e == null) {
                                    FancyToast.makeText(getContext(),
                                            "Done!!",
                                            FancyToast.LENGTH_LONG, FancyToast.SUCCESS,
                                            true).show();
                                }else{
                                    FancyToast.makeText(getContext(),
                                            "Unknown Error" +e.getMessage(),
                                            FancyToast.LENGTH_LONG, FancyToast.ERROR,
                                            true).show();
                                }
                                dialog.dismiss();
                            }
                        });
                    }

                }else{
                    FancyToast.makeText(getContext(),
                                    "Error:You must select an image ",
                            FancyToast.LENGTH_LONG, FancyToast.ERROR,
                            true).show();
                }
                break;
        }
        }

    private void getChosenImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent,2000);
        //FancyToast.makeText(getContext(),"Now we can access the images",FancyToast.LENGTH_SHORT,FancyToast.SUCCESS,true).show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]==
                PackageManager.PERMISSION_GRANTED){
            getChosenImage();

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==2000){
        if(resultCode== Activity.RESULT_OK) {
            try {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getActivity().getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columIndex);
                cursor.close();
                recievedImageBitmap= BitmapFactory.decodeFile(picturePath);
                imgShare.setImageBitmap(recievedImageBitmap);
            } catch (Exception e) {
                    e.printStackTrace();
            }
        }
        }
    }
}