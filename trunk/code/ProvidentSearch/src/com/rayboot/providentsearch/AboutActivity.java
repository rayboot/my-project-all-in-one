package com.rayboot.providentsearch;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.UMFeedbackService;

public class AboutActivity extends SherlockActivity {
	@InjectView(R.id.tvAbout)
	TextView tvAbout;

	@InjectView(R.id.btnLike)
	Button btnLike;
	@InjectView(R.id.btnSad)
	Button btnSad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		Views.inject(this);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		btnSad.setOnClickListener(onClickListener);
		btnLike.setOnClickListener(onClickListener);
		try {
			tvAbout.setText(String.format(
					getResources().getString(R.string.about_content),
					this.getPackageManager().getPackageInfo(
							this.getPackageName(), 0).versionName));
		} catch (NotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			// startActivity(new Intent(this, StatementActivity.class));
			overridePendingTransition(R.anim.base_slide_right_in,
					R.anim.stay_anim);
			break;
		default:
			break;
		}
		return true;
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.getId() == R.id.btnLike) {
				shareSomethingText(AboutActivity.this, "合肥公积金查询",
						getResources().getString(R.string.share_main_content));
			} else if (v.getId() == R.id.btnSad) {
				UMFeedbackService.openUmengFeedbackSDK(AboutActivity.this);
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// menu.add(0, 1, 0, "声明").setShowAsAction(
		// MenuItem.SHOW_AS_ACTION_IF_ROOM
		// | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

	public boolean shareSomethingText(Context context, String title,
			String content) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setType("text/plain"); // 分享发送到数据类型
		intent.putExtra(Intent.EXTRA_SUBJECT, title); // 分享的主题
		intent.putExtra(Intent.EXTRA_TEXT, content); // 附带的说明信息
		context.startActivity(Intent.createChooser(intent, "分享"));
		return true;
	}

}
