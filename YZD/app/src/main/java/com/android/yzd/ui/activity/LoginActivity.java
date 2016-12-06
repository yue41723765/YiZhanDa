package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.StatusBarUtil;
import com.android.yzd.tools.T;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class LoginActivity extends BaseActivity {

    @BindView(R.id.login_tel)
    EditText loginTel;
    @BindView(R.id.login_password)
    EditText loginPassword;
    @BindView(R.id.login_login)
    Button loginLogin;
    @BindView(R.id.forget_password)
    TextView forgetPassword;

    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
//        同意并阅读《一站达注册协议》
        AppManager.getAppManager().addActivity(this);
        loginTel.setText((String) SPUtils.get(this, K.USERNAME, ""));
    }

    @OnClick({R.id.titleBar_right_text})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_login:

                String tel = loginTel.getText().toString();
                String pass = loginPassword.getText().toString();
                if (tel.equals("")) {
                    T.show(this, "手机号码出错", Toast.LENGTH_SHORT);
                    return;
                }
                if (pass.equals("")) {
                    T.show(this, "密码出错", Toast.LENGTH_SHORT);
                    return;
                }
                Login(tel, pass);

                return;
            case R.id.forget_password:
                intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(K.TITLE, 2);
                startActivity(intent);
                break;
            case R.id.titleBar_right_text:
                intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(K.TITLE, 1);
                startActivity(intent);
                break;
        }
    }

    private void Login(final String tel, String pass) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                UserInfoEntity userInfo = gson.fromJson(gson.toJson(o), UserInfoEntity.class);
                SPUtils.put(LoginActivity.this, K.USERINFO, userInfo);
                SPUtils.put(LoginActivity.this, K.ISLOG, false);
                SPUtils.put(LoginActivity.this, K.USERNAME, tel);
                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        };
        setProgressSubscriber(onNextListener);
        params.clear();
        params.put("account", tel);
        params.put("password", pass);
        HttpMethods.getInstance(this).login(progressSubscriber, params);
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
