package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.android.yzd.R;
import com.android.yzd.tools.K;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.MyItemDecoration;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class MessageManagerActivity extends BaseActivity {

    @BindView(R.id.message_recycler)
    RecyclerView messageRecycler;


    CommonAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_message_manage;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        messageRecycler.setLayoutManager(linearLayoutManager);
        messageRecycler.addItemDecoration(new MyItemDecoration(OrientationHelper.VERTICAL, getResources().getDrawable(R.color.background_2), 1));


        final List<String> list = new ArrayList<>();
        list.add("一站达客服");
        list.add("系统消息");
        list.add("订单消息");

        adapter = new CommonAdapter<String>(this, R.layout.item_message, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                holder.setText(R.id.message_title, s);
                switch (position) {
                    case 0:
                        Picasso.with(MessageManagerActivity.this).load(R.mipmap.message_service).into((ImageView) holder.getView(R.id.message_image));
                        break;
                    case 1:
                        Picasso.with(MessageManagerActivity.this).load(R.mipmap.message).into((ImageView) holder.getView(R.id.message_image));
                        break;
                    case 2:
                        Picasso.with(MessageManagerActivity.this).load(R.mipmap.message_order).into((ImageView) holder.getView(R.id.message_image));
                        break;
                }
            }
        };
        adapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                if (list.get(position).equals("系统消息")) {
                    intent = new Intent(MessageManagerActivity.this, SystemMessageActivity.class);
                    startActivity(intent);
                }
                if (list.get(position).equals("订单提示")) {
                }

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
        messageRecycler.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
