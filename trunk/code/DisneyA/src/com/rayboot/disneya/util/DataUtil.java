package com.rayboot.disneya.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import android.content.Context;
import android.text.TextUtils;

public class DataUtil {
	public static boolean LocationUserSet = false;
	public static String domain = "";

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
	
	public static String getDomain() {
		if (!TextUtils.isEmpty(domain)) {
			return domain;
		}
		String en = "aHR0cDovLzYxLjE5MS40NC4xNzAvY2x0L3B1Ymxpc2gvY2x0L3Jlc291cmNlL2ltYWdlcy90ZW1wLw==";
		try {
			domain = new String(Base64.decode(en, Base64.DEFAULT), "US-ASCII");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return domain;
	}
}
