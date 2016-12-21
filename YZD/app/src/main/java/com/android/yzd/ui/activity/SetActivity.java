package com.android.yzd.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.yzd.R;
import com.android.yzd.been.UserInfoEntity;
import com.android.yzd.tools.AppManager;
import com.android.yzd.tools.K;
import com.android.yzd.tools.SPUtils;
import com.android.yzd.ui.custom.BaseActivity;
import com.android.yzd.ui.view.BaseDialog;
import com.hyphenate.chat.EMClient;

/**
 * Created by Administrator on 2016/10/8 0008.
 */
public class SetActivity extends BaseActivity {

    BaseDialog dialog;

    @Override
    public int getContentViewId() {
        return R.layout.activity_set;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        dialog = new BaseDialog(this);
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.set_clearCache:
                break;
            case R.id.set_about:
                intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                break;
            case R.id.set_callTel:
                dialog.showDialog(R.layout.dialog_call);
                break;
            case R.id.cancel:
                dialog.dismiss();
                break;
            case R.id.sure:
                dialog.dismiss();
                break;
            case R.id.set_versions:
                break;
            case R.id.set_feedback:
                intent = new Intent(this, FeedBackActivity.class);
                startActivity(intent);
                break;
            case R.id.exit_login:
                AppManager.getAppManager().finishAllActivity();

                SPUtils.put(this, K.USERINFO, new UserInfoEntity());
                SPUtils.put(this, K.ISLOG, true);
                EMClient.getInstance().logout(true);
                intent = new Intent(this, LoginActivity.class);

                startActivity(intent);
                break;
        }
    }
}
