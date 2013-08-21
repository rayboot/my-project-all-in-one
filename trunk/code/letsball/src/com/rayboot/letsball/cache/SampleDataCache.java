package com.rayboot.letsball.cache;

import java.io.File;
import java.io.IOException;

import org.apache.http.ParseException;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.rayboot.utility.HttpUtility;
import com.rayboot.utility.Utility;
import com.rayboot.utility.file.FileUtility;

public class SampleDataCache {

	private final String CACHE_FOLDER = "LETSCACHE";
	Context mContext;
	public static String data = "";
	private static SampleDataCache mInstant = null;

	public SampleDataCache(Context context) {
		super();
		mInstant = this;
		this.mContext = context;
	}

	public static SampleDataCache getSampleDataCacheInstant(Context context) {
		if (mInstant == null) {
			mInstant = new SampleDataCache(context);
		}
		return mInstant;
	}

	public String getData(String urlString, boolean isUseCache)
			throws ParseException, IOException {
		if (!urlString.contains("http://")) {
			urlString = Utility.getWholeUrl(urlString);
		}
		File cacheFile = new File(getCacheFilePath(urlString));
		String cacheData = null;
		if (isUseCache && cacheFile.exists()) {
			// 有缓存数据
			cacheData = FileUtility.readSDFile(cacheFile.getAbsolutePath());
		}
		if (TextUtils.isEmpty(cacheData)
				&& HttpUtility.isNetworkAvailable(mContext)) {
			// 没有缓存数据
			cacheData = HttpUtility.executeHttpGet(urlString);
			if (TextUtils.isEmpty(cacheData)) {
				Log.e("警告", "数据返回有误。" + urlString);
				return null;
			}
			FileUtility.writeFile2SD(cacheFile.getAbsolutePath(), cacheData);
		}

		return cacheData;
	}

	public String getCachePath() {
		String sdPath = FileUtility.getSDPath();
		if (sdPath == null) {
			return null;
		}
		String cachePath = sdPath + "/" + CACHE_FOLDER + "/";
		FileUtility.isExistAndCreate(cachePath);
		return cachePath;
	}

	public String getCacheFilePath(String urlString) {
		if (TextUtils.isEmpty(getCachePath())) {
			return null;
		}
		return getCachePath() + getCacheFileName(urlString);
	}

	public String getCacheFileName(String urlString) {
		return urlString.hashCode() + ".data";
	}

	public boolean clearCache() {
		return deleteFile(new File(getCachePath()));
	}

	public boolean deleteFile(File file) {

		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete(); // delete()方法 你应该知道 是删除的意思;
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					this.deleteFile(files[i]); // 把每个文件 用这个方法进行迭代
				}
			}
			file.delete();
		}
		return true;
	}
}
