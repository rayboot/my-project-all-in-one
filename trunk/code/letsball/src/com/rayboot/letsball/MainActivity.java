package com.rayboot.letsball;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rayboot.letsball.activity.AboutActivity;
import com.rayboot.letsball.adapter.AreaInfoAdapter;
import com.rayboot.letsball.cache.SampleDataCache;
import com.rayboot.letsball.model.AreaInfo;
import com.rayboot.utility.DataUtility;
import com.rayboot.utility.Utility;
import com.tjerkw.slideexpandable.library.SlideExpandableListAdapter;
import com.umeng.fb.NotificationType;
import com.umeng.fb.UMFeedbackService;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MainActivity extends SherlockActivity {
	final int MORE_CLEAR_CACHE = 1;
	final int MORE_FEEBACK = 2;
	final int MORE_ABOUT = 3;
	final int MORE_SHARE = 4;
	ListView areaListView;
	List<AreaInfo> areaInfos = new ArrayList<AreaInfo>();
	AreaInfoAdapter aiAdapter;
	long lastBackTime = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		setTheme(LetsBallApp.THEME); // Used for theme switching in samples
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		areaListView = (ListView) findViewById(R.id.areaListView);

		areaInfos = AreaInfo.getAllAreaInfoFromDB();
		if (areaInfos == null || areaInfos.size() == 0) {
			new LoadContentTask().execute();
		} else {
			loadContent();
		}

		initUMeng();
	}

	private void initUMeng() {
		// 友盟意见反馈检索
		UMFeedbackService.enableNewReplyNotification(this,
				NotificationType.AlertDialog);
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
				case 1: // has no update
				case 2: // none wifi
				case 3: // time out
					break;
				}
			}
		});
	}

	private void loadContent() {
		// TODO Auto-generated method stub
		aiAdapter = new AreaInfoAdapter(this, areaInfos);

		areaListView.setAdapter(new SlideExpandableListAdapter(aiAdapter,
				R.id.rlAreaTitle, R.id.llLeagueType));
		// areaListView.setAdapter(aiAdapter);
		aiAdapter.notifyDataSetChanged();
	}

	public void initAreaInfo() {
		for (int area_type : AreaInfo.TYPE_AREA_ARRAY) {
			AreaInfo ai = AreaInfo.getAreaInfoFromDB(area_type);
			if (ai == null) {
				ai = new AreaInfo(this, area_type);
				ai.save();
			}
			areaInfos.add(ai);
		}
	}

	private class LoadContentTask extends AsyncTask<Object, Object, Object> {

		@Override
		protected Object doInBackground(Object... arg) {
			initAreaInfo();
			return true;
		}

		protected void onPostExecute(Object result) {
			// process result
			loadContent();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		SubMenu sub = menu.addSubMenu("Setting");
		// sub.add(0, MORE_CLEAR_CACHE, 0, "清空缓存");
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
		case MORE_CLEAR_CACHE:
			DataUtility.clearMatchInfos();
			SampleDataCache.getSampleDataCacheInstant(
					this.getApplicationContext()).clearCache();
			ImageLoader.getInstance().clearDiscCache();
			ImageLoader.getInstance().clearMemoryCache();
			Crouton.makeText(this, "清空缓存完毕。", Style.CONFIRM).show();
			break;
		case MORE_FEEBACK:
			Utility.sendCustomEvent(this, "10001");
			UMFeedbackService.openUmengFeedbackSDK(this);
			break;
		case MORE_SHARE:
			Utility.sendCustomEvent(this, "10000");
			Utility.shareSomethingText(this, "口袋联赛，联赛利器", getResources()
					.getString(R.string.share_main_content));
			break;
		case MORE_ABOUT:
			startActivity(new Intent(this, AboutActivity.class));
			overridePendingTransition(R.anim.base_slide_right_in,
					R.anim.stay_anim);
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
		Crouton.cancelAllCroutons();
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			long now = System.currentTimeMillis();
			if (now - lastBackTime < 3000) {
				finish();
			} else {
				Toast.makeText(this, "再按一次退出口袋联赛", Toast.LENGTH_LONG).show();
				lastBackTime = System.currentTimeMillis();
			}
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

}
