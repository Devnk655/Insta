package com.example.insta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.shashank.sony.fancytoastlib.FancyToast;

public class LoginActivity extends AppCompatActivity {
private EditText edtusername,edtpassword;
private Button btnlogin;
private TextView txtsignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        edtusername = findViewById(R.id.edtusername);
        edtpassword = findViewById(R.id.edtpassword);
        btnlogin =  findViewById(R.id.btnlogin);
        txtsignup=findViewById(R.id.txtsignup);
        txtsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =  new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });
        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ParseUser.logInInBackground(edtusername.getText().toString(), edtpassword.getText().toString(), new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        if(user!=null && e==null){
                            FancyToast.makeText(LoginActivity.this, user.get("username")+"is  LoggedIn  sucessfully ", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                            ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
                            progressDialog.setMessage("Logining In"+edtusername.getText().toString());
                            progressDialog.show();
                            transisationToSocialMediaActivity();


                        } else{
                            FancyToast.makeText(LoginActivity.this, e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();

                        }

                    }
                });

            }
        });
    }
    private void transisationToSocialMediaActivity(){
        Intent intent =  new Intent(LoginActivity.this,SocialMediaActivity.class);
        startActivity(intent);
        finish();
    }


}