package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.tools.K;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class Register2Activity extends BaseActivity {


    @BindView(R.id.titleBar_title)
    TextView title;
    @BindView(R.id.binding_tel)
    TextView bindingTel;
    @BindView(R.id.setPassword)
    TextView setPassword;
    @BindView(R.id.register_password)
    EditText registerPassword;
    @BindView(R.id.register_again_password)
    EditText registerAgainPassword;
    @BindView(R.id.register_register)
    Button registerRegister;
    int status_title;

    @Override
    public int getContentViewId() {
        return R.layout.activity_register_2;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        status_title = getIntent().getExtras().getInt(K.TITLE);
        if (status_title == 2) {
            title.setText("忘记密码");
            bindingTel.setText("安全验证");
            registerRegister.setText("确定");
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
