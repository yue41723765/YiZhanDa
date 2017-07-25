package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.PayFindResultEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by 33181 on 2017/7/21.
 */

public class PayResultActivity extends BaseActivity {
    @BindView(R.id.pay_result_tv)TextView payTv;
    @BindView(R.id.title_tools)Toolbar title_tools;

    @Override
    public int getContentViewId() {
        return R.layout.activity_pay_result;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        title_tools.setNavigationIcon(R.mipmap.arrow_left_white);
        title_tools.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(PayResultActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        intent=getIntent();
        String result=intent.getStringExtra("PayResult");
        if ("arr".equals(result)){
            payTv.setText("已成功下单,请保持手机通畅");
        }else  if ("0".equals(result)){
            payTv.setText("支付失败");
        }else if ("1".equals(result)){
            payTv.setText("支付成功");
        }else {
            payTv.setText("抱歉，查询不到此订单");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            Intent intent=new Intent(PayResultActivity.this,MainActivity.class);
            startActivity(intent);
            finish();
            return false;
        }else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
