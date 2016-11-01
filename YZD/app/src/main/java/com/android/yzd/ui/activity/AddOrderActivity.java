package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/6 0006.
 */
public class AddOrderActivity extends BaseActivity {

    @BindView(R.id.bottom_goods_number)
    TextView bottomGoodsNumber;
    @BindView(R.id.bottom_all_price)
    TextView bottomAllPrice;
    @BindView(R.id.bottom_tools)
    RelativeLayout bottomTools;
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
    @BindView(R.id.order_image)
    ImageView orderImage;
    @BindView(R.id.order_number)
    TextView orderNumber;
    @BindView(R.id.order_price)
    TextView orderPrice;
    @BindView(R.id.order_buy_number)
    TextView orderBuyNumber;
    @BindView(R.id.order_freight)
    TextView orderFreight;
    @BindView(R.id.discount_coupon)
    TextView discountCoupon;
    @BindView(R.id.goods_number)
    TextView goodsNumber;
    @BindView(R.id.order_all_price)
    TextView orderAllPrice;
    @BindView(R.id.pay_type)
    TextView payType;
    @BindView(R.id.invoice_head)
    TextView invoiceHead;
    @BindView(R.id.order_remark)
    TextView orderRemark;
    @BindView(R.id.order_content)
    ScrollView orderContent;

    @Override
    public int getContentViewId() {
        return R.layout.activity_add_order;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.remark:
                intent = new Intent(this, RemarkActivity.class);
                startActivityForResult(intent, RemarkActivity.ResultNumber);
                break;
            case R.id.order_sure:
                intent = new Intent(this, PayActivity.class);
                startActivity(intent);
                break;

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null)
            return;
        switch (requestCode) {
            case RemarkActivity.ResultNumber:
                orderRemark.setText(data.getExtras().getString(RemarkActivity.RemarkResult));
                break;
        }
    }
}
