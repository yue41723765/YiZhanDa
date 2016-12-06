package com.android.yzd.ui.activity;

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
import com.android.yzd.http.ProgressSubscriber;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.StatusBarUtil;
import com.android.yzd.tools.T;
import com.android.yzd.ui.custom.BaseActivity;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

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
    int status_title;//1--------注册 2--------修改密码
    String account;//手机号码

    SubscriberOnNextListener regisiterLinstener;
    ProgressSubscriber progressSubscriber;
    Map<String, String> param = new HashMap<>();


    @Override
    public int getContentViewId() {
        return R.layout.activity_register_2;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        AppManager.getAppManager().addActivity(this);

        status_title = getIntent().getExtras().getInt(K.TITLE);
        account = getIntent().getExtras().getString(K.DATA);
        if (status_title == 2) {
            title.setText("忘记密码");
            bindingTel.setText("安全验证");
            registerRegister.setText("确定");
        }
        //注册开启定位
        regisiterLinstener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                if (status_title == 1) {
                    Gson gson = new Gson();
                    UserInfoEntity infoEntity = gson.fromJson(gson.toJson(o), UserInfoEntity.class);
                    SPUtils.put(Register2Activity.this, K.USERINFO, infoEntity);
                    T.show(Register2Activity.this, "注册成功!", Toast.LENGTH_SHORT);
                } else {
                    T.show(Register2Activity.this, "重置密码成功", Toast.LENGTH_SHORT);
                }
                finish();
            }
        };
    }

    public void setProgressSubscriber(SubscriberOnNextListener listener) {
        progressSubscriber = new ProgressSubscriber(listener, this, false);
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.register_register:
                String pas1 = registerPassword.getText().toString();
                String pas2 = registerAgainPassword.getText().toString();
                if (pas1.equals("") | pas2.equals("")) {
                    T.show(this, "密码不能为空！", Toast.LENGTH_SHORT);
                    return;
                }
                if (!pas1.equals(pas2)) {
                    T.show(this, "密码不一致，请确认密码！", Toast.LENGTH_SHORT);
                    return;
                }


                setProgressSubscriber(regisiterLinstener);
                param.clear();
                param.put("account", account);
                param.put("password", pas2);

                if (status_title == 2) {
                    HttpMethods.getInstance(this).forgetPassword(progressSubscriber, param);
                } else {
                    HttpMethods.getInstance(this).register(progressSubscriber, param);
                }
                break;
        }
    }
}
