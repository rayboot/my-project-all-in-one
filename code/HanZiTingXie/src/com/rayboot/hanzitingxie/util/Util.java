package com.rayboot.hanzitingxie.util;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Bundle;

import com.umeng.fb.FeedbackAgent;

public class Util {

	// 获取AppKey
	public static String getMetaValue(Context context, String metaKey) {
		Bundle metaData = null;
		String apiKey = null;
		if (context == null || metaKey == null) {
			return null;
		}
		try {
			ApplicationInfo ai = context.getPackageManager()
					.getApplicationInfo(context.getPackageName(),
							PackageManager.GET_META_DATA);
			if (null != ai) {
				metaData = ai.metaData;
			}
			if (null != metaData) {
				apiKey = metaData.getString(metaKey);
			}
		} catch (NameNotFoundException e) {

		}
		return apiKey;
	}

	public static boolean shareSomethingText(Context context, String title,
			String content) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setType("text/plain"); // 分享发送到数据类型
		intent.putExtra(Intent.EXTRA_SUBJECT, title); // 分享的主题
		intent.putExtra(Intent.EXTRA_TEXT, content); // 附带的说明信息
		context.startActivity(Intent.createChooser(intent, "分享"));
		return true;
	}

	public static boolean shareSomethingTextPhoto(Context context,
			String title, String content, String filepath) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setType("text/plain"); // 分享发送到数据类型
		intent.putExtra(Intent.EXTRA_SUBJECT, title); // 分享的主题
		intent.putExtra(Intent.EXTRA_TEXT, content); // 附带的说明信息
		File file = new File(filepath);
		intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
		intent.setType("image/jpeg");
		context.startActivity(Intent.createChooser(intent, "分享"));
		return true;
	}

	public static String getVersionName(Context context) {
		// 获取packagemanager的实例
		PackageManager packageManager = context.getPackageManager();
		// getPackageName()是你当前类的包名，0代表是获取版本信息
		PackageInfo packInfo;
		String version = "1.0.0";
		try {
			packInfo = packageManager.getPackageInfo(context.getPackageName(),
					0);
			version = packInfo.versionName;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return version;
	}

	public static void openFeedbackActivity(Context context) {

		FeedbackAgent agent = new FeedbackAgent(context);
		agent.startFeedbackActivity();
	}

    public static void sendEmail(Context context, String subject, String text)
    {

        // 给someone@domain.com发邮件发送内容为“Hello”的邮件
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,
                new String[] { "rayboot.work@gmail.com" });
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, text);
        intent.setType("text/plain");
        context.startActivity(intent);
    }
}
