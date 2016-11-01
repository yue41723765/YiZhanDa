package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.K;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.MyItemDecoration;
import com.android.yzd.ui.view.TitleBarView;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2016/10/10 0010.
 */
public class AddressManageActivity extends BaseActivity {

    @BindView(R.id.tools_title)
    TitleBarView toolsTitle;
    @BindView(R.id.tools_bottom)
    Button toolsBottom;
    @BindView(R.id.am_recycler)
    RecyclerView amRecycler;

    CommonAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_address_manage;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        amRecycler.setLayoutManager(linearLayoutManager);
        Drawable drawable = getResources().getDrawable(R.color.background);
        amRecycler.addItemDecoration(new MyItemDecoration(OrientationHelper.VERTICAL, drawable, DensityUtils.dp2px(this, 10)));
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter = new CommonAdapter<String>(this, R.layout.item_address, list) {

            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                CheckBox checkBox = holder.getView(R.id.is_default);
                if (position == 0) {
                    checkBox.setChecked(true);
                }
                holder.getView(R.id.address_edit).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(AddressManageActivity.this, EditAddressActivity.class);
                        intent.putExtra(K.STATUS, 2);
                        startActivity(intent);
                    }
                });
            }
        };
        amRecycler.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tools_bottom:
                intent = new Intent(this, EditAddressActivity.class);
                intent.putExtra(K.STATUS, 1);
                startActivity(intent);
                break;
        }
    }
}
