package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.AddressEntity;
import com.android.yzd.been.CartListBean;
import com.android.yzd.been.CouponEntity;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.been.UserRegAgr;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.T;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.TitleBarView;
import com.squareup.picasso.Picasso;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/6 0006.
 */
public class AddOrderActivity extends BaseActivity {

    @BindView(R.id.title_tools)
    TitleBarView titleTools;
    @BindView(R.id.order_sure)
    Button orderSure;
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
    @BindView(R.id.item_address)
    TextView itemAddress;
    @BindView(R.id.order_address)
    RelativeLayout orderAddress;
    @BindView(R.id.recycler)
    RecyclerView recycler;
    @BindView(R.id.order_minus)
    ImageView orderMinus;
    @BindView(R.id.order_buy_number)
    TextView orderBuyNumber;
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.order_freight)
    TextView orderFreight;
    @BindView(R.id.coupon)
    TextView coupon;
    @BindView(R.id.discount_coupon)
    RelativeLayout discountCoupon;
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
    @BindView(R.id.remark)
    RelativeLayout remark;
    @BindView(R.id.order_content)
    ScrollView orderContent;

    List<CartListBean> goodsBeanList;
    UserInfoEntity userInfo;
    AddressEntity addressEntity;
    float delivery_price;//运费
    float total = 0;

    CouponEntity entity;


    private String ob;
    @Override
    public int getContentViewId() {
        return R.layout.activity_add_order;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);
        goodsBeanList = getIntent().getParcelableArrayListExtra(K.DATA);
        userInfo = (UserInfoEntity) SPUtils.get(this, K.USERINFO, UserInfoEntity.class);
        delivery_price = getIntent().getFloatExtra("delivery_price", 0);
        orderFreight.setText("+￥" + delivery_price);
        int GoodsNum = 0;

        for (CartListBean cart : goodsBeanList) {
            GoodsNum += cart.getNumber();
            total += cart.getGoods_price() * cart.getNumber();
        }
        //保留两位小数
        DecimalFormat df = new DecimalFormat("#.##");
        String allPrice = "￥" + df.format(total + delivery_price);
        orderAllPrice.setText(allPrice);
        bottomAllPrice.setText(allPrice);


        goodsNumber.setText("共" + GoodsNum + "件商品");
        bottomGoodsNumber.setText(GoodsNum + "");


        init();
        setAdapter();
        getAddressInfo();
    }

    private void setAdapter() {
        CommonAdapter adapter = new CommonAdapter<CartListBean>(this, R.layout.item_add_order, goodsBeanList) {
            @Override
            protected void convert(ViewHolder holder, CartListBean cartListBean, int position) {
                Picasso.with(AddOrderActivity.this).load(cartListBean.getGoods_logo()).into((ImageView) holder.getView(R.id.order_image));
                holder.setText(R.id.order_title_, cartListBean.getGoods_name());
                holder.setText(R.id.order_number, "x" + cartListBean.getNumber());
                holder.setText(R.id.order_price, "￥" + cartListBean.getGoods_price());
            }
        };
        recycler.setAdapter(adapter);
    }

    private void init() {
        LinearLayoutManager manager = new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        manager.setOrientation(OrientationHelper.VERTICAL);
        recycler.setLayoutManager(manager);
        recycler.setItemAnimator(new DefaultItemAnimator());
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
                //addOrder();
                if (addressEntity == null) {
                    T.show(this, "请选择地址!", Toast.LENGTH_SHORT);
                    return;
                }
                List<String> ids = new ArrayList<>();
                for (CartListBean cart : goodsBeanList) {
                    Map<String, String> params = new HashMap<>();
                    params.put("cart_id", cart.getCart_id());
                    ids.add(gson.toJson(params));
                }
                intent.putExtra("cart_json",ids.toString());
                if (entity != null){
                    intent.putExtra("m_c_id",entity.getM_c_id());
                }
                intent.putExtra("remark", orderRemark.getText().toString());
                intent.putExtra("m_id",userInfo.getM_id());
                intent.putExtra("address_id",addressEntity.getAddress_id());
                Float allPrice = total + delivery_price;
                intent.putExtra("money",allPrice);
                //addOrder(ids.toString(), addressEntity.getAddress_id());
                startActivity(intent);
                break;
            case R.id.discount_coupon:
                intent = new Intent(this, DiscountCouponActivity.class);
                intent.putExtra(K.STATUS, 111);
                startActivityForResult(intent, 200);
                break;
            case R.id.order_address:
                intent = new Intent(this, AddressManageActivity.class);
                intent.putExtra(K.STATUS, 111);
                startActivityForResult(intent, 100);
                break;
        }
    }

    private void addOrder(String cart_json, String address_id) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                ob= (String) o;
                payDescription();
                T.show(AddOrderActivity.this, "下单成功", Toast.LENGTH_SHORT);
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.clear();
        httpParamet.addParameter("m_id", userInfo.getM_id());
        httpParamet.addParameter("cart_json", cart_json);
        httpParamet.addParameter("address_id", address_id);
        if (entity != null)
            httpParamet.addParameter("m_c_id", entity.getM_c_id());
        httpParamet.addParameter("remark", orderRemark.getText().toString());
        HttpMethods.getInstance(this).addOrder(progressSubscriber, httpParamet.bulider());
    }


    private void payDescription() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                UserRegAgr ura = gson.fromJson(gson.toJson(o), UserRegAgr.class);
                intent = new Intent(AddOrderActivity.this, PayActivity.class);
                intent.putExtra(K.DATA, ura);
                startActivity(intent);
                finish();
            }
        };
        setProgressSubscriber(onNextListener, false);
        HttpMethods.getInstance(this).payDescription(progressSubscriber);
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
            case 100:
                //地址
                addressEntity = data.getParcelableExtra(K.DATA);
                orderAddressName.setText("收货人：" + addressEntity.getConsignee());
                orderAddressTel.setText(addressEntity.getMobile());
                itemAddress.setText(addressEntity.getAddress());
                break;
            case 200:
                entity = data.getParcelableExtra(K.DATA);
                if (entity != null) {
                    float value = Float.valueOf(entity.getValue());
                    if (value < (total + delivery_price)) {
                        coupon.setText("-￥" + entity.getValue());

                        //保留两位小数
                        DecimalFormat df = new DecimalFormat("#.##");
                        String allPrice = "￥" + df.format(total + delivery_price - value);
                        orderAllPrice.setText(allPrice);
                        bottomAllPrice.setText(allPrice);

                    } else {
                        T.show(this, "优惠券满" + entity.getValue() + "元使用", Toast.LENGTH_SHORT);
                    }
                }
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private void getAddressInfo() {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                if (o.toString().equals("[]"))
                    return;
                addressEntity = gson.fromJson(gson.toJson(o), AddressEntity.class);
                if (addressEntity != null) {
                    orderAddressName.setText("收货人：" + addressEntity.getConsignee());
                    orderAddressTel.setText(addressEntity.getMobile());
                    itemAddress.setText(addressEntity.getAddress());
                }
            }
        };
        setProgressSubscriber(onNextListener);
        httpParamet.addParameter("m_id", userInfo.getM_id());
        HttpMethods.getInstance(this).getOneAddress(progressSubscriber, httpParamet.bulider());
    }
}
