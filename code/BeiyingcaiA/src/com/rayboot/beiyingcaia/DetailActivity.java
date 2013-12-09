package com.rayboot.beiyingcaia;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import cn.waps.AdView;
import cn.waps.AppConnect;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.rayboot.beiyingcaia.obj.AObj;
import com.squareup.picasso.Picasso;
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
		String urlString  = "http://61.191.44.170/clt/publish/clt/resource/images/temp/"
				+ curAObj.file_name;
		Picasso.with(this)
				.load(urlString)
				.into((ImageView) findViewById(R.id.ivImg));
		TextView tvAnswer = (TextView) findViewById(R.id.tvAnswer);
		tvAnswer.setText(curAObj.song_name);
		TextView tvTip = (TextView) findViewById(R.id.tvTip);
		tvTip.setText(curAObj.tips);
		AdLinearLayout = (LinearLayout) findViewById(R.id.AdLinearLayout);
		AppConnect.getInstance(this).showPopAd(this);
		new AdView(this, AdLinearLayout).DisplayAd();
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
