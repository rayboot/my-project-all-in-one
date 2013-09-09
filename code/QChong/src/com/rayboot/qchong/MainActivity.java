package com.rayboot.qchong;

import java.text.DecimalFormat;
import java.util.Timer;
import java.util.TimerTask;

import android.R.integer;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.InjectView;
import butterknife.Views;
import cn.waps.AdView;
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class MainActivity extends SherlockActivity {
	final int MORE_GET_POINT = 1;
	final int MORE_FEEBACK = 2;
	final int MORE_ABOUT = 3;
	final int MORE_SHARE = 4;
	@InjectView(R.id.etCount)
	EditText etCount;
	@InjectView(R.id.etPhone)
	EditText etPhone;
	@InjectView(R.id.etQQ)
	EditText etQQ;
	@InjectView(R.id.etSMS)
	EditText etSMS;
	@InjectView(R.id.btnSMS)
	Button btnSMS;
	@InjectView(R.id.tvPrice)
	TextView tvPrice;
	@InjectView(R.id.AdLinearLayout)
	LinearLayout AdLinearLayout;

	String curOrder = "";
	FeedbackAgent agent;
	DecimalFormat df = new DecimalFormat("0.00");

	int app_count = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Views.inject(this);
		doLogin();
		initUMeng();
		initBaiduPush();
		etCount.addTextChangedListener(textWatcher);

		etQQ.setText(Util.getInfoFromShared(this, "qq"));
		etPhone.setText(Util.getInfoFromShared(this, "phone"));
		initWAPS();
	}
	
	private void initWAPS(){
		AppConnect.getInstance(this);
		AppConnect.getInstance(this).getPoints(updatePointsNotifier);
		// 初始化自定义广告数据
		AppConnect.getInstance(this).initAdInfo();
		// 初始化插屏广告数据
		AppConnect.getInstance(this).initPopAd(this);
		AppConnect.getInstance(this).awardPoints(10, updatePointsNotifier);
		// 禁用错误报告
		AppConnect.getInstance(this).setCrashReport(false);
		new AdView(this, AdLinearLayout).DisplayAd();
	}

	private UpdatePointsNotifier updatePointsNotifier = new UpdatePointsNotifier() {

		@Override
		public void getUpdatePointsFailed(String arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void getUpdatePoints(String arg0, int arg1) {
			// TODO Auto-generated method stub
			app_count = arg1;
		}
	};

	private void initBaiduPush() {

		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY,
				Util.getMetaValue(MainActivity.this, "api_key"));
	}

	private TextWatcher textWatcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable arg0) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onTextChanged(CharSequence arg0, int arg1, int arg2,
				int arg3) {
			// TODO Auto-generated method stub
			if (TextUtils.isEmpty(arg0)) {
				tvPrice.setText("价格总计：0元");
			} else {
				tvPrice.setText("价格总计："
						+ df.format(Integer.parseInt(arg0.toString()) * 0.92)
						+ "元");
			}

		}

	};

	private void initUMeng() {
		// 友盟意见反馈检索
		agent = new FeedbackAgent(this);
		agent.sync();
		// 友盟检测更新
		UmengUpdateAgent.update(this);
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			@Override
			public void onUpdateReturned(int updateStatus,
					UpdateResponse updateInfo) {
				switch (updateStatus) {
				case 0: // has update
					UmengUpdateAgent.showUpdateDialog(MainActivity.this,
							updateInfo);
					break;
				}
			}
		});
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
		if (app_count < 25) {
			AlertDialog.Builder builder = new Builder(MainActivity.this);
			builder.setMessage("非常抱歉，您的积分不足，尝试如下方法获取积分。");
			builder.setTitle("提示");
			builder.setPositiveButton("软件推荐", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					AppConnect.getInstance(MainActivity.this).showAppOffers(
							MainActivity.this);
				}
			});
			builder.setNeutralButton("团购推荐", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					AppConnect.getInstance(MainActivity.this).showTuanOffers(
							MainActivity.this);
				}
			});
			builder.setNegativeButton("取消", new OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			builder.create().show();
			return;
		}

		if (AppConnect.getInstance(this).hasPopAd(this)) {
			AppConnect.getInstance(this).showPopAd(this);
		}

		curOrder = "";
		if (TextUtils.isEmpty(etPhone.getText().toString())) {
			Toast.makeText(this, "请先填写扣费手机号码", Toast.LENGTH_LONG).show();
			return;
		}

		AppConnect.getInstance(this).spendPoints(25, updatePointsNotifier);
		
		Util.setInfoToShared(this, "phone", etPhone.getText().toString());
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
						btnSMS.setEnabled(false);
						new Timer().schedule(new TimerTask() {
							public void run() {
								Message message = new Message();
								message.what = 1;
								handler.sendMessage(message);
							}
						}, 30000);
					}
				}
				if (smsObj.msg.contains("安徽")) {
					smsObj.msg = "该手机号码不支持支付费用。";
				}
				Toast.makeText(MainActivity.this, smsObj.msg, Toast.LENGTH_LONG)
						.show();

			}

		});

	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				btnSMS.setEnabled(true);
				break;
			}
			super.handleMessage(msg); // 这句话的意思是处理消息队列中的消息
		}
	};

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

		Util.setInfoToShared(this, "phone", etPhone.getText().toString());
		Util.setInfoToShared(this, "qq", etQQ.getText().toString());
		setAllInputEnable(false);

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
		HttpUtil.get(HttpUtil.DOORDER, rp, new AsyncHttpResponseHandler() {

			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				super.onFinish();
				setAllInputEnable(true);
			}

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
				// arg0 =
				// "{\"actualFee\": \"92\",\"description\": \"充值成功!\",\"code\": \"000000\",\"msg\": \"充值成功\"}";
				Gson gson = new Gson();
				OrderObj orderObj = gson.fromJson(arg0, OrderObj.class);
				if (orderObj != null && orderObj.code.equals("000000")) {
					AlertDialog.Builder builder = new Builder(MainActivity.this);
					builder.setMessage("本次交易共购买Q币"
							+ etCount.getText().toString() + "个\n总计扣费："
							+ ((double) orderObj.actualFee / 100) + "元");
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

	private void setAllInputEnable(boolean b) {
		etCount.setEnabled(b);
		etQQ.setEnabled(b);
		etPhone.setEnabled(b);
		etSMS.setEnabled(b);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu sub = menu.addSubMenu("Setting");
		sub.add(0, MORE_GET_POINT, 0, "获取积分");
		sub.add(0, MORE_FEEBACK, 0, "意见反馈");
		sub.add(0, MORE_SHARE, 0, "分享");
		sub.add(0, MORE_ABOUT, 0, "关于");
		MenuItem subMenu1Item = sub.getItem();
		subMenu1Item.setIcon(R.drawable.align_just_icon);
		subMenu1Item.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS
				| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
		case 0:
			return false;
		case MORE_GET_POINT:
			AppConnect.getInstance(this).showOffers(this);
			break;
		case MORE_ABOUT:
			startActivity(new Intent(this, AboutActivity.class));
			overridePendingTransition(R.anim.base_slide_right_in,
					R.anim.stay_anim);
			break;
		case MORE_FEEBACK:
			agent.startFeedbackActivity();
			break;
		case MORE_SHARE:
			Util.shareSomethingText(MainActivity.this, "分享",
					"我使用  #Q币九折起#  我找到的全网最低买Q币，知道你喜欢，特意转给你。");
			break;
		default:
			break;
		}
		return true;
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		AppConnect.getInstance(this).close();
	}
}
