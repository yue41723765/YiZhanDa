package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.been.MessageEntity;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.ProgressSubscriber;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.StatusBarUtil;
import com.android.yzd.tools.U;
import com.android.yzd.ui.custom.BaseActivity;
import com.google.gson.reflect.TypeToken;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/10/17 0017.
 */
public class OrderMessageActivity extends BaseActivity {
    @BindView(R.id.titleBar_title)
    TextView title;
    @BindView(R.id.message_recycler)
    RecyclerView messageRecycler;


    CommonAdapter adapter;

    SubscriberOnNextListener onNextListener;
    ProgressSubscriber progressSubscriber;

    Map<String, String> params = new HashMap<>();
    UserInfoEntity userInfoEntity;


    List<MessageEntity> messageEntities = new ArrayList<>();

    @Override
    public int getContentViewId() {
        return R.layout.activity_system_message;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);

//        角色类型：receiver_type 2企业用户，3原产地商家
        String receiver_type = getIntent().getExtras().getString("receiver_type");

        userInfoEntity = (UserInfoEntity) SPUtils.get(this, K.USERINFO, UserInfoEntity.class);
        params.put("m_id", userInfoEntity.getM_id());
        params.put("receiver_type", receiver_type);
        params.put("p", "1");
        onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                List<MessageEntity> messageEntities = gson.fromJson(gson.toJson(o), new TypeToken<List<MessageEntity>>() {
                }.getType());
                OrderMessageActivity.this.messageEntities.addAll(messageEntities);
                adapter.notifyDataSetChanged();
            }
        };
//        progressSubscriber = new ProgressSubscriber(onNextListener, this);
//        HttpMethods.getInstance(this).orderMessageList(progressSubscriber, params);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        messageRecycler.setLayoutManager(linearLayoutManager);
        messageRecycler.setItemAnimator(new DefaultItemAnimator());

        adapter = new CommonAdapter<MessageEntity>(this, R.layout.item_system_message, messageEntities) {

            @Override
            protected void convert(ViewHolder holder, MessageEntity info, int position) {
                holder.setText(R.id.item_date, U.timeStampToStr(info.getCreate_time()));
                holder.setText(R.id.message_title, info.getMsg_content());
                holder.setText(R.id.message_content, info.getPick_addr());

                TextView textView = holder.getView(R.id.details);
                textView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent = new Intent(OrderMessageActivity.this, SystemDetailsActivity.class);
                        startActivity(intent);
                    }
                });
            }
        };
        messageRecycler.setAdapter(adapter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.WHITE);
            StatusBarUtil.StatusBarLightMode(this);
        }
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
