package com.rayboot.yibihuaa;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.analytics.MobclickAgent;

public class DetailActivity extends SherlockActivity {
	String curAObj;
	String curType;

	// LinearLayout AdLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		curAObj = getIntent().getStringExtra("content_detail");
		curType = getIntent().getStringExtra("content_type");
		ImageLoader.getInstance().displayImage(
				"assets://" + curType + "/" + curAObj + ".jpg",
				(ImageView) findViewById(R.id.ivImg));
		// AdLinearLayout = (LinearLayout) findViewById(R.id.AdLinearLayout);
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
