package com.rayboot.pinyincrazy;

import org.holoeverywhere.HoloEverywhere;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.ThemeManager;
import org.holoeverywhere.HoloEverywhere.PreferenceImpl;

import com.activeandroid.ActiveAndroid;

public class MyApplication extends org.holoeverywhere.app.Application {
	private static final String PACKAGE = MyApplication.class.getPackage()
			.getName();

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
