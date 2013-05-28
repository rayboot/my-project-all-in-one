package com.rayboot.utility.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.http.util.EncodingUtils;

import android.os.Environment;

import com.rayboot.letsball.LetsBallApp;

public class FileUtility {

	public static String getSDPath() {
		boolean sdCardExist = Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED); // 判断sd卡是否存在
		if (sdCardExist) {
			return Environment.getExternalStorageDirectory().toString();// 获取跟目录
		}
		return LetsBallApp.getAppInstant().getFilesDir().getAbsolutePath();
	}

	public static void isExistAndCreate(String path) {
		File file = new File(path);
		// 判断文件夹是否存在,如果不存在则创建文件夹
		if (!file.exists()) {
			file.mkdirs();
		}
	}

	public static String readSDFile(String filePath) {
		String res = "";
		try {
			FileInputStream fin = new FileInputStream(filePath);
			// FileInputStream fin = openFileInput(fileName);
			// 用这个就不行了，必须用FileInputStream
			int length = fin.available();
			byte[] buffer = new byte[length];
			fin.read(buffer);
			res = EncodingUtils.getString(buffer, "UTF-8");
			fin.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
	}

	public static boolean writeFile2SD(String filePath, String content) {
		try {
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			FileOutputStream fout = new FileOutputStream(filePath);
			fout.write(content.getBytes());
			fout.close();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
