package com.rayboot.hanzitingxie.util;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class DataUtil {

	public static void saveInitData(String url) throws IOException {
		// 从 URL 直接加载 HTML 文档
		Document doc = Jsoup.connect(url).get();
		Elements elements = doc.body().getElementsByAttributeValue("class",
				"table-view log-set-param");
		for (Element element : elements) {

			Elements trsElements = element.getElementsByTag("tr");
			for (Element tr : trsElements) {

				Elements tdsElements = tr.getElementsByTag("td");
				if (tdsElements.size() == 4) {
					for (int i = 0; i < tdsElements.size(); i++) {
						JsoupUtility.getAtagAttr(tdsElements.get(1), "a", "href")
						.trim();
						if (i == 1) {
							
						}else if (i == 2) {
							
						}
					}
				}else if (tdsElements.size() == 5) {
					for (int i = 0; i < tdsElements.size(); i++) {
						if (i == 2) {
							
						}else if (i == 3) {
							
						}
					}
				}
			}
		}
	}
}
