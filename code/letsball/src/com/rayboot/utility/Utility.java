package com.rayboot.utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import android.content.Context;
import android.content.Intent;

import com.baidu.mobstat.StatService;
import com.rayboot.letsball.LetsBallApp;
import com.umeng.analytics.MobclickAgent;

public class Utility {

	public static String getWholeUrl(String url) {
		return url.contains(Global.Domain) ? url : Global.Domain + url;
	}

	public static String getAllMatchUrl(String fileName) {
//		try {
//			InputStreamReader inputReader = new InputStreamReader(LetsBallApp
//					.getAppInstant().getResources().getAssets().open(fileName));
//			BufferedReader bufReader = new BufferedReader(inputReader);
//			String line = "";
//			while ((line = bufReader.readLine()) != null) {
//				return line;
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return null;
		

        BufferedReader bufferedReader = null;  
        try {  
            bufferedReader = new BufferedReader(new InputStreamReader((LetsBallApp
					.getAppInstant().getAssets().open(fileName))));
            String line = null;  
            StringBuilder builder = new StringBuilder();  
            while (null != (line = bufferedReader.readLine())) {  
                builder.append(line);  
            }  
            return builder.toString();  
        } catch (IOException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }finally{  
            if (null != bufferedReader) {  
                try {  
                    bufferedReader.close();  
                } catch (IOException e) {  
                    // TODO Auto-generated catch block  
                    e.printStackTrace();  
                }  
            }  
            bufferedReader = null;  
        }  
        return "";  
	}

	public static void sendEmail(Context context, String subject, String text) {

		// 给someone@domain.com发邮件发送内容为“Hello”的邮件
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.putExtra(Intent.EXTRA_EMAIL,
				new String[] { "rayboot.work@gmail.com" });
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, text);
		intent.setType("text/plain");
		context.startActivity(intent);
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

	public static void sendCustomEvent(Context context, String key) {
		StatService.onEvent(context, key, "pass", 1);
		MobclickAgent.onEvent(context, key);
	}
}
