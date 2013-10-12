package com.rayboot.hanzitingxie;

import org.holoeverywhere.HoloEverywhere;
import org.holoeverywhere.HoloEverywhere.PreferenceImpl;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.ThemeManager;

import com.activeandroid.ActiveAndroid;
import com.umeng.analytics.MobclickAgent;

public class MyApplication extends org.holoeverywhere.app.Application {
	private static final String PACKAGE = MyApplication.class.getPackage()
			.getName();

	public static final int PLAY_TYPE_CHUANGGUAN = 0;
	public static final int PLAY_TYPE_WUJIN = 1;

	static {
		HoloEverywhere.DEBUG = true;
		HoloEverywhere.PREFERENCE_IMPL = PreferenceImpl.JSON;
		LayoutInflater.registerPackage(PACKAGE + ".widget");
		ThemeManager.setDefaultTheme(ThemeManager.LIGHT
				| ThemeManager.NO_ACTION_BAR);
	}

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
