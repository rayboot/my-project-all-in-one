package com.rayboot.letsball.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.ImageView;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.baidu.mobstat.StatService;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rayboot.cus.widget.viewpager.TabPageIndicator;
import com.rayboot.letsball.LetsBallApp;
import com.rayboot.letsball.R;
import com.rayboot.letsball.adapter.TeamDetailAdapter;
import com.umeng.analytics.MobclickAgent;

public class TeamDetailActivity extends BaseSampleFragmentActivity {
	public static final String SP_NAME = "teamdetail";
	private static final String[] TITLES = new String[] { "球员一览", "过去比赛",
			"未来10场", "交锋历史" };
	private String[] DETAIL_URLS = new String[] { "/player/",
			"/outcome/history/", "/outcome/future/", "/outcome/future/next" };

	ImageView ivLogo;
	TextView tvName;
	TextView tvArea;
	TextView tvLeader;
	TextView tvGround;
	TextView tvNet;
	String baseUrl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(LetsBallApp.THEME); // Used for theme switching in samples
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_team_detail);

		ivLogo = (ImageView) findViewById(R.id.ivLogo);
		ImageLoader.getInstance().displayImage(
				getIntent().getStringExtra("teamInfo_logo"), ivLogo);
		// ivLogo.setImageResource(getIntent().getIntExtra("teamInfo_logo",
		// R.drawable.default_60));
		tvName = (TextView) findViewById(R.id.tvName);
		tvName.setText(getIntent().getStringExtra("teamInfo_name"));

		tvArea = (TextView) findViewById(R.id.tvArea);
		tvLeader = (TextView) findViewById(R.id.tvLeader);
		tvGround = (TextView) findViewById(R.id.tvGround);
		tvNet = (TextView) findViewById(R.id.tvNet);
		setUrlArray(getIntent().getStringExtra("teamInfo_url"));

		FragmentPagerAdapter adapter = new TeamDetailAdapter(
				getSupportFragmentManager(), TITLES, DETAIL_URLS);

		ViewPager pager = (ViewPager) findViewById(R.id.pager);
		pager.setAdapter(adapter);

		TabPageIndicator indicator = (TabPageIndicator) findViewById(R.id.indicator);
		indicator.setViewPager(pager);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		setTitle(tvName.getText());

	}

	private void setUrlArray(String data) {
		// TODO Auto-generated method stub
		for (String detail : DETAIL_URLS) {
			data = data.replace(detail, "");
		}

		for (int i = 0; i < DETAIL_URLS.length; i++) {
			DETAIL_URLS[i] = data + DETAIL_URLS[i];
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
		default:
			break;
		}
		return true;
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
}
