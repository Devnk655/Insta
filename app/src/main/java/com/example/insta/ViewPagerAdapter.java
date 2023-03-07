package com.example.insta;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new ProfileTab();
        }else if(position==1){
            return new UserTab();
        } else{
            return new ShareImageTab();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
    public CharSequence getPageTitle(int position) {
        if(position==0){
            return "Profile";
        }else if(position==1){
            return "Users";
        }else{
            return "Share Image";
        }
    }
}
