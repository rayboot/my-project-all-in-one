package com.rayboot.pinyincrazy;

import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import com.iflytek.speech.SpeechUtility;
import com.rayboot.pinyincrazy.adapter.MainAdapter;
import com.rayboot.pinyincrazy.obj.MainDataObj;
import com.rayboot.pinyincrazy.obj.PinyinDataObj;
import com.rayboot.pinyincrazy.utils.DataUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class MainActivity extends MyBaseActivity {
	@InjectView(R.id.lvMain) ListView lvMain;
	MainAdapter<MainDataObj> adapter;
	FeedbackAgent agent;

	List<MainDataObj> mainDatas = new ArrayList<MainDataObj>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		ButterKnife.inject(this);

		if (PinyinDataObj.getChuangGuanRandomData() == null) {
			mainDatas.add(new MainDataObj(MainDataObj.MAIN_WUJIN));
		}else {
			mainDatas.add(new MainDataObj(MainDataObj.MAIN_GAME));
		}
		mainDatas.add(new MainDataObj(MainDataObj.MAIN_RANK));
		mainDatas.add(new MainDataObj(MainDataObj.MAIN_MORE));

		adapter = new MainAdapter<MainDataObj>(this, mainDatas);
		AnimationAdapter animAdapter = new SwingLeftInAnimationAdapter(adapter);
		animAdapter.setAbsListView(lvMain);
		lvMain.setAdapter(animAdapter);
		SpeechUtility.getUtility(this).setAppid(
				getString(R.string.iflytek_app_id));
		initUMeng();
		DataUtil.changeCoin(this, 1);
		
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
					UmengUpdateAgent.showUpdateDialog(MainActivity.this,
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

	public void mainItemClick(View view) {
		int index = (Integer) view.getTag(R.string.tag_1);
		Intent intent = null;
		switch (index) {
		case MainDataObj.MAIN_GAME:
			intent = new Intent(MainActivity.this, GameActivity.class);
			intent.putExtra("is_wujin", 0);
			MainActivity.this.startActivity(intent);
			break;
		case MainDataObj.MAIN_WUJIN:
			intent = new Intent(MainActivity.this, GameActivity.class);
			intent.putExtra("is_wujin", 1);
			MainActivity.this.startActivity(intent);
			break;
		case MainDataObj.MAIN_RANK:
			intent = new Intent(MainActivity.this, RankActivity.class);
			MainActivity.this.startActivity(intent);
			break;
		case MainDataObj.MAIN_MORE:
			intent = new Intent(MainActivity.this, MoreActivity.class);
			MainActivity.this.startActivity(intent);
			break;
		}
	}
	
	
	private long exitTime = 0;
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
