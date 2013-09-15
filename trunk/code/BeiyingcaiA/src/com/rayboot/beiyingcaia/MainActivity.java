package com.rayboot.beiyingcaia;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import cn.waps.AdView;
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.activeandroid.ActiveAndroid;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rayboot.beiyingcaia.obj.AObj;
import com.rayboot.beiyingcaia.util.DataUtil;
import com.rayboot.beiyingcaia.util.Util;
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

	GridView gvContent;
	MyBaseAdapter<AObj> adapter;
	ListView lvIndex;
	MyBaseAdapter<String> adapterIndex;
	List<AObj> allObjs = null;
	List<String> indexData = new ArrayList<String>();
	FeedbackAgent agent;
	int app_count = 0;
	LinearLayout AdLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		allObjs = AObj.getAllData();

		gvContent = (GridView) findViewById(R.id.gvContent);
		adapter = new ContentAdapter<AObj>(this, allObjs);
		gvContent.setAdapter(adapter);

		gvContent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Gson gson = new GsonBuilder()
						.excludeFieldsWithoutExposeAnnotation().create();
				Intent intent = new Intent(MainActivity.this,
						DetailActivity.class);
				intent.putExtra("content_detail",
						gson.toJson(((AObj) adapter.getItem(arg2))));
				MainActivity.this.startActivity(intent);
			}
		});

		int count = allObjs.size();
		for (int i = 0; i < count; i += 20) {
			indexData.add(i + 1 + "\n|\n" + (i + 20));
		}

		lvIndex = (ListView) findViewById(R.id.lvIndex);
		adapterIndex = new IndexAdapter<String>(this, indexData);
		lvIndex.setAdapter(adapterIndex);
		lvIndex.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				adapter.setDatas(AObj.getDataByRank(arg2 * 20 + 1,
						(arg2 + 1) * 20));
				adapter.notifyDataSetChanged();
			}
		});
		initUMeng();
		initWAPS();
	}

	private void initWAPS() {
		AdLinearLayout = (LinearLayout) findViewById(R.id.AdLinearLayout);
		AppConnect.getInstance(this);
		AppConnect.getInstance(this).getPoints(updatePointsNotifier);
		// 初始化自定义广告数据
		AppConnect.getInstance(this).initAdInfo();
		// 初始化插屏广告数据
		AppConnect.getInstance(this).initPopAd(this);
		// AppConnect.getInstance(this).awardPoints(10, updatePointsNotifier);
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

	private void initUMeng() {
		MobclickAgent.setDebugMode(false);
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

	public void onSaveToDB(View view) {
		Gson gson = new Gson();
		String data = DataUtil.getFromAssets(this, "leveljson");
		List<AObj> aObjs = gson.fromJson(data, new TypeToken<List<AObj>>() {
		}.getType());
		ActiveAndroid.beginTransaction();
		try {
			for (AObj aObj : aObjs) {
				aObj.save();
			}
			ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}
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
		case MORE_GET_POINT:
			AppConnect.getInstance(this).showOffers(this);
			break;
		case MORE_ABOUT:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		case MORE_FEEBACK:
			agent.startFeedbackActivity();
			break;
		case MORE_SHARE:
			Util.shareSomethingText(MainActivity.this, "分享",
					"我使用  #我是谁背影猜答案#  知道你也玩，能通关吗？试试这个吧");
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		AppConnect.getInstance(this).close();
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
