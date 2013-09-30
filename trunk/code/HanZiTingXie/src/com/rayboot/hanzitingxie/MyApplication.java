package com.rayboot.hanzitingxie;

import com.activeandroid.ActiveAndroid;

public class MyApplication extends org.holoeverywhere.app.Application {

	public static final int PLAY_TYPE_CHUANGGUAN = 0;
	public static final int PLAY_TYPE_WUJIN = 1;

	@Override
	public void onCreate() {
		super.onCreate();
		ActiveAndroid.initialize(this);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
		ActiveAndroid.dispose();
	}

}
