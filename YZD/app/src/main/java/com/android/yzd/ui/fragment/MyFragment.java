package com.android.yzd.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.ui.activity.AddressManageActivity;
import com.android.yzd.ui.activity.BankCardActivity;
import com.android.yzd.ui.activity.CollectActivity;
import com.android.yzd.ui.activity.DiscountCouponActivity;
import com.android.yzd.ui.activity.IntegralActivity;
import com.android.yzd.ui.activity.MyInfoActivity;
import com.android.yzd.ui.activity.OrderActivity;
import com.android.yzd.ui.activity.SetActivity;
import com.android.yzd.ui.activity.WalletActivity;
import com.android.yzd.ui.custom.BaseFragment;
import com.android.yzd.ui.view.CircleImageView;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class MyFragment extends BaseFragment {
    @BindView(R.id.my_message)
    ImageView myMessage;
    @BindView(R.id.top_tools)
    RelativeLayout topTools;
    @BindView(R.id.my_head)
    CircleImageView myHead;
    @BindView(R.id.my_nick)
    TextView myNick;
    @BindView(R.id.my_tel)
    TextView myTel;

    UserInfoEntity userInfo;

    @Override
    public int getContentViewId() {
        return R.layout.fragment_my;
    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {

    }

    @OnClick({R.id.my_message, R.id.my_info, R.id.discount_coupon, R.id.my_collect, R.id.my_allOrder,
            R.id.my_wait_pay, R.id.my_wait_delivery, R.id.my_wait_getGoods, R.id.my_accomplish,
            R.id.my_wallet, R.id.my_shopping_mall_store, R.id.bank_card, R.id.address_manage, R.id.my_setting})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.my_message:
                break;
            case R.id.my_info:
                intent = new Intent(getContext(), MyInfoActivity.class);
                startActivity(intent);
                break;
            case R.id.discount_coupon:
                intent = new Intent(getContext(), DiscountCouponActivity.class);
                startActivity(intent);
                break;
            case R.id.my_collect:
                intent = new Intent(getContext(), CollectActivity.class);
                startActivity(intent);
                break;
            case R.id.my_allOrder:
                intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra(K.STATUS, 0);
                startActivity(intent);
                break;
            case R.id.my_wait_pay:
                intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra(K.STATUS, 1);
                startActivity(intent);
                break;
            case R.id.my_wait_delivery:
                intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra(K.STATUS, 2);
                startActivity(intent);
                break;
            case R.id.my_wait_getGoods:
                intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra(K.STATUS, 3);
                startActivity(intent);
                break;
            case R.id.my_accomplish:
                intent = new Intent(getContext(), OrderActivity.class);
                intent.putExtra(K.STATUS, 4);
                startActivity(intent);
                break;
            case R.id.my_wallet:
                intent = new Intent(getContext(), WalletActivity.class);
                startActivity(intent);
                break;
            case R.id.bank_card:
                intent = new Intent(getContext(), BankCardActivity.class);
                startActivity(intent);
                break;
            case R.id.address_manage:
                intent = new Intent(getContext(), AddressManageActivity.class);
                startActivity(intent);
                break;
            case R.id.my_setting:
                intent = new Intent(getContext(), SetActivity.class);
                startActivity(intent);
                break;
            case R.id.my_shopping_mall_store:
                intent = new Intent(getContext(), IntegralActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        userInfo = (UserInfoEntity) SPUtils.get(context, K.USERINFO, UserInfoEntity.class);
        showUserInfo();
    }

    //刷新个人信息
    private void showUserInfo() {
        Picasso.with(context).load(userInfo.getHead_pic()).into(myHead);
        myNick.setText(userInfo.getNickname());
        myTel.setText(userInfo.getAccount());
    }


}
