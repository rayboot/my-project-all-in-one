package com.rayboot.ahjiaotong;

import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.rayboot.ahjiaotong.util.Util;
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
	final int MORE_CLEAR_DB = 5;
	FeedbackAgent agent;

	Button btnHead;
	Button btnType;
	Button btnSearch;
	EditText etNum;
	EditText etFrameNum;

	ListView lvContent;
	MyBaseAdapter<HistoryObj> adapter;
	List<HistoryObj> historyObjs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnHead = (Button) findViewById(R.id.btnHead);
		btnType = (Button) findViewById(R.id.btnType);
		btnSearch = (Button) findViewById(R.id.btnSearch);
		lvContent = (ListView) findViewById(R.id.lvContent);

		etNum = (EditText) findViewById(R.id.etNum);
		etFrameNum = (EditText) findViewById(R.id.etFrameNum);

		btnHead.setOnClickListener(onClickListener);
		btnType.setOnClickListener(onClickListener);
		btnSearch.setOnClickListener(onClickListener);
		initUMeng();
		initHistory();
		lvContent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				HistoryObj historyObj = (HistoryObj) arg1.getTag(R.string.tag1);

				btnHead.setText(historyObj.license.subSequence(0, 2));
				etNum.setText(historyObj.license.substring(2));
				etFrameNum.setText(historyObj.frameNumber);
				btnType.setTag(historyObj.model);
				btnType.setText(historyObj.modelName);
				btnSearch.performClick();
			}
		});
	}

	private void initHistory() {
		adapter = new HistoryAdapter<HistoryObj>(this, HistoryObj.getAllData());
		lvContent.setAdapter(adapter);
	}

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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
		btnHead.setText(Global.headNum);
		btnType.setText(Global.carType);
		btnType.setTag(Global.carTypeNum);

		if (adapter != null) {
			adapter.setDatas(HistoryObj.getAllData());
			adapter.notifyDataSetChanged();
		}
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = null;
			switch (v.getId()) {
			case R.id.btnHead:
				intent = new Intent(MainActivity.this, HeadNumSelActivity.class);
				MainActivity.this.startActivity(intent);
				break;
			case R.id.btnType:
				intent = new Intent(MainActivity.this, TypeSelActivity.class);
				MainActivity.this.startActivity(intent);
				break;
			case R.id.btnSearch:
				intent = new Intent(MainActivity.this, ResultActivity.class);
				intent.putExtra("license", btnHead.getText().toString().trim()
						+ etNum.getText().toString().trim());
				intent.putExtra("frameNumber", etFrameNum.getText().toString()
						.trim());
				intent.putExtra("model", btnType.getTag() + "");
				intent.putExtra("modelName", btnType.getText().toString()
						.trim());
				MainActivity.this.startActivity(intent);
				break;
			default:
				break;
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu sub = menu.addSubMenu("Setting");
		sub.add(0, MORE_CLEAR_DB, 0, "清空历史记录");
		// sub.add(0, MORE_GET_POINT, 0, "获取积分");
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
		case android.R.id.home:
		case 0:
			return false;
			// case MORE_GET_POINT:
			// AppConnect.getInstance(this).showOffers(this);
			// break;
		case MORE_ABOUT:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		case MORE_FEEBACK:
			agent.startFeedbackActivity();
			break;
		case MORE_SHARE:
			Util.shareSomethingText(MainActivity.this, "分享",
					"我使用  #安徽交通违章查询#  试了，确实可以查到，知道你需要，特意发给你！");
			break;
		case MORE_CLEAR_DB:
			HistoryObj.removeAllData();
			adapter.setDatas(HistoryObj.getAllData());
			adapter.notifyDataSetChanged();
			break;
		default:
			break;
		}
		return true;
	}

}
