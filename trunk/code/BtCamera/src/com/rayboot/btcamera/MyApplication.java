package com.rayboot.btcamera;

import org.holoeverywhere.HoloEverywhere;
import org.holoeverywhere.HoloEverywhere.PreferenceImpl;
import org.holoeverywhere.LayoutInflater;
import org.holoeverywhere.ThemeManager;

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
	}

	@Override
	public void onTerminate() {
		super.onTerminate();
	}

}
