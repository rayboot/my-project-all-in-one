package com.rayboot.letsball.model;

import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.text.TextUtils;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "MatchDetailInfos")
public class MatchDetailInfo extends Model {
	@Column(name = "urlKey")
	public String urlKey;

	// public ArrayList<MatchTimeLineObj> timeLineObjs = new
	// ArrayList<MatchTimeLineObj>();
	// public ArrayList<MatchStatisticObj> statisticObjs = new
	// ArrayList<MatchStatisticObj>();

	public MatchDetailInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MatchDetailInfo(String urlString, Document doc) {
		// TODO Auto-generated constructor stub
		this.urlKey = urlString;
		save();
		Element estart;
		Elements trsElements;
		// 构建时间轴
		estart = doc.body()
				.getElementsByAttributeValue("class", "ddtable sjtable").get(0);
		trsElements = estart.getElementsByTag("tr");

		ActiveAndroid.beginTransaction();
		try {
			for (Element element : trsElements) {
				Elements tdsElements = element.getElementsByTag("td");
				if (tdsElements.size() >= 3) {
					new MatchTimeLineObj(this, tdsElements).save();
				}
			}
			ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}

		// 比赛状态
		estart = doc.body()
				.getElementsByAttributeValue("class", "ddtable bstable").get(2);
		trsElements = estart.getElementsByTag("tr");
		ActiveAndroid.beginTransaction();
		try {
			for (Element element : trsElements) {
				Elements tdsElements = element.getElementsByTag("td");
				if (tdsElements.size() >= 3) {
					new MatchStatisticObj(this, tdsElements).save();
				}
			}
			ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}

	}

	public List<MatchTimeLineObj> getAllMatchTimeLineObjs() {
		return new Select().from(MatchTimeLineObj.class)
				.where("MatchDetailInfo = ?", getId()).execute();
	}

	public List<MatchStatisticObj> getAllMatchStatisticObjs() {
		return new Select().from(MatchStatisticObj.class)
				.where("MatchDetailInfo = ?", getId()).execute();
	}

	public static MatchDetailInfo getMatchDetailInfoFDB(String urlKey) {
		if (TextUtils.isEmpty(urlKey)) {
			return null;
		}
		return new Select().from(MatchDetailInfo.class)
				.where("urlKey = ?", urlKey).executeSingle();
	}
}
