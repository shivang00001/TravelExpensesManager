package com.trip.colleaguesexpmanager.adapters;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.trip.colleaguesexpmanager.fragments.FragmentLogin;
import com.trip.colleaguesexpmanager.fragments.FragmentRegister;


public class CustomPagerLoginRegisterAdapter extends android.support.v4.app.FragmentStatePagerAdapter {

    int mNumOfTabs;
    Activity activity;


    public CustomPagerLoginRegisterAdapter(FragmentManager fm, int NumOfTabs, Activity activity) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
        this.activity = activity;

    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0) {
            FragmentLogin tabLogin = new FragmentLogin();
            return tabLogin;
        }
        if(position == 1) {
            FragmentRegister tabRegister = new FragmentRegister();
            return tabRegister;
        }

        else
        {
            return null;
        }




    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }


}

