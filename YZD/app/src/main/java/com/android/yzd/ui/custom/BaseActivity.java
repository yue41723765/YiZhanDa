package com.android.yzd.ui.custom;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;

import com.android.yzd.http.ProgressSubscriber;
import com.android.yzd.http.SubscriberOnNextListener;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/8/15.
 */

public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public Intent intent;

    public Gson gson = new Gson();
    public Map<String, String> params = new HashMap<>();

    public ProgressSubscriber progressSubscriber;

    public void setProgressSubscriber(SubscriberOnNextListener onNextListener) {
        progressSubscriber = new ProgressSubscriber(onNextListener, this, false);
    }

    public abstract int getContentViewId();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(getContentViewId());
        ButterKnife.bind(this);
        initAllMembersView(savedInstanceState);
    }

    protected abstract void initAllMembersView(Bundle savedInstanceState);


    public void onClick(View v) {

    }

}
