package com.rayboot.utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.ParseException;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.content.Context;
import android.util.Log;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.rayboot.letsball.cache.SampleDataCache;
import com.rayboot.letsball.model.MatchDetailInfo;
import com.rayboot.letsball.model.MatchInfo;
import com.rayboot.letsball.model.PairRoundUrl;
import com.rayboot.letsball.model.RoundInfo;
import com.rayboot.letsball.model.ShooterInfo;
import com.rayboot.letsball.model.TableInfo;
import com.rayboot.letsball.model.TeamDetailInfo;

public class JsoupUtility {

	public static String getAtagAttr(Element ele, String tag, String attr) {
		Elements someElements = ele.getElementsByTag(tag);
		if (someElements.size() > 0) {
			return someElements.get(0).attr(attr);
		}
		return "";
	}

	public static List<PairRoundUrl> parseAllRoundUrl(Context context,
			String allUrlString) throws ParseException, IOException,
			IllegalArgumentException {
		String shooterUrl = null;
		String tableUrl = null;
		Element estart;
		Elements asElements;
		// Map<String, String> mapRoundUrl = new HashMap<String, String>();
		List<PairRoundUrl> pairsRoundUrl = new ArrayList<PairRoundUrl>();
		ArrayList<MatchInfo> matchInfos = new ArrayList<MatchInfo>();
		Document doc = Jsoup.parse(SampleDataCache.getSampleDataCacheInstant(
				context).getData(allUrlString, false));

		// 获取积分榜射手榜url
		estart = doc.body().getElementsByAttributeValue("class", "zxmenubox")
				.get(0);
		asElements = estart.getElementsByTag("a");
		for (Element element : asElements) {
			if ("积分榜".endsWith(element.text())) {
				tableUrl = element.attr("href").trim();
			} else if ("射手榜".endsWith(element.text())) {
				shooterUrl = element.attr("href").trim();
			}
		}
		asElements = null;

		// 获取轮数
		estart = doc.body().getElementsByAttributeValue("class", "sctable")
				.get(0);
		asElements = estart.getElementsByTag("a");
		for (Element element : asElements) {
			if (!StringUtil.isNumeric(element.text())) {// 过滤全部的连接
				continue;
			}
			pairsRoundUrl.add(new PairRoundUrl(element.text(), element.attr(
					"href").trim(), allUrlString.hashCode()));
		}

		// 获取轮次信息
		estart = doc.body().getElementsByTag("table").get(0);
		Elements trsElements = estart.getElementsByTag("tr");
		for (Element element : trsElements) {
			Elements tdsElements = element.getElementsByTag("td");
			if (tdsElements.size() == 0) {
				continue;
			}
			matchInfos.add(new MatchInfo(tdsElements));
		}

		// 设置轮次对象
		for (PairRoundUrl pair : pairsRoundUrl) {
			String key = pair.roundTitle;
			String urlString = pair.roundUrl;

			ActiveAndroid.beginTransaction();
			try {
				RoundInfo ri = new RoundInfo(key, tableUrl, shooterUrl,
						urlString, matchInfos);
				ri.save();

				for (MatchInfo mi : matchInfos) {
					if (mi.roundNum == Integer.valueOf(key)) {
						mi.setRoundInfo(ri);
						mi.save();
					}
				}

				if (ri.isHaveMatch()) {
					// DataUtility.setRoundInfo(urlString, ri);
					pair.setStartEndTime(ri);
				}

				// 存储联赛列表
				pair.save();
				ri = null;
				ActiveAndroid.setTransactionSuccessful();
			} finally {
				ActiveAndroid.endTransaction();
			}
		}

		matchInfos.clear();
		matchInfos = null;
		doc = null;
		System.gc();

		return pairsRoundUrl;
	}

	public static boolean parseRoundUrl(Context context, RoundInfo roundInfo,
			boolean isUseCache) throws ParseException, IOException,
			IllegalArgumentException {

		Document doc = Jsoup.parse(SampleDataCache.getSampleDataCacheInstant(
				context).getData(roundInfo.roundUrl, isUseCache));

		Element estart = doc.body().getElementsByTag("table").get(0);
		Elements trsElements = estart.getElementsByTag("tr");

		ActiveAndroid.beginTransaction();
		try {
			// 删除原有的过期数据
			new Delete().from(MatchInfo.class)
					.where("RoundInfo = ?", roundInfo.getId()).execute();
			for (Element element : trsElements) {
				Elements tdsElements = element.getElementsByTag("td");
				if (tdsElements.size() == 0) {
					continue;
				}
				MatchInfo mi = new MatchInfo(tdsElements);
				mi.setRoundInfo(roundInfo);
				mi.save();
			}
			ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}

		// 获取积分榜射手榜url
		estart = doc.body().getElementsByAttributeValue("class", "zxmenubox")
				.get(0);
		Elements asElements = estart.getElementsByTag("a");
		for (Element element : asElements) {
			if ("积分榜".endsWith(element.text())) {
				Global.CUR_TABLE_URL = Utility.getWholeUrl(element.attr("href")
						.trim());
			} else if ("射手榜".endsWith(element.text())) {
				Global.CUR_SHOOTER_URL = Utility.getWholeUrl(element.attr(
						"href").trim());
			}
		}

		doc = null;
		return true;
	}

