package com.rayboot.letsball.activity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.baidu.mobstat.StatService;
import com.rayboot.cus.widget.viewpager.TabPageIndicator;
import com.rayboot.letsball.LetsBallApp;
import com.rayboot.letsball.R;
import com.rayboot.letsball.adapter.RoundAdapter;
import com.rayboot.letsball.model.AreaInfo;
import com.rayboot.letsball.model.LeagueTypeInfo;
import com.rayboot.letsball.model.PairRoundUrl;
import com.rayboot.utility.JsoupUtility;
import com.rayboot.utility.Utility;
import com.umeng.analytics.MobclickAgent;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class LeagueActivity extends BaseSampleFragmentActivity {

	int focesRount = -1;
	LeagueTypeInfo leagueInfo;
	// Map<String, String> mapRoundUrl = new HashMap<String, String>();
	List<PairRoundUrl> pairsRoundUrl = new ArrayList<PairRoundUrl>();
	private final int TABLE_ID = 1;
	private final int SHOOTER_ID = 2;
	private int curRound = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(LetsBallApp.THEME); // Used for theme switching in samples
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_league_loading);
		boolean isLeague = getIntent().getBooleanExtra("league_or_cup", true);
		int areaId = getIntent().getIntExtra("area_id", 1);
		leagueInfo = new AreaInfo(this, areaId).getLeagueTypeInfo(isLeague);
		setTitle(leagueInfo.name);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		Utility.sendCustomEvent(this, leagueInfo.leagueId + "");

		new LoadContentTask().execute(leagueInfo);
	}

	private void loadContent(boolean isSuccessLoad) {
		if (isSuccessLoad) {
			setContentView(R.layout.activity_league);
			mPager = (ViewPager) findViewById(R.id.pager);
			mIndicator = (TabPageIndicator) findViewById(R.id.indicator);
			mAdapter = new RoundAdapter(getSupportFragmentManager(), leagueInfo);
			mPager.setAdapter(mAdapter);
			mIndicator.setViewPager(mPager, curRound);
		} else {
			Crouton.makeText(LeagueActivity.this, "数据解析错误，请检测网络！", Style.ALERT)
					.show();
			findViewById(R.id.content).setVisibility(View.GONE);
		}
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

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Crouton.cancelAllCroutons();
		super.onDestroy();
	}

	private class LoadContentTask extends AsyncTask<Object, Object, Object> {

		private LeagueTypeInfo curLeague;

		@Override
		protected Object doInBackground(Object... arg) {
			curLeague = (LeagueTypeInfo) arg[0];
			boolean result = false;
			try {
				pairsRoundUrl = PairRoundUrl
						.getAllPairRoundUrlFromDB(curLeague.allUrl.hashCode());
				if (pairsRoundUrl == null || pairsRoundUrl.size() <= 0) {
					pairsRoundUrl = JsoupUtility.parseAllRoundUrl(
							LeagueActivity.this, curLeague.allUrl);
					setCurRound();
					result = true;
				} else {
					setCurRound();
					result = true;
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}

		protected void onPostExecute(Object result) {
			// process result
			loadContent((Boolean) result);
		}
	}

	public void setCurRound() {
		if (pairsRoundUrl == null || pairsRoundUrl.size() <= 0) {
			return;
		}
		long now = System.currentTimeMillis();
		for (PairRoundUrl pru : pairsRoundUrl) {
			if (pru.endTime < now) {
				continue;
			} else {
				curRound = pairsRoundUrl.indexOf(pru);
				return;
			}
		}
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
		case SHOOTER_ID:
		case TABLE_ID:
			startActivity(new Intent(this, TableAndShooterActivity.class)
					.putExtra("TYPE", item.getItemId()));
			overridePendingTransition(R.anim.base_slide_right_in,
					R.anim.stay_anim);
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, SHOOTER_ID, 0, "射手榜")
				.setIcon(R.drawable.ic_s)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM
								| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		menu.add(0, TABLE_ID, 0, "积分榜")
				.setIcon(R.drawable.ic_t)
				.setShowAsAction(
						MenuItem.SHOW_AS_ACTION_IF_ROOM
								| MenuItem.SHOW_AS_ACTION_WITH_TEXT);

		return super.onCreateOptionsMenu(menu);
	}

}
