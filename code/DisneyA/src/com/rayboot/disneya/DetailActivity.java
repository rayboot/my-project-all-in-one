package com.rayboot.disneya;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

public class DetailActivity extends SherlockActivity {
	int curAObj;
	LinearLayout AdLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		curAObj = getIntent().getIntExtra("content_detail", 1);
		Picasso.with(this)
				.load("http://61.191.44.170/clt/publish/clt/resource/images/temp/"
						+ curAObj + ".png").placeholder(R.drawable.ic_launcher)
				.into((ImageView) findViewById(R.id.ivImg));
		TextView tvAnswer = (TextView) findViewById(R.id.tvAnswer);
		tvAnswer.setText(curAObj + "");
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
