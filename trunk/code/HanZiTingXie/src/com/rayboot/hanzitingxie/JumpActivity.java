package com.rayboot.hanzitingxie;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.Toast;

import android.R.integer;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;

import com.activeandroid.util.SQLiteUtils;
import com.rayboot.hanzitingxie.obj.SourceData;
import com.rayboot.hanzitingxie.util.DataUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class JumpActivity extends MyBaseActivity {
	private long exitTime = 0;

	ListView lvMode;
	BaseAdapter adapter;
	FeedbackAgent agent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Holo_Theme_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jump);
		getSupportActionBar().setTitle(
				getResources().getString(R.string.app_name));

		lvMode = (ListView) findViewById(R.id.lvMode);
		lvMode.setOnItemClickListener(onItemClickListener);

		int count = DataUtil.getInfoFromShared(this, "wenzibi");
		if (count == 0) {
			DataUtil.setInfoToShared(this, "wenzibi", 20);
		} else {
			DataUtil.setInfoToShared(this, "wenzibi", count + 2);
		}
		loadMode();
		initUMeng();
	}

	private void initUMeng() {
		MobclickAgent.setDebugMode(false);
		agent = new FeedbackAgent(this);
		agent.sync();
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

	private void loadMode() {
		List<String> datas = new ArrayList<String>();
		datas.add("闯关");
		datas.add("无尽");
		datas.add("数据");
		datas.add("设置");
		datas.add("更多");
		adapter = new ModeAdapter<String>(this, datas);
		lvMode.setAdapter(adapter);
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			switch (arg2) {
			case 0:
				onChuangGuan();
				break;
			case 1:
				onWuJin();
				break;
			case 2:
				onData();
				break;
			case 3:
				onSettings();
				break;
			case 4:
				onMore();
				break;
			default:
				break;
			}

		}
	};

	public void onChuangGuan() {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("cur_play_type", MyApplication.PLAY_TYPE_CHUANGGUAN);
		startActivity(intent);
	}

	public void onWuJin() {
		if (SourceData.getChuangGuanRandomData() != null) {
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
//		Intent intent = new Intent(this, RankActivity.class);
//		startActivity(intent);
		 doInitData();
	}
	
	public void loadData(){
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

}
