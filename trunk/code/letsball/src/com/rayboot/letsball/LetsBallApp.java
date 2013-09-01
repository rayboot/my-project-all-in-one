package com.rayboot.letsball;

import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

public class LetsBallApp extends com.activeandroid.app.Application {
	private static LetsBallApp sInstance = null;
	public static int THEME = R.style.Theme_MyTheme;

	public LetsBallApp() {
		super();
		// TODO Auto-generated constructor stub
		sInstance = this;
	}

	public static Context getAppInstant() {
		if (sInstance == null) {
			sInstance = new LetsBallApp();
		}
		return sInstance;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		initImageLoader(this);
	}

	public void initImageLoader(Context context) {
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);
	}
}
