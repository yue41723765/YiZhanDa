package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.tools.K;
import com.android.yzd.ui.custom.BaseActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class SystemMessageActivity extends BaseActivity {
    @BindView(R.id.titleBar_title)
    TextView title;
    @BindView(R.id.message_recycler)
    RecyclerView messageRecycler;


    CommonAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_system_message;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        messageRecycler.setLayoutManager(linearLayoutManager);

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        adapter = new CommonAdapter<String>(this, R.layout.item_system_message, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                TextView textView = holder.getView(R.id.details);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(SystemMessageActivity.this, SystemDetailsActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };
        messageRecycler.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
