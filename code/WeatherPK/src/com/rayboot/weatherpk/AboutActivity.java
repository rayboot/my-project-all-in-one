package com.rayboot.weatherpk;

import com.rayboot.weatherpk.utily.Utilly;
import com.umeng.fb.FeedbackAgent;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

public class AboutActivity extends Activity {
	@InjectView(R.id.btnBack)
	Button btnBack;
	@InjectView(R.id.btnShare)
	Button btnShare;
	@InjectView(R.id.btnFeedback)
	Button btnFeedback;
	FeedbackAgent agent;
	@InjectView(R.id.tvAppname)
	TextView tvAppname;
	@InjectView(R.id.tvVersion)
	TextView tvVersion;
	@InjectView(R.id.ivIcon)
	ImageView ivIcon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		Views.inject(this);

		agent = new FeedbackAgent(this);
		agent.sync();

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				AboutActivity.this.finish();
			}
		});
		btnShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Utilly.shareSomethingText(AboutActivity.this, "分享",
						"我使用  #哪最热#  看到现在我这里温度全国排名第几，很有意思，还能PK，赶紧来试试吧。");
			}
		});
		btnFeedback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				agent.startFeedbackActivity();
			}
		});

		tvAppname.setText(getResources().getString(R.string.app_name));
		tvVersion.setText("当前版本：v" + getVersionName());
		ivIcon.setBackgroundResource(R.drawable.ic_launcher);
	}

	private String getVersionName() {
		// 获取packagemanager的实例
		PackageManager packageManager = getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		String version = "1.0.0";
		try {
			packInfo = packageManager.getPackageInfo(getPackageName(), 0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}
}
