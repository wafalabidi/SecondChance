package com.esprit.secondchance.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by sofien on 21/11/2017.
 */

public class CustomPagerAdapter extends FragmentStatePagerAdapter {
    Fragment[] fragments;
    FragmentManager fragmentManager;

    public CustomPagerAdapter(FragmentManager fm, Fragment[] fragments) {
        super(fm);
        this.fragmentManager = fm;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
