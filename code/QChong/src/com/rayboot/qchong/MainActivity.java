package com.rayboot.qchong;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.InjectView;
import butterknife.Views;

import com.actionbarsherlock.app.SherlockActivity;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

public class MainActivity extends SherlockActivity {
	@InjectView(R.id.etCount)
	EditText etCount;
	@InjectView(R.id.etPhone)
	EditText etPhone;
	@InjectView(R.id.etQQ)
	EditText etQQ;
	@InjectView(R.id.etSMS)
	EditText etSMS;

	String curOrder = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Views.inject(this);
		doLogin();
	}

	private void doLogin() {
		RequestParams rp = new RequestParams();
		rp.put("mobileNumber", "");
		rp.put("cityId", "01");
		rp.put("clientType", "01");
		rp.put("v", "3.0.0.2");
		rp.put("marketCode", "2");
		rp.put("version", "3.0.0.2");
		rp.put("ua", "");
		rp.put("clientW", "480");
		rp.put("clientH", "800");
		rp.put("uuid", "");
		rp.put("appNumber", "100100-000000-000");
		rp.put("imsi",
				((TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE))
						.getSubscriberId());
		HttpUtil.get(HttpUtil.LOGIN, rp, null);
	}

	public void doSendSMS(View view) {
		curOrder = "";
		if (TextUtils.isEmpty(etPhone.getText().toString())) {
			Toast.makeText(this, "请先填写扣费手机号码", Toast.LENGTH_LONG).show();
			return;
		}
		RequestParams rp = new RequestParams();
		rp.put("mobileNumber", "");
		rp.put("cityId", "01");
		rp.put("clientType", "01");
		rp.put("v", "3.0.0.2");
		rp.put("marketCode", "2");
		rp.put("payNumber", etPhone.getText().toString());
		HttpUtil.get(HttpUtil.SENDMSM, rp, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
				Toast.makeText(MainActivity.this, "网络异常，请稍后再试。",
						Toast.LENGTH_LONG).show();
			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
				Gson gson = new Gson();
				SMSObj smsObj = gson.fromJson(arg0, SMSObj.class);
				if (smsObj != null && smsObj.code.equals("000000")) {
					if (!TextUtils.isEmpty(smsObj.orderId)) {
						curOrder = smsObj.orderId;
					}
				}
				Toast.makeText(MainActivity.this, smsObj.msg, Toast.LENGTH_LONG)
						.show();
			}

		});

	}

	public void doOrder(View view) {
		if (TextUtils.isEmpty(etQQ.getText().toString())) {
			Toast.makeText(this, "请先填写QQ账号", Toast.LENGTH_LONG).show();
			return;
		}
		if (TextUtils.isEmpty(etCount.getText().toString())) {
			Toast.makeText(this, "请填写充值数量", Toast.LENGTH_LONG).show();
			return;
		}
		if (TextUtils.isEmpty(etPhone.getText().toString())) {
			Toast.makeText(this, "请填写扣费手机号码", Toast.LENGTH_LONG).show();
			return;
		}
		if (TextUtils.isEmpty(etSMS.getText().toString())) {
			Toast.makeText(this, "请填写验证码", Toast.LENGTH_LONG).show();
			return;
		}

		RequestParams rp = new RequestParams();
		rp.put("mobileNumber", "");
		rp.put("cityId", "01");
		rp.put("clientType", "01");
		rp.put("v", "3.0.0.2");
		rp.put("marketCode", "2");
		rp.put("payNumber", etPhone.getText().toString());
		rp.put("orderId", curOrder);
		rp.put("smsCode", etSMS.getText().toString());
		rp.put("fee", Integer.valueOf(etCount.getText().toString()) * 100 + "");
		rp.put("spAccount", etQQ.getText().toString());
		HttpUtil.get(HttpUtil.LOGIN, rp, new AsyncHttpResponseHandler() {

			@Override
			public void onFailure(Throwable arg0, String arg1) {
				// TODO Auto-generated method stub
				super.onFailure(arg0, arg1);
				Toast.makeText(MainActivity.this, "网络异常，请稍后再试。",
						Toast.LENGTH_LONG).show();
			}

			@Override
			public void onSuccess(String arg0) {
				// TODO Auto-generated method stub
				super.onSuccess(arg0);
				Gson gson = new Gson();
				OrderObj orderObj = gson.fromJson(arg0, OrderObj.class);
				if (orderObj != null && orderObj.code.equals("000000")) {
					AlertDialog.Builder builder = new Builder(MainActivity.this);
					builder.setMessage(orderObj.msg);
					builder.setTitle("充值成功");
					builder.setPositiveButton("确认", new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
					builder.create().show();
				} else {
					Toast.makeText(MainActivity.this, orderObj.msg,
							Toast.LENGTH_LONG).show();
				}
			}

		});
	}
}
