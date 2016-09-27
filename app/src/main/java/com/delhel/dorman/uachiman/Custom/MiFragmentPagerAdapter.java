package com.delhel.dorman.uachiman.Custom;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

/**
 * Created by Usuario on 15/08/2016.
 */
public class MiFragmentPagerAdapter extends FragmentPagerAdapter {
    private String tabTitles[];
    private Fragment fragment[];

    public MiFragmentPagerAdapter(FragmentManager fm, String[] tabTitles, final Fragment[] fragment) {
        super(fm);
        this.tabTitles = tabTitles;
        this.fragment = fragment;
    }

    @Override
    public int getCount() {
        return tabTitles.length;
    }

    @Override
    public Fragment getItem(int position) {

        return fragment[position];

    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return super.isViewFromObject(view, object);
    }

}
