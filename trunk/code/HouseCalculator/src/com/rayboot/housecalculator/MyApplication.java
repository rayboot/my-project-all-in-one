package com.rayboot.housecalculator;

import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;
import android.app.Application;

public class MyApplication extends Application {
	public static MyApplication mInstance;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance = this;
		Utilly.initUMeng(this);
		AppConnect.getInstance(this);
		AppConnect.getInstance(this).getPoints(updatePointsNotifier);
		AppConnect.getInstance(this).initPopAd(this);
		AppConnect.getInstance(this).awardPoints(1, updatePointsNotifier);
	}

	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		AppConnect.getInstance(this).finalize();
	}

	public UpdatePointsNotifier updatePointsNotifier = new UpdatePointsNotifier() {

		@Override
		public void getUpdatePointsFailed(String arg0) {
			// TODO Auto-generated method stub
		}

		@Override
		public void getUpdatePoints(String arg0, int arg1) {
			// TODO Auto-generated method stub
			Utilly.setInfoToShared("total_point", arg1 + "");
		}
	};

}
