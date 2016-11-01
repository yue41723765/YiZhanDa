package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.tools.K;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class RegisterActivity extends BaseActivity {
    @BindView(R.id.titleBar_title)
    TextView title;
    @BindView(R.id.binding_tel)
    TextView bindingTel;
    @BindView(R.id.setPassword)
    TextView setPassword;
    @BindView(R.id.register_tel)
    EditText registerTel;
    @BindView(R.id.register_code)
    EditText registerCode;
    @BindView(R.id.register_check)
    CheckBox registerCheck;
    @BindView(R.id.protocol_deal)
    TextView protocolDeal;

    int status_title;
    @BindView(R.id.get_code)
    Button getCode;
    @BindView(R.id.next)
    Button next;
    @BindView(R.id.bottom)
    LinearLayout bottom;

    @Override
    public int getContentViewId() {
        return R.layout.activity_register;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        status_title = getIntent().getExtras().getInt(K.TITLE);
        if (status_title == 2) {
            title.setText("忘记密码");
            bindingTel.setText("安全验证");
            next.setText("验证");
            bottom.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.next:
                intent = new Intent(this, Register2Activity.class);
                intent.putExtra(K.TITLE, status_title);
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
