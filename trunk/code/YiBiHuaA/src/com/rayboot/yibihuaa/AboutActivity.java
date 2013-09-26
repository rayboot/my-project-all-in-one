package com.rayboot.yibihuaa;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.rayboot.yibihuaa.util.Util;

public class AboutActivity extends SherlockActivity {

	TextView tvVersion;
	TextView tvAppname;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		tvVersion = (TextView) findViewById(R.id.tvVersion);
		tvVersion.setText("当前版本：v" + Util.getVersionName(this));
		tvAppname = (TextView) findViewById(R.id.tvAppname);
		tvAppname.setText(R.string.app_name);
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
				"我使用  #一笔画答案#  知道你也玩，能通关吗？试试这个吧");
	}

	public void onResume() {
		super.onResume();
	}

	public void onPause() {
		super.onPause();
	}
}
