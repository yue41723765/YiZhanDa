package com.android.yzd.ui.fragment;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.yzd.R;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.ui.activity.OrderDetailsActivity;
import com.android.yzd.ui.custom.BaseFragment;
import com.android.yzd.ui.custom.RecyclerViewDivider;
import com.android.yzd.ui.view.MyItemDecoration;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class OrderFragment extends BaseFragment {

    @BindView(R.id.recycler)
    RecyclerView recycler;

    CommonAdapter adapter;


    @Override
    public int getContentViewId() {
        return R.layout.refresh_recycler;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {


        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        List<Integer> itemList = new ArrayList<>();
        itemList.add(R.drawable.light_1);
        itemList.add(R.drawable.light_2);
        itemList.add(R.drawable.light_3);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        recycler.setLayoutManager(linearLayoutManager);
        Drawable drawable1 = getResources().getDrawable(android.R.color.black);
        recycler.addItemDecoration(new MyItemDecoration(OrientationHelper.VERTICAL, drawable1, DensityUtils.dp2px(context, 3)));
        adapter = new CommonAdapter<String>(getContext(), R.layout.item_order, list) {
            RecyclerView recyclerView;

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                recyclerView = holder.getView(R.id.order_recycler);
                List<String> stringList = new ArrayList<>();
                stringList.add("");
                stringList.add("");
                stringList.add("");
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                Drawable drawable1 = getResources().getDrawable(android.R.color.white);
                recyclerView.addItemDecoration(new MyItemDecoration(OrientationHelper.VERTICAL, drawable1, DensityUtils.dp2px(context, 3)));
                CommonAdapter adapter = new CommonAdapter<String>(context, R.layout.item_order_item, stringList) {

                    @Override
                    protected void convert(ViewHolder holder, String s, int position) {

                    }
                };
                recyclerView.setAdapter(adapter);

            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {
                intent = new Intent(context, OrderDetailsActivity.class);
                startActivity(intent);
            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        recycler.setAdapter(adapter);
    }
}
