package com.android.yzd.ui.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.tools.DensityUtils;
import com.android.yzd.tools.U;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/2 0002.
 */
public class MyInfoActivity extends BaseActivity {


    PopupWindow popupWindow;

    View head;
    View sex;
    @BindView(R.id.alter_head)
    RelativeLayout alterHead;
    @BindView(R.id.info_nick)
    TextView infoNick;
    @BindView(R.id.alter_nick)
    RelativeLayout alterNick;
    @BindView(R.id.info_sex)
    TextView infoSex;
    @BindView(R.id.alter_sex)
    RelativeLayout alterSex;
    @BindView(R.id.info_tel)
    TextView infoTel;
    @BindView(R.id.alter_tel)
    RelativeLayout alterTel;
    @BindView(R.id.alter_password)
    RelativeLayout alterPassword;
    @BindView(R.id.info_save)
    Button infoSave;

    @Override
    public int getContentViewId() {
        return R.layout.activity_my_info;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {


        init();
    }

    private void init() {
        head = getLayoutInflater().inflate(R.layout.popup_choose_photo, null);
        sex = getLayoutInflater().inflate(R.layout.dialog_sex, null);

        popupWindow = new PopupWindow(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        //点击空白处时，隐藏掉pop窗口
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                U.setWindows(MyInfoActivity.this, 1f);
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.alter_head:
                U.setWindows(this, 0.5f);
                popupWindow.setContentView(head);
                popupWindow.setAnimationStyle(R.style.PopupAnimationStyle);
                popupWindow.showAtLocation(head, Gravity.BOTTOM, 0, DensityUtils.dp2px(this, 10));
                break;
            case R.id.privary:
                infoSex.setText("保密");
                popupWindow.dismiss();
                break;
            case R.id.man:
                infoSex.setText("男");
                popupWindow.dismiss();
                break;
            case R.id.woman:
                infoSex.setText("女");
                popupWindow.dismiss();
                break;
            case R.id.alter_sex:
                U.setWindows(this, 0.5f);
                popupWindow.setContentView(sex);
                popupWindow.setAnimationStyle(R.style.PopupAnimationStyle);
                popupWindow.showAtLocation(sex, Gravity.CENTER, 0, 0);
                break;
            case R.id.alter_tel:
                //绑定手机号
                intent = new Intent(this, AuthenticationActivity.class);
                startActivity(intent);
                break;
            case R.id.alter_password:
                //修改密码
                intent = new Intent(this, AlterPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.info_save:
                //保存
                break;
        }
    }

}
