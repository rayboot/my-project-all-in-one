package com.rayboot.pinyincrazy;

import java.util.ArrayList;
import java.util.List;

import org.holoeverywhere.widget.ListView;

import android.os.Bundle;
import android.view.View;
import butterknife.ButterKnife;
import butterknife.InjectView;

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
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		mainDatas.add(new MainDataObj(MainDataObj.MAIN_GAME, "闯关"));
		mainDatas.add(new MainDataObj(MainDataObj.MAIN_RANK, "数据"));
		mainDatas.add(new MainDataObj(MainDataObj.MAIN_SETTING, "设置"));
		mainDatas.add(new MainDataObj(MainDataObj.MAIN_MORE, "更多"));

		adapter = new MainAdapter<MainDataObj>(this, mainDatas);
		lvMain.setAdapter(adapter);
	}

	public void mainItemClick(View view) {
		
	}
}
