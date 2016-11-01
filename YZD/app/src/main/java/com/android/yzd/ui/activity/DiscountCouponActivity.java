package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.ui.custom.BaseActivity;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class DiscountCouponActivity extends BaseActivity {

    @BindView(R.id.not_discount_coupon)
    LinearLayout notDiscountCoupon;
    @BindView(R.id.discount_coupon_recycler)
    RecyclerView discountCouponRecycler;

    CommonAdapter adapter;

    @Override
    public int getContentViewId() {
        return R.layout.activity_discount_coupon;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        discountCouponRecycler.setVisibility(View.VISIBLE);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        discountCouponRecycler.setLayoutManager(linearLayoutManager);

        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter = new CommonAdapter<String>(this, R.layout.item_coupon, list) {

            ImageView coupon_top;
            TextView title;
            TextView coupon_validity;
            TextView coupon_price_title;
            TextView coupon_price;
            TextView coupon_full;
            TextView coupon_user;
            ImageView coupon_status;



            @Override
            protected void convert(ViewHolder holder, String s, int position) {
                coupon_top = holder.getView(R.id.coupon_top);
                title = holder.getView(R.id.coupon_name);
                coupon_validity = holder.getView(R.id.coupon_validity);
                coupon_price_title = holder.getView(R.id.coupon_price_title);
                coupon_price = holder.getView(R.id.coupon_price);
                coupon_full = holder.getView(R.id.coupon_full);
                coupon_user = holder.getView(R.id.coupon_user);
                coupon_status = holder.getView(R.id.coupon_status);
                if (position == 3) {

                    coupon_top.setImageResource(R.mipmap.coupon_top_2);
                    coupon_status.setVisibility(View.VISIBLE);
                    title.setTextColor(getResources().getColor(R.color.black_90));
                    coupon_validity.setTextColor(getResources().getColor(R.color.black_90));
                    coupon_price_title.setTextColor(getResources().getColor(R.color.black_90));
                    coupon_price.setTextColor(getResources().getColor(R.color.black_90));
                    coupon_full.setTextColor(getResources().getColor(R.color.black_90));
                    coupon_user.setTextColor(getResources().getColor(R.color.black_90));
                }
                if (position == 4) {
                    coupon_top.setImageResource(R.mipmap.coupon_top_2);
                    coupon_status.setVisibility(View.VISIBLE);
                    coupon_status.setImageResource(R.mipmap.have_expired);
                    title.setTextColor(getResources().getColor(R.color.black_90));
                    coupon_validity.setTextColor(getResources().getColor(R.color.black_90));
                    coupon_price_title.setTextColor(getResources().getColor(R.color.black_90));
                    coupon_price.setTextColor(getResources().getColor(R.color.black_90));
                    coupon_full.setTextColor(getResources().getColor(R.color.black_90));
                    coupon_user.setTextColor(getResources().getColor(R.color.black_90));
                }
            }
        };
        discountCouponRecycler.setAdapter(adapter);
    }

}
