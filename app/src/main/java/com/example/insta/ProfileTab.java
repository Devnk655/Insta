package com.example.insta;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import com.shashank.sony.fancytoastlib.FancyToast;


public class ProfileTab extends Fragment {
private EditText edtProfileName,edtProfileBio,edtProfileProfession,edtProfileHoobies,edtProfileFavSport;
private Button btnupdateinfo;


    public ProfileTab() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
   View view = inflater.inflate(R.layout.fragment_profile_tab,container,false);
   edtProfileName=view.findViewById(R.id.edtProfileName);
   edtProfileBio=view.findViewById(R.id.edtProfileBio);
   edtProfileProfession=view.findViewById(R.id.edtProfileProfession);
   edtProfileHoobies=view.findViewById(R.id.edtProfileHoobies);
   edtProfileFavSport=view.findViewById(R.id.edtProfileFavSport);
   btnupdateinfo=view.findViewById(R.id.btnUpdateInfo);
   ParseUser parseUser = ParseUser.getCurrentUser();
   btnupdateinfo.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(View view) {
           parseUser.put("profileName",edtProfileName.getText().toString());
           parseUser.put("profileBio",edtProfileBio.getText().toString());
           parseUser.put("profileProfession",edtProfileProfession.getText().toString());
           parseUser.put("profileHoobies",edtProfileHoobies.getText().toString());
           parseUser.put("profileFavSport",edtProfileFavSport.getText().toString());

           parseUser.saveInBackground(new SaveCallback() {
               @Override
               public void done(ParseException e) {
                   if(e==null){
                       FancyToast.makeText(getContext(), "Info is Updated", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                   } else{
                       FancyToast.makeText(getContext(),e.getMessage(), FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
                   }
               }
           });

       }
   });
   return view;
    }
}