package com.rayboot.letsball;

import java.io.IOException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.rayboot.letsball.model.AreaInfo;
import com.rayboot.letsball.model.PairRoundUrl;
import com.rayboot.letsball.model.TeamMapInfo;
import com.rayboot.utility.Global;
import com.rayboot.utility.JsoupUtility;
import com.umeng.analytics.MobclickAgent;

public class IndexActivity extends Activity {
	List<AreaInfo> areaInfos = new ArrayList<AreaInfo>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		// 初始化球队map表
		if (TeamMapInfo.getTeamMapInfoFromDB() == null) {
			Global.initTeamPara(this);
		}

		initMsgPush();

		new LoadContentTask().execute();

	}

	private class LoadContentTask extends AsyncTask<Object, Object, Object> {

		@Override
		protected Object doInBackground(Object... arg) {
			initAreaInfo();
			initLeagueData();
			return true;
		}

		protected void onPostExecute(Object result) {
			// process result
			finishLoad();
		}
	}

	private void finishLoad() {
		Intent intent = new Intent(IndexActivity.this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	private void initMsgPush() {
		PushManager.startWork(getApplicationContext(),
				PushConstants.LOGIN_TYPE_API_KEY,
				getResources().getString(R.string.baidu_msg_push_api_key));
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

	public void initLeagueData() {
		for (AreaInfo ai : areaInfos) {
			try {
				loadData(ai.getLeagueTypeInfo(false).allUrl);
				loadData(ai.getLeagueTypeInfo(true).allUrl);
			} catch (StreamCorruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void loadData(String url) throws ParseException,
			IllegalArgumentException, IOException {
		List<PairRoundUrl> pairsRoundUrl = PairRoundUrl
				.getAllPairRoundUrlFromDB(url.hashCode());
		if (pairsRoundUrl == null || pairsRoundUrl.size() <= 0) {
			JsoupUtility.parseAllRoundUrl(this, url);
		}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		PushManager.activityStarted(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		PushManager.activityStoped(this);
	}

	@Override
	protected void onNewIntent(Intent intent) {
		// TODO Auto-generated method stub
		super.onNewIntent(intent);
		setIntent(intent);
	}

}
