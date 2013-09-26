package com.rayboot.yibihuaa;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.RecyclerListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class MainActivity extends SherlockActivity {

	// final int MORE_GET_POINT = 1;
	final int MORE_FEEBACK = 2;
	final int MORE_ABOUT = 3;
	final int MORE_SHARE = 4;

	final int BLUE_COUNT = 100;
	final int GREEN_COUNT = 60;
	final int RED_COUNT = 40;

	List<String> blueListDatas = new ArrayList<String>();
	List<String> greenListDatas = new ArrayList<String>();
	List<String> redListDatas = new ArrayList<String>();

	String curType = "blue";

	GridView gvContent;
	MyBaseAdapter<String> adapter;
	FeedbackAgent agent;
	int app_count = 0;
	LinearLayout AdLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		for (int i = 1; i <= BLUE_COUNT; i++) {
			blueListDatas.add(i + "");
		}
		for (int i = 1; i <= GREEN_COUNT; i++) {
			greenListDatas.add(i + "");
		}
		for (int i = 1; i <= RED_COUNT; i++) {
			redListDatas.add(i + "");
		}

		gvContent = (GridView) findViewById(R.id.gvContent);

		gvContent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						DetailActivity.class);
				intent.putExtra("content_detail",
						(String) adapter.getItem(arg2));
				intent.putExtra("content_type", curType);
				MainActivity.this.startActivity(intent);
			}
		});

		gvContent.setRecyclerListener(new RecyclerListener() {

			@Override
			public void onMovedToScrapHeap(View view) {
				// TODO Auto-generated method stub
				ImageView imageView = (ImageView) view.findViewById(R.id.ivImg);
				if (imageView != null) {
					imageView.setImageBitmap(null);
				}

			}
		});

		findViewById(R.id.btnBlue).setOnClickListener(onClickListener);
		findViewById(R.id.btnGreen).setOnClickListener(onClickListener);
		findViewById(R.id.btnRed).setOnClickListener(onClickListener);

		findViewById(R.id.btnBlue).performClick();
		initUMeng();
		// initWAPS();
	}

	// private void initWAPS() {
	// AdLinearLayout = (LinearLayout) findViewById(R.id.AdLinearLayout);
	// AppConnect.getInstance(this);
	// AppConnect.getInstance(this).getPoints(updatePointsNotifier);
	// // 初始化自定义广告数据
	// AppConnect.getInstance(this).initAdInfo();
	// // 初始化插屏广告数据
	// AppConnect.getInstance(this).initPopAd(this);
	// // AppConnect.getInstance(this).awardPoints(10, updatePointsNotifier);
	// // 禁用错误报告
	// AppConnect.getInstance(this).setCrashReport(false);
	// new AdView(this, AdLinearLayout).DisplayAd();
	// }
	//
	// private UpdatePointsNotifier updatePointsNotifier = new
	// UpdatePointsNotifier() {
	//
	// @Override
	// public void getUpdatePointsFailed(String arg0) {
	// // TODO Auto-generated method stub
	//
	// }
	//
	// @Override
	// public void getUpdatePoints(String arg0, int arg1) {
	// // TODO Auto-generated method stub
	// app_count = arg1;
	// }
	// };

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			String curWorld = "蓝色世界";
			switch (v.getId()) {
			case R.id.btnBlue:
				adapter = new ContentAdapter<String>(MainActivity.this,
						blueListDatas, "blue");
				curType = "blue";
				curWorld = "蓝色世界";
				break;
			case R.id.btnGreen:
				adapter = new ContentAdapter<String>(MainActivity.this,
						greenListDatas, "green");
				curType = "green";
				curWorld = "绿色世界";
				break;
			case R.id.btnRed:
				adapter = new ContentAdapter<String>(MainActivity.this,
						redListDatas, "red");
				curType = "red";
				curWorld = "红色世界";
				break;
			}
			gvContent.setAdapter(adapter);

			adapter.notifyDataSetChanged();
			setTitle(MainActivity.this.getResources().getString(
					R.string.app_name)
					+ "-" + curWorld);
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu sub = menu.addSubMenu("Setting");
		// sub.add(0, MORE_GET_POINT, 0, "获取积分");
		sub.add(0, MORE_FEEBACK, 0, "意见反馈");
		// sub.add(0, MORE_SHARE, 0, "分享");
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
		// case MORE_GET_POINT:
		// AppConnect.getInstance(this).showOffers(this);
		// break;
		case MORE_ABOUT:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		case MORE_FEEBACK:
			agent.startFeedbackActivity();
			break;
		// case MORE_SHARE:
		// Util.shareSomethingText(MainActivity.this, "分享",
		// "我使用  #我是谁背影猜答案#  知道你也玩，能通关吗？试试这个吧");
		// break;
		default:
			break;
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// AppConnect.getInstance(this).close();
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
