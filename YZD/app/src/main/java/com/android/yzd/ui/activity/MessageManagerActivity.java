package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.SystemMessageEntity;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.ProgressSubscriber;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.L;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.StatusBarUtil;
import com.android.yzd.ui.custom.BaseActivity;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/11 0011.
 */
public class MessageManagerActivity extends BaseActivity {

    SubscriberOnNextListener messageOnNextListener;
    ProgressSubscriber progressSubscriber;
    Map<String, String> params = new HashMap<>();
    UserInfoEntity userInfo;

    @BindView(R.id.service_image)
    ImageView serviceImage;
    @BindView(R.id.service_number)
    TextView serviceNumber;
    @BindView(R.id.service_title)
    TextView serviceTitle;
    @BindView(R.id.service_content)
    TextView serviceContent;
    @BindView(R.id.service_data)
    TextView serviceData;
    @BindView(R.id.service_message)
    RelativeLayout serviceMessage;
    @BindView(R.id.system_image)
    ImageView systemImage;
    @BindView(R.id.system_number)
    TextView systemNumber;
    @BindView(R.id.system_title)
    TextView systemTitle;
    @BindView(R.id.system_content)
    TextView systemContent;
    @BindView(R.id.system_data)
    TextView systemData;
    @BindView(R.id.system_message)
    RelativeLayout systemMessage;
    @BindView(R.id.order_image)
    ImageView orderImage;
    @BindView(R.id.order_number)
    TextView orderNumber;
    @BindView(R.id.order_title)
    TextView orderTitle;
    @BindView(R.id.message_content)
    TextView messageContent;
    @BindView(R.id.order_data)
    TextView orderData;
    @BindView(R.id.order_message)
    RelativeLayout orderMessage;

    @Override
    public int getContentViewId() {
        return R.layout.activity_message_manage;
    }


    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);

        userInfo = (UserInfoEntity) SPUtils.get(this, K.USERINFO, UserInfoEntity.class);
        setServiceMessage();

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.service_message:
                L.i(messageEntity.getService_account() + "");

                intent = new Intent(MessageManagerActivity.this, MessageActivity.class);
                intent.putExtra("nameNick", "一站达客服");
                intent.putExtra("ecId", messageEntity.getService_account());
                intent.putExtra("yourHead", messageEntity.getService_logo());
                intent.putExtra("myHead", userInfo.getHead_pic());
                startActivity(intent);
                break;
            case R.id.system_message:
                intent = new Intent(MessageManagerActivity.this, SystemMessageActivity.class);
                intent.putExtra("receiver_type", "3");
                startActivity(intent);
                SPUtils.put(this, "sysNumber", sys_num);
                break;
            case R.id.order_message:
                intent = new Intent(MessageManagerActivity.this, OrderMessageActivity.class);
                intent.putExtra("receiver_type", "3");
                startActivity(intent);
                SPUtils.put(this, "OrderNumber", order_num);
                break;
        }
    }

    SystemMessageEntity messageEntity;

    //系统消息
    private void setServiceMessage() {
        messageOnNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                messageEntity = gson.fromJson(gson.toJson(o), SystemMessageEntity.class);
                showUi(messageEntity);
            }
        };
        setProgressSubscriber(messageOnNextListener);
        params.clear();
        params.put("m_id", userInfo.getM_id());
        HttpMethods.getInstance(this).messageIndex(progressSubscriber, params);
    }

    int sys_num;
    int order_num;

    private void showUi(SystemMessageEntity messageEntity) {

//        Picasso.with(this).load(messageEntity.getCustomer_head_pic()).into(serviceImage);
        sys_num = Integer.valueOf(messageEntity.getSystem_count());
        int msysNumber = (int) SPUtils.get(this, "sysNumber", 0);
        if (sys_num != msysNumber) {
            systemNumber.setVisibility(View.VISIBLE);
            systemNumber.setText(sys_num + "");
        }
        systemContent.setText(messageEntity.getSystem_title());
        systemData.setText(messageEntity.getSystem_time());
        order_num = Integer.valueOf(messageEntity.getNotice_count());
        int OrderNumber = (int) SPUtils.get(this, "OrderNumber", 0);
        if (order_num != OrderNumber) {
            orderNumber.setVisibility(View.VISIBLE);
            orderNumber.setText(order_num + "");
        }

        systemContent.setText(messageEntity.getNotice_title());
        messageContent.setText(messageEntity.getNotice_title());
    }


    public void setProgressSubscriber(SubscriberOnNextListener listener) {
        progressSubscriber = new ProgressSubscriber(listener, this, false, false);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.WHITE);
            StatusBarUtil.StatusBarLightMode(this);
        }
        ButterKnife.bind(this);
    }
}
