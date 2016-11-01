package com.android.yzd.ui.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.yzd.R;
import com.android.yzd.tools.K;
import com.android.yzd.ui.custom.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/10/10 0010.
 */
public class EditAddressActivity extends BaseActivity {
    @BindView(R.id.titleBar_right_text)
    TextView title_right;
    @BindView(R.id.edit_name)
    EditText editName;
    @BindView(R.id.edit_tel)
    EditText editTel;
    @BindView(R.id.edit_address)
    EditText editAddress;

    @Override
    public int getContentViewId() {
        return R.layout.activity_edit_address;
    }

    @Override
    protected void initAllMembersView(Bundle savedInstanceState) {
        int status = getIntent().getExtras().getInt(K.STATUS);
        switch (status) {
            case 1:
                break;
            case 2:
                title_right.setVisibility(View.GONE);
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
