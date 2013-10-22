package com.rayboot.hanzitingxie.util;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.R.integer;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

import com.rayboot.hanzitingxie.obj.SourceData;

public class DataUtil {

	public static int YU_SU = 20;
	public static int YIN_DIAO = 50;
	public static int YIN_LIANG = 80;

	// 从html.txt里读取原信息
	// "http://baike.baidu.com/link?url=4ppxyv-jM3B5fZy8dHVNJl1mlZ0vPWN6bitUYH_liqUNvekTCq-7gaDH2ZcvjxYDoScgWEuraW7HE2HavmxEiq"

	public static void saveInitData(Context context) throws IOException {
		Document doc = Jsoup.parse(
				context.getResources().getAssets().open("html.txt"), "UTF-8",
				"http://example.com/");
		// 从 URL 直接加载 HTML 文档
		// Document doc = Jsoup.connect(url).get();
		Elements elements = doc.body().getElementsByAttributeValue("class",
				"table-view log-set-param");
		for (Element element : elements) {

			Elements trsElements = element.getElementsByTag("tr");
			for (Element tr : trsElements) {
				String href = "";
				String title = "";
				String pinyin = "";

				Elements tdsElements = tr.getElementsByTag("td");
				if (tdsElements.size() == 4) {
					for (int i = 0; i < tdsElements.size(); i++) {
						href = getAtagAttr(tdsElements.get(1), "a", "href")
								.trim();
						title = tdsElements.get(1).text().trim();
						title = title.replace("(注)", "");
						title = title.replace("（注）", "");
						if (title.contains("/")) {
							title = title.substring(0, title.indexOf("/"));
						}
						pinyin = tdsElements.get(2).text().trim();
					}
				} else if (tdsElements.size() == 5) {
					for (int i = 0; i < tdsElements.size(); i++) {
						href = getAtagAttr(tdsElements.get(2), "a", "href")
								.trim();
						title = tdsElements.get(2).text().trim();
						title = title.replace("(注)", "");
						title = title.replace("（注）", "");
						if (title.contains("/")) {
							title = title.substring(0, title.indexOf("/"));
						}
						pinyin = tdsElements.get(3).text().trim();
					}
				}
				if (!TextUtils.isEmpty(href) && !TextUtils.isEmpty(title)
						&& !TextUtils.isEmpty(pinyin)) {
					new SourceData(title, pinyin, href).save();
				}
			}
		}
	}

	public static String getAtagAttr(Element ele, String tag, String attr) {
		Elements someElements = ele.getElementsByTag(tag);
		if (someElements.size() > 0) {
			return someElements.get(0).attr(attr);
		}
		return "";
	}

	public static int getInfoFromShared(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(
				"hanzitingxie", Context.MODE_PRIVATE);
		return preferences.getInt(key, 0);
	}

	public static boolean setInfoToShared(Context context, String key,
			int value) {
		SharedPreferences preferences = context.getSharedPreferences(
				"hanzitingxie", Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putInt(key, value);
		editor.commit();
		return true;
	}
}
