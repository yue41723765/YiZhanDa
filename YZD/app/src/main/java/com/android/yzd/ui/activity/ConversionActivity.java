package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.android.yzd.R;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.MyItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/10 0010.
 */
public class ConversionActivity extends BaseActivity {

    @BindView(R.id.conversion_recycler)
    RecyclerView conversionRecycler;

    CommonAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_conversion;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        conversionRecycler.setLayoutManager(linearLayoutManager);
        conversionRecycler.addItemDecoration(new MyItemDecoration(OrientationHelper.HORIZONTAL, getResources().getDrawable(R.color.background), 1));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        adapter = new CommonAdapter<String>(this, R.layout.item_history, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        };

        conversionRecycler.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
