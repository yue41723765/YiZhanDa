package com.android.yzd.ui.custom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/8/16.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {

    protected Context context;
    protected View mRootView;

    public Intent intent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mRootView = inflater.inflate(getContentViewId(), container, false);
        ButterKnife.bind(this, mRootView);
        this.context = getActivity();
        onCreateView(savedInstanceState);
        return mRootView;
    }

    public abstract int getContentViewId();

    protected abstract void onCreateView(Bundle savedInstanceState);


    public void onClick(View v) {
    }
}
