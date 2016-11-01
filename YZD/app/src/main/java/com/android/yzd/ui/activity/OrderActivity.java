package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import com.android.yzd.R;
import com.android.yzd.tools.K;
import com.android.yzd.ui.adapter.FragmentViewPagerAdapter;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class OrderActivity extends BaseActivity {


    @BindView(R.id.order_tablayout)
    TabLayout orderTablayout;
    @BindView(R.id.order_viewpager)
    ViewPager orderViewpager;

    String[] titles = new String[]{"全部", "待支付", "待发货", "待收货", "已完成"};

    @Override
    public int getContentViewId() {
        return R.layout.activity_order;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

        int status = getIntent().getExtras().getInt(K.STATUS);

        orderTablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        orderTablayout.addTab(orderTablayout.newTab().setText(titles[0]));
        orderTablayout.addTab(orderTablayout.newTab().setText(titles[1]));
        orderTablayout.addTab(orderTablayout.newTab().setText(titles[2]));
        orderTablayout.addTab(orderTablayout.newTab().setText(titles[3]));
        orderTablayout.addTab(orderTablayout.newTab().setText(titles[4]));

        FragmentViewPagerAdapter viewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), titles);
        orderViewpager.setAdapter(viewPagerAdapter);
        orderTablayout.setupWithViewPager(orderViewpager);
        orderViewpager.setCurrentItem(status);
    }

}
