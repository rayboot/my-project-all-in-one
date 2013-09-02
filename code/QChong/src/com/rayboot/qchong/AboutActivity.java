package com.rayboot.qchong;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.umeng.analytics.MobclickAgent;

public class AboutActivity extends SherlockActivity {
	@InjectView(R.id.tvVersion)
	TextView tvVersion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		Views.inject(this);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
				"我使用  #Q币九折起#  我找到的全网最低买Q币，知道你喜欢，特意转给你。");
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
