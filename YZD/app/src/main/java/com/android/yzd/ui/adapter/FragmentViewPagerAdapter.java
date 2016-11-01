package com.android.yzd.ui.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.android.yzd.tools.K;
import com.android.yzd.ui.fragment.OrderFragment;


/**
 * Created by Administrator on 2016/9/21.
 */

public class FragmentViewPagerAdapter extends FragmentPagerAdapter {
    String[] mTitle;

    public FragmentViewPagerAdapter(FragmentManager fm, String[] mTitle) {
        super(fm);
        this.mTitle = mTitle;
    }

    @Override
    public Fragment getItem(int position) {
        return newInstance(mTitle[position], position);
    }

    @Override
    public int getCount() {
        return mTitle.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }

    public Fragment newInstance(String title, int position) {
        OrderFragment orderFragment = new OrderFragment();
        Bundle bundle = new Bundle();
        bundle.putString(K.STATUS, title);
        bundle.putInt(K.POSITION, position);
        orderFragment.setArguments(bundle);
        return orderFragment;
    }
}