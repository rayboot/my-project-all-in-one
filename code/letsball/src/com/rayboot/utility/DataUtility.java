package com.rayboot.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.rayboot.letsball.LetsBallApp;
import com.rayboot.letsball.model.MatchInfo;
import com.rayboot.letsball.model.RoundInfo;
import com.rayboot.utility.base64.Base64;

public class DataUtility {
	public static Object getMatchInfos(String urlString)
			throws StreamCorruptedException, IOException,
			ClassNotFoundException {

		SharedPreferences preferences = LetsBallApp.getAppInstant()
				.getSharedPreferences("matchinfos", Context.MODE_PRIVATE);
		String savedDataString = preferences.getString(urlString, null);
		if (savedDataString == null) {
			return null;
		}
		byte[] base64Bytes = Base64.decode(savedDataString);
		ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		return ois.readObject();
	}

	public static boolean setMatchInfos(String urlString,
			ArrayList<MatchInfo> matchList) throws IOException {
		SharedPreferences preferences = LetsBallApp.getAppInstant()
				.getSharedPreferences("matchinfos", Context.MODE_PRIVATE);
		// 将map转换为byte[]
		ByteArrayOutputStream toByte = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(toByte);
		oos.writeObject(matchList);
		// 对byte[]进行Base64编码

		String payCityMapBase64 = new String(
				Base64.encode(toByte.toByteArray()));
		// 存储
		Editor editor = preferences.edit();
		editor.putString(urlString, payCityMapBase64);
		editor.commit();
		return true;
	}

	public static boolean clearMatchInfos() {
		SharedPreferences preferences = LetsBallApp.getAppInstant()
				.getSharedPreferences("matchinfos", Context.MODE_PRIVATE);
		return preferences.edit().clear().commit();
	}

	public static Object getRoundInfo(String urlString)
			throws StreamCorruptedException, IOException,
			ClassNotFoundException {

		SharedPreferences preferences = LetsBallApp.getAppInstant()
				.getSharedPreferences("roundinfo", Context.MODE_PRIVATE);
		String savedDataString = preferences.getString(urlString, null);
		if (savedDataString == null) {
			return null;
		}
		byte[] base64Bytes = Base64.decode(savedDataString);
		ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		return ois.readObject();
	}

	public static boolean setRoundInfo(String urlString, RoundInfo ri)
			throws IOException {
		SharedPreferences preferences = LetsBallApp.getAppInstant()
				.getSharedPreferences("roundinfo", Context.MODE_PRIVATE);
		// 将map转换为byte[]
		ByteArrayOutputStream toByte = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(toByte);
		oos.writeObject(ri);
		// 对byte[]进行Base64编码

		String payCityMapBase64 = new String(
				Base64.encode(toByte.toByteArray()));
		// 存储
		Editor editor = preferences.edit();
		editor.putString(urlString, payCityMapBase64);
		editor.commit();
		return true;
	}

	public static Object getObjectInfo(String spName, String key)
			throws StreamCorruptedException, IOException,
			ClassNotFoundException {

		SharedPreferences preferences = LetsBallApp.getAppInstant()
				.getSharedPreferences(spName, Context.MODE_PRIVATE);
		String savedDataString = preferences.getString(key, null);
		if (savedDataString == null) {
			return null;
		}
		byte[] base64Bytes = Base64.decode(savedDataString);
		ByteArrayInputStream bais = new ByteArrayInputStream(base64Bytes);
		ObjectInputStream ois = new ObjectInputStream(bais);
		return ois.readObject();
	}

	public static boolean setObjectInfo(String spName, String key, Object obj)
			throws IOException {
		SharedPreferences preferences = LetsBallApp.getAppInstant()
				.getSharedPreferences(spName, Context.MODE_PRIVATE);
		// 将map转换为byte[]
		ByteArrayOutputStream toByte = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(toByte);
		oos.writeObject(obj);
		// 对byte[]进行Base64编码

		String payCityMapBase64 = new String(
				Base64.encode(toByte.toByteArray()));
		// 存储
		Editor editor = preferences.edit();
		editor.putString(key, payCityMapBase64);
		editor.commit();
		return true;
	}

}
