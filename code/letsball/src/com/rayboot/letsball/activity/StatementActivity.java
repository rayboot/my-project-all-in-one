package com.rayboot.letsball.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.baidu.mobstat.StatService;
import com.rayboot.letsball.LetsBallApp;
import com.rayboot.letsball.R;
import com.rayboot.utility.Utility;
import com.umeng.analytics.MobclickAgent;

public class StatementActivity extends SherlockActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(LetsBallApp.THEME); // Used for theme switching in samples
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_statement);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("声明");

		findViewById(R.id.btnContact).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Utility.sendEmail(StatementActivity.this, "口袋联赛", "");
			}
		});
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
