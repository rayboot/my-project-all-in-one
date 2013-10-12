package com.rayboot.hanzitingxie;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.rayboot.hanzitingxie.util.Util;
import com.umeng.fb.FeedbackAgent;

public class MoreActivity extends MyBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Holo_Theme_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		TextView tvVersion = (TextView) findViewById(R.id.tvVersion);
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
		Util.shareSomethingText(MoreActivity.this, "分享",
				"我使用  #汉字听写#  确实，汉字我们还会写多少呢？ 你试试？");
	}

	public void onFeebackClick(View view) {
		Util.openFeedbackActivity(this);
	}

}
