package com.rayboot.letsball.activity;

import java.io.IOException;

import org.apache.http.ParseException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.baidu.mobstat.StatService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rayboot.letsball.LetsBallApp;
import com.rayboot.letsball.R;
import com.rayboot.letsball.adapter.MatchStatisticAdapter;
import com.rayboot.letsball.adapter.MatchTimeLineAdapter;
import com.rayboot.letsball.model.MatchDetailInfo;
import com.rayboot.letsball.model.MatchInfo;
import com.rayboot.utility.JsoupUtility;
import com.rayboot.utility.Utility;
import com.umeng.analytics.MobclickAgent;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class MatchDetailActivity extends SherlockActivity {

	final int TIMELINE_MODE = 1;
	final int STATISTIC_MODE = 2;

	TextView tvTime;
	LinearLayout llHome;
	ImageView ivHome;
	TextView tvHome;
	LinearLayout llScore;
	TextView tvScore;
	TextView tvStatus;
	LinearLayout llAway;
	ImageView ivAway;
	TextView tvAway;

	Button btnEvent;
	Button btnAns;

	ListView lvContent;
	LinearLayout llLoading;

	MatchInfo matchInfo;
	MatchDetailInfo matchDetailInfo;

	BaseAdapter timelineAdapter;
	BaseAdapter statisticAdapter;

	String leagueName;

	View lineView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(LetsBallApp.THEME); // Used for theme switching in samples
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_match_detail);

		tvTime = (TextView) findViewById(R.id.tvTime);
		llHome = (LinearLayout) findViewById(R.id.llHome);
		ivHome = (ImageView) findViewById(R.id.ivHome);
		tvHome = (TextView) findViewById(R.id.tvHome);
		llScore = (LinearLayout) findViewById(R.id.llScore);
		tvScore = (TextView) findViewById(R.id.tvScore);
		tvStatus = (TextView) findViewById(R.id.tvStatues);
		llAway = (LinearLayout) findViewById(R.id.llAway);
		ivAway = (ImageView) findViewById(R.id.ivAway);
		tvAway = (TextView) findViewById(R.id.tvAway);
		btnEvent = (Button) findViewById(R.id.btnEvent);
		btnAns = (Button) findViewById(R.id.btnAns);
		btnEvent.setOnClickListener(onClickListener);
		btnAns.setOnClickListener(onClickListener);
		lvContent = (ListView) findViewById(R.id.lvContent);
		llLoading = (LinearLayout) findViewById(R.id.content);
		lineView = findViewById(R.id.viewLine);

		llHome.setOnClickListener(onClickListener);
		llAway.setOnClickListener(onClickListener);

		matchInfo = (MatchInfo) getIntent().getSerializableExtra("matchinfo");
		tvTime.setText(matchInfo.startTime);
		ImageLoader.getInstance().displayImage(
				matchInfo.getHomeTeamInfo().logoName, ivHome);
		// ivHome.setImageResource(matchInfo.getHomeTeamInfo().getTeamLogo());
		tvHome.setText(matchInfo.getHomeTeamInfo().teamName);
		ImageLoader.getInstance().displayImage(
				matchInfo.getAwayTeamInfo().logoName, ivAway);
		// ivAway.setImageResource(matchInfo.getAwayTeamInfo().getTeamLogo());
		tvAway.setText(matchInfo.getAwayTeamInfo().teamName);
		tvScore.setText(matchInfo.score);
		tvStatus.setText(matchInfo.getStatusString());

		leagueName = getIntent().getStringExtra("leagueName");

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setTitle("比赛详情");

		matchDetailInfo = MatchDetailInfo
				.getMatchDetailInfoFDB(matchInfo.detailUrl);
		if (matchDetailInfo == null) {
			new LoadContentTask().execute(true);
		} else {
			loadContent();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Crouton.cancelAllCroutons();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		StatService.onPause(this);
		MobclickAgent.onPause(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		StatService.onResume(this);
		MobclickAgent.onResume(this);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.equals(btnEvent)) {
				setContent(TIMELINE_MODE);
			} else if (v.equals(btnAns)) {
				setContent(STATISTIC_MODE);
			} else if (v.equals(llHome)) {
				Intent intent = new Intent(MatchDetailActivity.this,
						TeamDetailActivity.class);
				intent.putExtra("teamInfo_name",
						matchInfo.getHomeTeamInfo().teamName);
				intent.putExtra("teamInfo_logo",
						matchInfo.getHomeTeamInfo().logoName);
				intent.putExtra("teamInfo_url",
						matchInfo.getHomeTeamInfo().detailUrl);
				MatchDetailActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.base_slide_right_in,
						R.anim.stay_anim);
			} else if (v.equals(llAway)) {
				Intent intent = new Intent(MatchDetailActivity.this,
						TeamDetailActivity.class);
				intent.putExtra("teamInfo_name",
						matchInfo.getAwayTeamInfo().teamName);
				intent.putExtra("teamInfo_logo",
						matchInfo.getAwayTeamInfo().logoName);
				intent.putExtra("teamInfo_url",
						matchInfo.getAwayTeamInfo().detailUrl);
				MatchDetailActivity.this.startActivity(intent);
				overridePendingTransition(R.anim.base_slide_right_in,
						R.anim.stay_anim);
			}
		}
	};

	private void loadContent() {
		// TODO Auto-generated method stub
		llLoading.setVisibility(View.GONE);
		lineView.setVisibility(View.VISIBLE);
		if (matchDetailInfo == null) {
			Crouton.makeText(this, "数据解析错误！", Style.ALERT).show();
			return;
		}
		setContent(TIMELINE_MODE);

	}

	private void setContent(int mode) {
		if (matchDetailInfo == null) {
			Crouton.makeText(this, "数据解析错误！", Style.ALERT).show();
			return;
		}

		switch (mode) {
		case TIMELINE_MODE:
			btnEvent.setSelected(true);
			btnAns.setSelected(false);
			if (timelineAdapter == null) {
				timelineAdapter = new MatchTimeLineAdapter(this,
						matchDetailInfo.getAllMatchTimeLineObjs());
			}
			lvContent.setAdapter(timelineAdapter);
			timelineAdapter.notifyDataSetChanged();
			break;
		case STATISTIC_MODE:
			btnEvent.setSelected(false);
			btnAns.setSelected(true);
			if (statisticAdapter == null) {
				statisticAdapter = new MatchStatisticAdapter(this,
						matchDetailInfo.getAllMatchStatisticObjs());
			}
			lvContent.setAdapter(statisticAdapter);
			statisticAdapter.notifyDataSetChanged();
			break;
		}
	}

	private class LoadContentTask extends AsyncTask<Object, Object, Object> {
		private boolean isUseCache = false;

		@Override
		protected Object doInBackground(Object... arg) {
			isUseCache = (Boolean) arg[0];
			try {
				matchDetailInfo = JsoupUtility.parseMatchDetailUrl(
						MatchDetailActivity.this, matchInfo.detailUrl,
						isUseCache);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO: handle exception
				e.printStackTrace();
				matchDetailInfo = null;
			}
			return true;
		}

		protected void onPostExecute(Object result) {
			// process result
			loadContent();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 1, 0, "分享")
				.setIcon(R.drawable.ic_share)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM
								| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// This uses the imported MenuItem from ActionBarSherlock
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.stay_anim,
					R.anim.base_slide_right_out);
			break;
		case 1:
			Utility.shareSomethingText(this, "口袋联赛", leagueName + " 第"
					+ matchInfo.roundNum + "轮 "
					+ matchInfo.getHomeTeamInfo().teamName + " "
					+ matchInfo.score + " "
					+ matchInfo.getAwayTeamInfo().teamName + "（口袋联赛精彩播报）");
			break;
		default:
			break;
		}
		return true;
	}
}
