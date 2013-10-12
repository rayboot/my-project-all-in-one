package com.rayboot.hanzitingxie;

import com.umeng.analytics.MobclickAgent;

public class MyBaseActivity extends org.holoeverywhere.app.Activity {
	
	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
