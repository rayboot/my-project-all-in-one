package com.rayboot.weatherpk.utily;

import java.io.File;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;

public class Utilly {

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
}
