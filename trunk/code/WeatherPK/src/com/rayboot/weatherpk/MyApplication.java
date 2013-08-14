package com.rayboot.weatherpk;

import java.lang.reflect.Type;
import java.util.List;

import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rayboot.weatherpk.obj.CityObj;
import com.rayboot.weatherpk.obj.ProvinceObj;
import com.rayboot.weatherpk.utily.DataUtil;

public class MyApplication extends com.activeandroid.app.Application {
	public static MyApplication mInstance;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mInstance = this;
		initDB();
	}

	private void initDB() {
		if (CityObj.getCityObjAll() != null
				&& CityObj.getCityObjAll().size() > 0) {
			return;
		}
		Gson gson = new Gson();
		Type ProvinceObjType = new TypeToken<List<ProvinceObj>>() {
		}.getType();
		ActiveAndroid.beginTransaction();
		try {
			List<ProvinceObj> provinceObjs = gson.fromJson(
					DataUtil.getFromAssets(this, "citycode"), ProvinceObjType);
			for (ProvinceObj provinceObj : provinceObjs) {
				for (CityObj cityObj : provinceObj.citys) {
					cityObj.province = provinceObj.province;
					cityObj.save();
				}
			}
			ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}
	}
}
