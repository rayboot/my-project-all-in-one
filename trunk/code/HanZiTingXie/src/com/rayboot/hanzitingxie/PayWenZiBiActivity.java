package com.rayboot.hanzitingxie;

import org.holoeverywhere.widget.Toast;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;

import com.rayboot.hanzitingxie.util.DataUtil;
import com.umeng.analytics.MobclickAgent;
import com.wanpu.pay.PayConnect;
import com.wanpu.pay.PayResultListener;

public class PayWenZiBiActivity extends MyBaseActivity {
	TextView tvBi;
	// 应用或游戏商自定义的支付订单(每条支付订单数据不可相同)
	String orderId = "";
	// 用户标识
	String userId = "";
	// 支付商品名称
	String goodsName = "文字币";
	// 支付金额
	float price = 0.0f;
	// 支付时间
	String time = "";
	// 支付描述
	String goodsDesc = "";
	// 应用或游戏商服务器端回调接口（无服务器可不填写）
	String notifyUrl = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Holo_Theme_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pay_wen_zi_bi);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		userId = PayConnect.getInstance(PayWenZiBiActivity.this).getDeviceId(
				PayWenZiBiActivity.this);
		tvBi = (TextView) findViewById(R.id.tvBi);
		changeWenZiBi(0);
	}

	public void doPay(View view) {
		String count = (String) view.getTag();
		if (TextUtils.isEmpty(count)) {
			Toast.makeText(getApplicationContext(), "交易金额异常", Toast.LENGTH_LONG)
					.show();
			return;
		}
		if (count.equals("1")) {
			goodsDesc = "购买300个文字币";
		} else if (count.equals("2")) {
			goodsDesc = "购买700个文字币";
		} else if (count.equals("3")) {
			goodsDesc = "购买1100个文字币";
		} else if (count.equals("4")) {
			goodsDesc = "购买1500个文字币";
		} else if (count.equals("5")) {
			goodsDesc = "购买2500个文字币";
		}
		try {
			// // 游戏商自定义支付订单号，保证该订单号的唯一性，建议在执行支付操作时才进行该订单号的生成
			orderId = System.currentTimeMillis() + "";

			if (!"".equals(count)) {
				price = Float.valueOf(count);
			} else {
				price = 0.0f;
			}

			PayConnect.getInstance(PayWenZiBiActivity.this).pay(
					PayWenZiBiActivity.this, orderId, userId, price, goodsName,
					goodsDesc, notifyUrl, new MyPayResultListener());

		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自定义Listener实现PaySuccessListener，用于监听支付成功
	 * 
	 * @author Administrator
	 * 
	 */
	private class MyPayResultListener implements PayResultListener {

		@Override
		public void onPayFinish(Context payViewContext, String orderId,
				int resultCode, String resultString, int payType, float amount,
				String goodsName) {
			// 可根据resultCode自行判断
			if (resultCode == 0) {
				Toast.makeText(getApplicationContext(),
						resultString + "：" + amount + "元", Toast.LENGTH_LONG)
						.show();
				// 支付成功时关闭当前支付界面
				PayConnect.getInstance(PayWenZiBiActivity.this).closePayView(
						payViewContext);

				// TODO 在客户端处理支付成功的操作

				// 未指定notifyUrl的情况下，交易成功后，必须发送回执
				PayConnect.getInstance(PayWenZiBiActivity.this).confirm(
						orderId, payType);

				changeWenZiBi(50);
				if (amount == 1.0) {
					MobclickAgent.onEvent(PayWenZiBiActivity.this, "1");
					changeWenZiBi(300);
				} else if (amount == 2.0) {
					MobclickAgent.onEvent(PayWenZiBiActivity.this, "2");
					changeWenZiBi(700);
				} else if (amount == 3.0) {
					MobclickAgent.onEvent(PayWenZiBiActivity.this, "3");
					changeWenZiBi(1100);
				} else if (amount == 4.0) {
					MobclickAgent.onEvent(PayWenZiBiActivity.this, "4");
					changeWenZiBi(1500);
				} else if (amount == 5.0) {
					MobclickAgent.onEvent(PayWenZiBiActivity.this, "5");
					changeWenZiBi(2500);
				}

			} else {
				Toast.makeText(getApplicationContext(), resultString,
						Toast.LENGTH_LONG).show();
			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// This uses the imported MenuItem from ActionBarSherlock
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return true;
	}

	private void changeWenZiBi(int cnt) {
		int count = DataUtil.getInfoFromShared(this, "wenzibi");
		DataUtil.setInfoToShared(this, "wenzibi", count + cnt);
		if (cnt > 0) {
			AppConnect.getInstance(this).awardPoints(cnt,
					new UpdatePointsNotifier() {

						@Override
						public void getUpdatePointsFailed(String arg0) {
							// TODO Auto-generated method stub
							DataUtil.g_wenzibi += DataUtil.getInfoFromShared(
									PayWenZiBiActivity.this, "wenzibi");
							changeUIWenZiBiHandler.sendEmptyMessage(1);
						}

						@Override
						public void getUpdatePoints(String arg0, int arg1) {
							// TODO Auto-generated method stub
							DataUtil.g_wenzibi = arg1;
							DataUtil.setInfoToShared(PayWenZiBiActivity.this,
									"wenzibi", 0);
							changeUIWenZiBiHandler.sendEmptyMessage(1);
						}
					});
		} else if (cnt < 0) {
			AppConnect.getInstance(this).spendPoints(Math.abs(cnt),
					updatePointsNotifier);
		}
		if (DataUtil.g_wenzibi == -100) {
			tvBi.setText("文字币：未知");
		} else {
			tvBi.setText("文字币：" + (DataUtil.g_wenzibi + cnt));
		}
	}

	private Handler changeUIWenZiBiHandler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg.what == 1) {
				tvBi.setText("文字币：" + DataUtil.g_wenzibi);
			}
		}
	};
}
