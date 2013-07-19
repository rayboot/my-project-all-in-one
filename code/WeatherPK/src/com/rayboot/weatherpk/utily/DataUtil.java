package com.rayboot.weatherpk.utily;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.rayboot.weatherpk.MyApplication;

public class DataUtil {
	public static boolean LocationUserSet = false;

	public static String getInfoFromShared(String key) {
		SharedPreferences preferences = MyApplication.mInstance
				.getSharedPreferences("weatherpk", Context.MODE_PRIVATE);
		return preferences.getString(key, null);
	}

	public static boolean setInfoToShared(String key, String value) {
		SharedPreferences preferences = MyApplication.mInstance
				.getSharedPreferences("weatherpk", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(key, value);
		editor.commit();
		return true;
	}

	public static String getFromAssets(Context context, String fileName) {
		try {
			InputStreamReader inputReader = new InputStreamReader(context
					.getResources().getAssets().open(fileName));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
