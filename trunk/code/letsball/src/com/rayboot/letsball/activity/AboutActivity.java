package com.rayboot.letsball.activity;

import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.rayboot.letsball.LetsBallApp;
import com.rayboot.letsball.R;
import com.rayboot.utility.Utility;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.UMFeedbackService;

public class AboutActivity extends SherlockActivity {

	TextView tvAbout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(LetsBallApp.THEME); // Used for theme switching in samples
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setTitle("关于");

		findViewById(R.id.btnLike).setOnClickListener(onClickListener);
		findViewById(R.id.btnSad).setOnClickListener(onClickListener);

		tvAbout = (TextView) findViewById(R.id.tvAbout);
		try {
			tvAbout.setText(String.format(
					getResources().getString(R.string.about_content),
					this.getPackageManager().getPackageInfo(
							this.getPackageName(), 0).versionName));
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.btnLike) {
				Utility.sendCustomEvent(AboutActivity.this, "10000");
				Utility.shareSomethingText(AboutActivity.this, "口袋联赛，联赛利器",
						getResources().getString(R.string.share_main_content));
			} else if (v.getId() == R.id.btnSad) {
				Utility.sendCustomEvent(AboutActivity.this, "10001");
				UMFeedbackService.openUmengFeedbackSDK(AboutActivity.this);
			}
		}
	};

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
	public boolean onOptionsItemSelected(MenuItem item) {
		// This uses the imported MenuItem from ActionBarSherlock
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.stay_anim,
					R.anim.base_slide_right_out);
			break;
		case 1:
			startActivity(new Intent(this, StatementActivity.class));
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
		menu.add(0, 1, 0, "声明").setShowAsAction(
				MenuItem.SHOW_AS_ACTION_IF_ROOM
						| MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onCreateOptionsMenu(menu);
	}
}
