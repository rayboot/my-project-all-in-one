package com.rayboot.letsball.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.baidu.mobstat.StatService;
import com.rayboot.letsball.R;
import com.rayboot.letsball.cache.SampleDataCache;
import com.rayboot.utility.DataUtility;
import com.umeng.analytics.MobclickAgent;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class SettingActivity extends Activity {

	Button btnClearCache;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);

		// final ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		// actionBar.setTitle("设置");

		btnClearCache = (Button) findViewById(R.id.btnClearCache);
		btnClearCache.setOnClickListener(onClickListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (v.equals(btnClearCache)) {
				DataUtility.clearMatchInfos();
				SampleDataCache.getSampleDataCacheInstant(
						SettingActivity.this.getApplicationContext())
						.clearCache();
				Crouton.makeText(SettingActivity.this, "清空缓存完毕。", Style.CONFIRM)
						.show();
			}
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		Crouton.cancelAllCroutons();
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
