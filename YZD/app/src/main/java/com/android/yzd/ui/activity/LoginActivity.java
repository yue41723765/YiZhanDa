package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
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
    }

    @OnClick({R.id.titleBar_right_text})
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_login:
                SPUtils.put(this, K.ISLOG, false);
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
