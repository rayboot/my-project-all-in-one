package com.rayboot.hanzitingxie;

import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.ThemeManager;
import org.holoeverywhere.widget.ListView;
import org.holoeverywhere.widget.Toast;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;

import com.rayboot.hanzitingxie.util.DataUtil;

public class JumpActivity extends MyBaseActivity {
	private long exitTime = 0;

	ListView lvMode;
	BaseAdapter adapter;
	boolean isLoading = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jump);

		lvMode = (ListView) findViewById(R.id.lvMode);
		lvMode.setOnItemClickListener(onItemClickListener);

		int count = DataUtil.getInfoFromShared(this, "wenzibi");
		if (count == 0) {
			DataUtil.setInfoToShared(this, "wenzibi", 20);
		} else {
			DataUtil.setInfoToShared(this, "wenzibi", count + 2);
		}
		loadMode();
		if (!isLoading) {
			this.startActivity(new Intent(this, LoadingActivity.class));
			return;
		}
	}

	private void loadMode() {
		List<String> datas = new ArrayList<String>();
		datas.add("闯关");
		datas.add("无尽");
		datas.add("数据");
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
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("cur_play_type", MyApplication.PLAY_TYPE_WUJIN);
		startActivity(intent);
	}

	public void onData() {
		Intent intent = new Intent(this, RankActivity.class);
		startActivity(intent);

	}

	public void onSettings() {
		Intent intent = new Intent(this, SettingsActivity.class);
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
				System.exit(0);
			}

			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

}
