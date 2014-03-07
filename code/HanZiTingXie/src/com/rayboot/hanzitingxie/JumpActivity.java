package com.rayboot.hanzitingxie;

import java.io.IOException;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;

import com.rayboot.hanzitingxie.obj.WordData;
import com.rayboot.hanzitingxie.util.DataUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;
import com.wanpu.pay.PayConnect;

public class JumpActivity extends MyBaseActivity {
	private long exitTime = 0;

	FeedbackAgent agent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.AppTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jump);

		initUMeng();
		initWaps();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (DataUtil.getInfoFromShared(JumpActivity.this, "wenzibi") > 0) {
			AppConnect.getInstance(this).awardPoints(
					DataUtil.getInfoFromShared(JumpActivity.this, "wenzibi"),
					new UpdatePointsNotifier() {

						@Override
						public void getUpdatePointsFailed(String arg0) {
							// TODO Auto-generated method stub

						}

						@Override
						public void getUpdatePoints(String arg0, int arg1) {
							// TODO Auto-generated method stub
							DataUtil.setInfoToShared(JumpActivity.this,
									"wenzibi", 0);
							DataUtil.g_wenzibi = arg1;
						}
					});
		}
	}

	private void initWaps() {
		PayConnect
				.getInstance("d17140e585412eb323b5312a303b166a", "WAPS", this);
		AppConnect.getInstance(this);
		AppConnect.getInstance(this).initPopAd(this);

		AppConnect.getInstance(this).getPoints(updatePointsNotifier);
		AppConnect.getInstance(this).awardPoints(1, updatePointsNotifier);

		int count = DataUtil.getInfoFromShared(this, "wenzibi");
		if (count > 0) {
			AppConnect.getInstance(this).awardPoints(count,
					new UpdatePointsNotifier() {

						@Override
						public void getUpdatePointsFailed(String arg0) {
							// TODO Auto-generated method stub
							DataUtil.g_wenzibi += DataUtil.getInfoFromShared(
									JumpActivity.this, "wenzibi");
						}

						@Override
						public void getUpdatePoints(String arg0, int arg1) {
							// TODO Auto-generated method stub
							DataUtil.setInfoToShared(JumpActivity.this,
									"wenzibi", 0);
							DataUtil.g_wenzibi = arg1;
						}
					});
		}

	}

	private void initUMeng() {
		MobclickAgent.setDebugMode(false);
		agent = new FeedbackAgent(this);
		agent.sync();
		MobclickAgent.updateOnlineConfig(this);
		String value = MobclickAgent.getConfigParams(this, "isShowAD");
		if (TextUtils.isEmpty(value)) {
			value = "0";
		}
		DataUtil.setInfoToShared(this, "isShowAD", Integer.valueOf(value));
		UmengUpdateAgent.update(this);
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			@Override
			public void onUpdateReturned(int updateStatus,
					UpdateResponse updateInfo) {
				switch (updateStatus) {
				case 0: // has update
					UmengUpdateAgent.showUpdateDialog(JumpActivity.this,
							updateInfo);
					break;
				case 1: // has no update
					break;
				case 2: // none wifi
					break;
				case 3: // time out
					break;
				}
			}
		});
	}

	public void doSelect(View view) {
		switch (view.getId()) {
		case R.id.ivSetting:
			onSettings();
			break;
		case R.id.ivAbout:
			onMore();
			break;
		case R.id.llChuangGuan:
			onChuangGuan();
			break;
		case R.id.llLiShi:
			onData();
			break;
		case R.id.llWuJin:
			onWuJin();
			break;
		default:
			break;
		}
	}

	public void onChuangGuan() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("cur_play_type", MyApplication.PLAY_TYPE_CHUANGGUAN);
		startActivity(intent);
	}

	public void onWuJin() {
		if (WordData.getChuangGuanRandomData() != null) {
			Toast.makeText(this, "您还未通关，不能进入无尽模式", Toast.LENGTH_SHORT).show();
			return;
		}
		int wenzibi = DataUtil.getInfoFromShared(this, "wenzibi");
		if (wenzibi < 200) {
			Toast.makeText(this, "文字币大于200才能进入", Toast.LENGTH_SHORT).show();
			return;
		}
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("cur_play_type", MyApplication.PLAY_TYPE_WUJIN);
		startActivity(intent);
	}

	public void onData() {
		Intent intent = new Intent(this, RankActivity.class);
		startActivity(intent);
		// doInitData();
	}

	public void doInitData() {
		try {
			DataUtil.saveInitData(this);
			Toast.makeText(this, "finish", Toast.LENGTH_SHORT).show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void onSettings() {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

	public void onMore() {
		Intent intent = new Intent(this, MoreActivity.class);
		startActivity(intent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK
				&& event.getAction() == KeyEvent.ACTION_DOWN) {

			if ((System.currentTimeMillis() - exitTime) > 2000) {
				Toast.makeText(getApplicationContext(), "再按一次退出程序",
						Toast.LENGTH_SHORT).show();
				exitTime = System.currentTimeMillis();
			} else {
				finish();
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		PayConnect.getInstance(this).close();
		AppConnect.getInstance(this).close();
		super.onDestroy();
	}
}
