package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.AddressEntity;
import com.android.yzd.been.IntegralListBean;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.T;
import com.android.yzd.ui.custom.BaseActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/12/22 0022.
 */

public class ConfirmConversionActivity extends BaseActivity {


    @BindView(R.id.address)
    ImageView address;
    @BindView(R.id.arrow_right)
    ImageView arrowRight;
    @BindView(R.id.order_address_name)
    TextView orderAddressName;
    @BindView(R.id.order_address_tel)
    TextView orderAddressTel;
    @BindView(R.id.order_address)
    RelativeLayout orderAddress;
    @BindView(R.id.find_image)
    ImageView findImage;
    @BindView(R.id.find_title)
    TextView findTitle;
    @BindView(R.id.find_content)
    TextView findContent;
    @BindView(R.id.find_integral)
    TextView findIntegral;
    @BindView(R.id.sure_conversion)
    Button sureConversion;
    @BindView(R.id.item_address)
    TextView itemAddress;


    IntegralListBean integralListBean;
    UserInfoEntity userInfo;
    AddressEntity addressEntity;

    @Override
    public int getContentViewId() {
        return R.layout.activity_true_conversion;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        userInfo = (UserInfoEntity) SPUtils.get(this, K.USERINFO, UserInfoEntity.class);
        integralListBean = getIntent().getParcelableExtra(K.DATA);

        Picasso.with(this).load(integralListBean.getGoods_logo()).into(findImage);
        findTitle.setText(integralListBean.getGoods_name());
        findContent.setText(integralListBean.getGoods_brief());
        findIntegral.setText(integralListBean.getNeed_integral());
        getAddressInfo();
    }

    private void getAddressInfo() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                addressEntity = gson.fromJson(gson.toJson(o), AddressEntity.class);
                orderAddressName.setText("收货人：" + addressEntity.getConsignee());
                orderAddressTel.setText(addressEntity.getMobile());
                itemAddress.setText(addressEntity.getAddress());
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.addParameter("m_id", userInfo.getM_id());
        HttpMethods.getInstance(this).getOneAddress(progressSubscriber, httpParamet.bulider());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sure_conversion:
                exchangeGoods();
                break;
        }
    }

    private void exchangeGoods() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                T.show(ConfirmConversionActivity.this, "兑换成功", Toast.LENGTH_SHORT);
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.clear();
        httpParamet.addParameter("m_id", userInfo.getM_id());
        httpParamet.addParameter("i_g_id", integralListBean.getI_g_id());
        httpParamet.addParameter("address_id", addressEntity.getAddress_id());
        HttpMethods.getInstance(this).exchangeGoods(progressSubscriber, httpParamet.bulider());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
