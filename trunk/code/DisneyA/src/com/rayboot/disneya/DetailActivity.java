package com.rayboot.disneya;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.rayboot.disneya.obj.AObj;
import com.umeng.analytics.MobclickAgent;

public class DetailActivity extends SherlockActivity {
	AObj curAObj;
	Gson gson = new Gson();
	LinearLayout AdLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		curAObj = gson.fromJson(getIntent().getStringExtra("content_detail"),
				AObj.class);
		ImageLoader.getInstance().displayImage("asse"
				+ "ts://" + curAObj.file_name,
				(ImageView) findViewById(R.id.ivImg));
		TextView tvAnswer = (TextView) findViewById(R.id.tvAnswer);
		tvAnswer.setText(curAObj.song_name);
		TextView tvTip = (TextView) findViewById(R.id.tvTip);
		tvTip.setText(curAObj.tips);
		AdLinearLayout = (LinearLayout) findViewById(R.id.AdLinearLayout);
		// AppConnect.getInstance(this).showPopAd(this);
		// new AdView(this, AdLinearLayout).DisplayAd();
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

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
