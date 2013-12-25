package com.rayboot.pinyincrazy;

import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.widget.ListView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import com.iflytek.speech.SpeechUtility;
import com.rayboot.pinyincrazy.adapter.MainAdapter;
import com.rayboot.pinyincrazy.obj.MainDataObj;
import com.rayboot.pinyincrazy.utils.DataUtil;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class MainActivity extends MyBaseActivity {
	@InjectView(R.id.lvMain)
	ListView lvMain;
	MainAdapter<MainDataObj> adapter;
	FeedbackAgent agent;

	List<MainDataObj> mainDatas = new ArrayList<MainDataObj>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Holo_Theme_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);

		mainDatas.add(new MainDataObj(MainDataObj.MAIN_GAME, "闯关"));
		mainDatas.add(new MainDataObj(MainDataObj.MAIN_RANK, "数据"));
		mainDatas.add(new MainDataObj(MainDataObj.MAIN_MORE, "更多"));

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
			MainActivity.this.startActivity(intent);
			break;
		case MainDataObj.MAIN_RANK:
			intent = new Intent(MainActivity.this, RankActivity.class);
			MainActivity.this.startActivity(intent);
			break;
		case MainDataObj.MAIN_SETTING:
			intent = new Intent(MainActivity.this, SettingActivity.class);
			MainActivity.this.startActivity(intent);
			break;
		case MainDataObj.MAIN_MORE:
			intent = new Intent(MainActivity.this, MoreActivity.class);
			MainActivity.this.startActivity(intent);
			break;
		}
	}
}
