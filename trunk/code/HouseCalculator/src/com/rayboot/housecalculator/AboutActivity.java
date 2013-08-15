package com.rayboot.housecalculator;

import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.umeng.fb.FeedbackAgent;

public class AboutActivity extends SherlockActivity {

	@InjectView(R.id.tvAppname)
	TextView tvAppname;
	@InjectView(R.id.tvVersion)
	TextView tvVersion;
	@InjectView(R.id.ivIcon)
	ImageView ivIcon;
	@InjectView(R.id.btnLike)
	Button btnLike;
	@InjectView(R.id.btnSad)
	Button btnSad;
	FeedbackAgent agent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.MyTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		Views.inject(this);

		agent = new FeedbackAgent(this);
		agent.sync();

		tvAppname.setText(getString(R.string.app_name));

		String version = "1.0";
		try {
			version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		tvVersion.setText("当前版本：v" + version);

		btnLike.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Utilly.shareSomethingText(AboutActivity.this, "分享",
						"我使用  #最优房贷计算器#  计算房贷灵活控制，还款一目了然，很好用，大家也来试试？");
			}
		});

		btnSad.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				agent.startFeedbackActivity();
			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
