package com.rayboot.yibihuaa;

import java.io.IOException;
import java.io.InputStream;

import uk.co.senab.photoview.PhotoViewAttacher;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.squareup.picasso.Picasso;
import com.umeng.analytics.MobclickAgent;

public class DetailActivity extends SherlockActivity {
	String curAObj;
	String curType;
	private PhotoViewAttacher mAttacher;
	ImageView ivContent;

	// LinearLayout AdLinearLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		curAObj = getIntent().getStringExtra("content_detail");
		curType = getIntent().getStringExtra("content_type");

		String tempString = "蓝色世界";
		if (curType.equals("blue")) {
			tempString = "蓝色世界";
		} else if (curType.equals("green")) {
			tempString = "绿色世界";
		} else if (curType.equals("red")) {
			tempString = "红色世界";
		}

		setTitle(tempString + " - 第" + curAObj + "关");

		ivContent = (ImageView) findViewById(R.id.ivImg);
		String urlString = "http://61.191.44.170/clt/publish/clt/resource/images/temp/"
				+ curType + "/" + curAObj + ".jpg";
		Picasso.with(this).load(urlString).into(ivContent);
		mAttacher = new PhotoViewAttacher(ivContent);
		mAttacher.setScaleType(ScaleType.CENTER_INSIDE);
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
