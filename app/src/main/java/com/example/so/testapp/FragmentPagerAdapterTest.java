package com.example.so.testapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;

public class FragmentPagerAdapterTest extends FragmentPagerAdapter {

    public FragmentPagerAdapterTest(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return new GraphFragment();
            case 1:
                return new GraphFragment();
            default:
                return new GraphFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + (position+1);
    }
}