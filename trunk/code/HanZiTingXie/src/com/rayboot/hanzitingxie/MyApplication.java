package com.rayboot.hanzitingxie;

import android.R.integer;
import android.app.Application;
import android.view.LayoutInflater;

import com.activeandroid.ActiveAndroid;

public class MyApplication extends com.activeandroid.app.Application {
	public static final int PLAY_TYPE_WUJIN = 0;
	public static final int PLAY_TYPE_CHUANGGUAN = 1;
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