	public static ArrayList<TableInfo> parseTableUrl(Context context,
			String urlString, boolean isUseCache) throws ParseException,
			IOException, IllegalArgumentException {

		ArrayList<TableInfo> tableInfos = new ArrayList<TableInfo>();
		Document doc = Jsoup.parse(SampleDataCache.getSampleDataCacheInstant(
				context).getData(urlString, isUseCache));
		Elements estartElements = doc.body().getElementsByTag("table");
		if (estartElements == null || estartElements.size() == 0) {
			return tableInfos;
		}
		Element estart = estartElements.get(0);
		Elements trsElements = estart.getElementsByTag("tr");
		for (Element element : trsElements) {
			if (!element.attr("class").contains("allev")) {
				continue;
			}
			Elements tdsElements = element.getElementsByTag("td");
			if (tdsElements.size() <= 1) {
				continue;
			}
			tableInfos.add(new TableInfo(tdsElements));
		}

		doc = null;
		return tableInfos;
	}

	public static ArrayList<ShooterInfo> parseShooterUrl(Context context,
			String urlString, boolean isUseCache) throws ParseException,
			IOException, IllegalArgumentException {
		urlString = Utility.getWholeUrl(urlString);

		ArrayList<ShooterInfo> shooterInfos = new ArrayList<ShooterInfo>();
		Document doc = Jsoup.parse(SampleDataCache.getSampleDataCacheInstant(
				context).getData(urlString, isUseCache));
		Elements estartElements = doc.body().getElementsByTag("table");
		if (estartElements == null || estartElements.size() == 0) {
			return shooterInfos;
		}
		Element estart = estartElements.get(0);
		Elements trsElements = estart.getElementsByTag("tr");
		Log.i("test----------------------", trsElements.size() + "");
		for (Element element : trsElements) {
			Elements tdsElements = element.getElementsByTag("td");
			if (tdsElements.size() <= 1) {
				continue;
			}
			shooterInfos.add(new ShooterInfo(tdsElements));
		}

		doc = null;
		return shooterInfos;
	}

	public static MatchDetailInfo parseMatchDetailUrl(Context context,
			String urlString, boolean isUseCache) throws ParseException,
			IOException, IllegalArgumentException {
		MatchDetailInfo mdi;
		urlString = Utility.getWholeUrl(urlString);
		Document doc = Jsoup.parse(SampleDataCache.getSampleDataCacheInstant(
				context).getData(urlString, isUseCache));
		mdi = new MatchDetailInfo(urlString, doc);
		doc = null;
		return mdi;
	}

	public static TeamDetailInfo parseTeamDetailInfoUrl(Context context,
			String urlString) throws ParseException, IOException,
			IllegalArgumentException {
		// TODO Auto-generated method stub
		TeamDetailInfo tdi;
		urlString = Utility.getWholeUrl(urlString);

		Document doc = Jsoup.parse(SampleDataCache.getSampleDataCacheInstant(
				context).getData(urlString, true));
		tdi = new TeamDetailInfo(doc);

		doc = null;
		return tdi;
	}

	public static ArrayList<MatchInfo> parseTeamMatchFutureHistoryUrl(
			Context context, String urlString, String status)
			throws ParseException, IOException, IllegalArgumentException {
		// TODO Auto-generated method stub
		urlString = Utility.getWholeUrl(urlString);

		ArrayList<MatchInfo> matchInfos = new ArrayList<MatchInfo>();
		Document doc = Jsoup.parse(SampleDataCache.getSampleDataCacheInstant(
				context).getData(urlString, true));
		Element estart = doc.body().getElementsByTag("table").get(0);
		Elements trsElements = estart.getElementsByTag("tr");
		Log.i("test----------------------", trsElements.size() + "");
		for (Element element : trsElements) {
			Elements tdsElements = element.getElementsByTag("td");
			if (tdsElements.size() == 0) {
				continue;
			}
			matchInfos.add(new MatchInfo(tdsElements, status));
		}
		doc = null;
		return matchInfos;
	}

	public static ArrayList<MatchInfo> parseTeamMatchNextUrl(Context context,
			String urlString, String status) throws ParseException,
			IOException, IllegalArgumentException {
		// TODO Auto-generated method stub
		urlString = Utility.getWholeUrl(urlString);

		ArrayList<MatchInfo> matchInfos = new ArrayList<MatchInfo>();
		Document doc = Jsoup.parse(SampleDataCache.getSampleDataCacheInstant(
				context).getData(urlString, true));
		Element estart = doc.body().getElementsByTag("table").get(1);
		Elements trsElements = estart.getElementsByTag("tr");
		for (Element element : trsElements) {
			Elements tdsElements = element.getElementsByTag("td");
			if (tdsElements.size() == 0) {
				continue;
			}
			matchInfos.add(new MatchInfo(tdsElements, status));
		}
		doc = null;
		return matchInfos;
	}

}
