package com.rayboot.hanzitingxie;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.view.MenuItem;
import com.rayboot.hanzitingxie.util.Util;

public class MoreActivity extends MyBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		TextView tvVersion = (TextView) findViewById(R.id.tvVersion);
		tvVersion.setText("当前版本：v" + Util.getVersionName(this));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	public void onShareClick(View view) {
		Util.shareSomethingText(MoreActivity.this, "分享",
				"我使用  #汉字听写#  确实，汉字我们还会写多少呢？ 你试试？");
	}

	public void onFeebackClick(View view) {
		Util.openFeedbackActivity(this);
	}

}
