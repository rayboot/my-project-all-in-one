package com.rayboot.pinyincrazy.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class DataUtil {

	public static int YU_SU = 20;
	public static int YIN_DIAO = 50;
	public static int YIN_LIANG = 80;
	public static int g_wenzibi = -100;

	public static int getInfoFromShared(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(
				"hanzitingxie", Context.MODE_PRIVATE);
		return preferences.getInt(key, 0);
	}

	public static boolean setInfoToShared(Context context, String key, int value) {
		SharedPreferences preferences = context.getSharedPreferences(
				"hanzitingxie", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt(key, value);
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
