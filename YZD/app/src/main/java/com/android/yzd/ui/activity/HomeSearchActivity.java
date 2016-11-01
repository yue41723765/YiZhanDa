package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.custom.RecyclerViewDivider;
import com.android.yzd.ui.view.MyItemDecoration;
import com.android.yzd.ui.view.RecyclerViewItemDecoration;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/3 0003.
 */
public class HomeSearchActivity extends BaseActivity {

    @BindView(R.id.hot_recycler)
    RecyclerView hotRecycler;
    @BindView(R.id.history_recycler)
    RecyclerView historyRecycler;
    @BindView(R.id.history_clear)
    TextView historyClear;


    CommonAdapter hotAdapter;
    CommonAdapter historyAdapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        List<String> list = new ArrayList<>();
        list.add("瓷砖");
        list.add("瓷砖2");
        list.add("瓷砖1");
        list.add("瓷砖4");
        list.add("瓷砖");
        list.add("瓷砖5");
        list.add("瓷砖6");
        list.add("瓷砖8");
        list.add("瓷砖9");

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        hotRecycler.setLayoutManager(gridLayoutManager);
        hotRecycler.addItemDecoration(new RecyclerViewItemDecoration(RecyclerViewItemDecoration.MODE_GRID, getResources().getColor(R.color.background), 2, 0, 0));
        hotAdapter = new CommonAdapter<String>(this, R.layout.item_home_search_hot, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.hot_text, s);
            }
        };
        hotRecycler.setAdapter(hotAdapter);

        List<String> history = new ArrayList<>();
        history.add("");
        history.add("");
        history.add("");
        history.add("");
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        historyRecycler.setLayoutManager(layoutManager);

        historyAdapter = new CommonAdapter<String>(this, R.layout.item_home_search_history, history) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        };
        historyRecycler.setAdapter(historyAdapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.search:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
