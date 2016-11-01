package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;

import com.android.yzd.R;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.custom.RecyclerViewDivider;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class CollectActivity extends BaseActivity {


    @BindView(R.id.collect_recycler)
    RecyclerView collectRecycler;

    CommonAdapter adapter;

    List<String> list = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        list.add("");
        list.add("");
        list.add("");
        list.add("");


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        collectRecycler.setLayoutManager(layoutManager);
        collectRecycler.addItemDecoration(new RecyclerViewDivider(this, OrientationHelper.VERTICAL));

        adapter = new CommonAdapter<String>(this, R.layout.item_collect, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {

            }
        };
        collectRecycler.setAdapter(adapter);
    }

}
