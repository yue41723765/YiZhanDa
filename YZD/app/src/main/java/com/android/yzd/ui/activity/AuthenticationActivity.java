package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class AuthenticationActivity extends BaseActivity {

    @BindView(R.id.authentication)
    TextView authentication;
    @BindView(R.id.binding_tel)
    TextView bindingTel;
    @BindView(R.id.authentication_tel)
    EditText authenticationTel;
    @BindView(R.id.authentication_code)
    EditText authenticationCode;
    @BindView(R.id.get_code)
    Button getCode;
    @BindView(R.id.sure)
    Button sure;

    int status = 1;//1---验证  2---绑定

    @Override
    public int getContentViewId() {
        return R.layout.activity_authentication;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {

    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.sure:
                if (status == 1) {
                    status = 2;
                    sure.setText("绑定");
                    authenticationTel.setText("");
                    authenticationCode.setText("");
                    authentication.setTextColor(getResources().getColor(R.color.black_30));
                    bindingTel.setTextColor(getResources().getColor(R.color.red_30));
                }
                if (status == 2) {

                }
                break;
        }
    }

}
