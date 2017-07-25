package com.android.yzd;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.yzd.ui.activity.MainActivity;
import com.android.yzd.ui.activity.PayResultActivity;
import com.android.yzd.ui.custom.BaseActivity;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import butterknife.BindView;

public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {
	@BindView(R.id.title_tools)Toolbar title_tools;
	private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";
	public static final String APP_ID = "wx5c5be12f9933f83d";
    private IWXAPI api;

	@Override
	public int getContentViewId() {
		return R.layout.activity_pay_result;
	}

	@Override
	protected void initAllMembersView(Bundle savedInstanceState) {
		title_tools.setNavigationIcon(R.mipmap.arrow_left_white);
		title_tools.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(WXPayEntryActivity.this,MainActivity.class);
				startActivity(intent);
				finish();
			}
		});

		api = WXAPIFactory.createWXAPI(this,APP_ID);

		api.handleIntent(getIntent(), this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		setIntent(intent);
        api.handleIntent(intent, this);
	}

	@Override
	public void onReq(BaseReq req) {
	}

	@Override
	public void onResp(BaseResp resp) {
		Toast.makeText(WXPayEntryActivity.this, resp.errCode, Toast.LENGTH_SHORT).show();
		if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("微信支付结果" + String.valueOf(resp.errCode));
			builder.show();
		}
	}
}