package com.rayboot.ahjiaotong;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.rayboot.ahjiaotong.util.Util;
import com.umeng.analytics.MobclickAgent;

public class AboutActivity extends SherlockActivity {
	TextView tvVersion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		
		tvVersion = (TextView) findViewById(R.id.tvVersion);

		tvVersion.setText("当前版本：v" + Util.getVersionName(this));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// This uses the imported MenuItem from ActionBarSherlock
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return true;
	}

	public void onShareClick(View view) {
		Util.shareSomethingText(AboutActivity.this, "分享",
				"我使用  #安徽交通违章查询#  试了，确实可以查到，知道你需要，特意发给你！");
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
