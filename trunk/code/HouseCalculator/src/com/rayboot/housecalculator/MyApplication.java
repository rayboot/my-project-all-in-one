package com.rayboot.housecalculator;

import android.app.Application;

public class MyApplication extends Application {
	public static MyApplication mInstance;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance = this;
		Utilly.initUMeng(this);
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
	}

}
