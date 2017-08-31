package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.yzd.R;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.http.HttpMethods;
import com.android.yzd.http.SubscriberOnNextListener;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.L;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.tools.StatusBarUtil;
import com.android.yzd.tools.T;
import com.android.yzd.ui.custom.BaseActivity;
import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

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
    @BindView(R.id.login_register)
    Button loginRegister;
    @BindView(R.id.forget_password)
    TextView forgetPassword;
    @BindView(R.id.remember_password)
    CheckBox rememberPassword;
    @BindView(R.id.login_toast)
    TextView toastText;
    @BindView(R.id.login_qq)
    Button login_qq;
    @BindView(R.id.login_wx)
    Button login_wx;
    String tel;
    //qq
    private Tencent mTencent; //qq主操作对象
    private BaseUiListener mIUiListener; //授权登录监听器
    private IUiListener userInfoListener; //获取用户信息监听器
    private String scope; //获取信息的范围参数
    private UserInfo mUserInfo; //qq用户信息

    private static final String TAG = "LoginActivity";
    private static final String QQ_APP_ID = "1105697773";//官方获取的APPID
    //微信API
    private IWXAPI api;
    //微信ID
    private static final String APP_ID="wx5c5be12f9933f83d";
    @Override
    public int getContentViewId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
      //        同意并阅读《一站达注册协议》
        AppManager.getAppManager().addActivity(this);
        loginTel.setText((String) SPUtils.get(this, K.USERNAME, ""));

        //微信注册在APP内
        api = WXAPIFactory.createWXAPI(LoginActivity.this,null);
        api.registerApp(APP_ID);

        //qq 传入参数APPID和全局Context上下文
        mTencent = Tencent.createInstance(QQ_APP_ID,LoginActivity.this.getApplicationContext());
    }


    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.login_login:

                tel = loginTel.getText().toString();
                String pass = loginPassword.getText().toString();
                if (tel.equals("")) {
                    //T.show(this, "手机号码出错", Toast.LENGTH_SHORT);
                    toastText.setText("手机号码出错");
                    toastText.setVisibility(View.VISIBLE);
                    return;
                }
                if (pass.equals("")) {
                    //T.show(this, "密码出错", Toast.LENGTH_SHORT);
                    toastText.setText("密码出错");
                    toastText.setVisibility(View.VISIBLE);
                    return;
                }
                Login(tel, pass);

                return;
            case R.id.forget_password:
                intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(K.TITLE, 2);
                startActivity(intent);
                break;
            case R.id.login_register:
                intent = new Intent(this, RegisterActivity.class);
                intent.putExtra(K.TITLE, 1);
                startActivity(intent);
                break;
            case R.id.login_qq:
                /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
                 官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
                 第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
                mIUiListener = new BaseUiListener();
                //all表示获取所有权限
                mTencent.login(LoginActivity.this,"all", mIUiListener);
                break;
            case R.id.login_wx:
                SendAuth.Req req=new SendAuth.Req();
                req.scope = "snsapi_userinfo";
                req.state = "wechat_login_lmy";
                api.sendReq(req);
                break;
        }
    }

    private void Login(final String tel, final String pass) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                UserInfoEntity userInfo = gson.fromJson(gson.toJson(o), UserInfoEntity.class);
                SPUtils.put(LoginActivity.this, K.USERINFO, userInfo);
                loginEC(userInfo.getEasemob_account(), userInfo.getEasemob_password());
            }
        };
        setProgressSubscriber(onNextListener);
        params.clear();
        params.put("account", tel);
        params.put("password", pass);
        HttpMethods.getInstance(this).login(progressSubscriber, params);
    }

    private void loginEC(String ecUser, String ecPass) {
        EMClient.getInstance().login(ecUser, ecPass, new EMCallBack() {//回调
            @Override
            public void onSuccess() {
                EMClient.getInstance().groupManager().loadAllGroups();
                EMClient.getInstance().chatManager().loadAllConversations();

                SPUtils.put(LoginActivity.this, K.ISLOG, false);
                SPUtils.put(LoginActivity.this, K.USERNAME, tel);

                intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                handler.sendEmptyMessageAtTime(-1, 0);
            }

            @Override
            public void onProgress(int progress, String status) {

            }

            @Override
            public void onError(int code, String message) {
                //L.i("----onError----");
                handler.sendEmptyMessageAtTime(code, 0);
            }
        });
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case -1:
                    //Toast.makeText(LoginActivity.this, "登陆成功!", Toast.LENGTH_SHORT).show();
                    toastText.setText("登录成功");
                    toastText.setVisibility(View.VISIBLE);
                    break;
                case 200:
                    //Toast.makeText(LoginActivity.this, "用户已登录!", Toast.LENGTH_SHORT).show();
                    toastText.setText("用户已登录");
                    toastText.setVisibility(View.VISIBLE);
                    break;
                case 202:
                    //Toast.makeText(LoginActivity.this, "用户id或密码错误!", Toast.LENGTH_SHORT).show();
                    toastText.setText("账号/密码不正确");
                    toastText.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            StatusBarUtil.transparencyBar(this);
        }
        ButterKnife.bind(this);
    }
    

    public void buttonLogin(View v){
        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        mIUiListener = new BaseUiListener();
        //all表示获取所有权限
        mTencent.login(LoginActivity.this,"all", mIUiListener);
    }

    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener{

        @Override
        public void onComplete(Object response) {
            Toast.makeText(LoginActivity.this, "授权成功", Toast.LENGTH_SHORT).show();
            L.e(TAG, "response:" + response);
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getApplicationContext(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        Log.e(TAG,"登录成功"+response.toString());
                    }

                    @Override
                    public void onError(UiError uiError) {
                        Log.e(TAG,"登录失败"+uiError.toString());
                    }

                    @Override
                    public void onCancel() {
                        Log.e(TAG,"登录取消");

                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Toast.makeText(LoginActivity.this, "授权失败", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onCancel() {
            Toast.makeText(LoginActivity.this, "授权取消", Toast.LENGTH_SHORT).show();

        }
    }
    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
