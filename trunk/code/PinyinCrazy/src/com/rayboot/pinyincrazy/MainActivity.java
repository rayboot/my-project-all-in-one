package com.rayboot.pinyincrazy;

import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.widget.ListView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.haarman.listviewanimations.swinginadapters.AnimationAdapter;
import com.haarman.listviewanimations.swinginadapters.prepared.SwingLeftInAnimationAdapter;
import com.rayboot.pinyincrazy.adapter.MainAdapter;
import com.rayboot.pinyincrazy.obj.MainDataObj;

public class MainActivity extends MyBaseActivity {
	@InjectView(R.id.lvMain)
	ListView lvMain;
	MainAdapter<MainDataObj> adapter;

	List<MainDataObj> mainDatas = new ArrayList<MainDataObj>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Holo_Theme_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.inject(this);

		mainDatas.add(new MainDataObj(MainDataObj.MAIN_GAME, "闯关"));
		mainDatas.add(new MainDataObj(MainDataObj.MAIN_RANK, "数据"));
		mainDatas.add(new MainDataObj(MainDataObj.MAIN_SETTING, "设置"));
		mainDatas.add(new MainDataObj(MainDataObj.MAIN_MORE, "更多"));

		adapter = new MainAdapter<MainDataObj>(this, mainDatas);
		AnimationAdapter animAdapter = new SwingLeftInAnimationAdapter(adapter);
		animAdapter.setAbsListView(lvMain);
		lvMain.setAdapter(animAdapter);
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
