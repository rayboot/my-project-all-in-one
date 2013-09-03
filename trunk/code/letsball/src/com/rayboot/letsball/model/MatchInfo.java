package com.rayboot.letsball.model;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import org.jsoup.select.Elements;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.rayboot.utility.JsoupUtility;

@Table(name = "MatchInfos")
public class MatchInfo extends Model implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int WILL_START = 0;
	public static final int MATCHING = 1;
	public static final int MATCH_END = 2;
	public static final int MATCH_DELAY = 3;
	public static final int MATCH_CANCEL = 4;

	@Column(name = "areaLeague")
	public String areaLeague;
	@Column(name = "leagueYear")
	public String leagueYear = "13/14";
	@Column(name = "roundNum")
	public int roundNum;
	@Column(name = "startTime")
	public String startTime;
	@Column(name = "time")
	public long time = 0;
	@Column(name = "status")
	public int status = WILL_START;
	@Column(name = "home")
	public String homeUrl;
	@Column(name = "score")
	public String score;
	@Column(name = "detailUrl")
	public String detailUrl;
	@Column(name = "away")
	public String awayUrl;

	@Column(name = "RoundInfo")
	RoundInfo roundInfo;

	public MatchInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MatchInfo(Elements eles, String status) {
		// TODO Auto-generated constructor stub
		this.areaLeague = eles.get(0).text();
		this.startTime = setStartTime(eles.get(1).text().trim());

		this.roundNum = Integer.valueOf(eles.get(2).text().trim());
		this.homeUrl = JsoupUtility.getAtagAttr(eles.get(3), "a", "href")
				.trim();
		if (getTeamInfo(homeUrl) == null) {
			new TeamInfo(eles.get(3).text().trim(), homeUrl).save();
		}
		this.score = eles.get(4).text().trim();
		this.detailUrl = JsoupUtility.getAtagAttr(eles.get(4), "a", "href")
				.trim();
		this.status = getStatusCode(status);
		this.awayUrl = JsoupUtility.getAtagAttr(eles.get(5), "a", "href")
				.trim();
		if (getTeamInfo(awayUrl) == null) {
			new TeamInfo(eles.get(5).text().trim(), awayUrl).save();
		}
	}

	public MatchInfo(Elements eles) {
		// TODO Auto-generated constructor stub
		this.roundNum = Integer.valueOf(eles.get(0).text().trim());
		this.startTime = setStartTime(eles.get(1).text());
		this.status = getStatusCode(eles.get(2).text().trim());
		this.homeUrl = JsoupUtility.getAtagAttr(eles.get(3), "a", "href")
				.trim();

		if (getTeamInfo(homeUrl) == null) {
			new TeamInfo(eles.get(3).text().trim(), homeUrl).save();
		}
		this.score = eles.get(4).text().trim();
		this.detailUrl = JsoupUtility.getAtagAttr(eles.get(4), "a", "href")
				.trim();

		this.awayUrl = JsoupUtility.getAtagAttr(eles.get(5), "a", "href")
				.trim();
		if (getTeamInfo(awayUrl) == null) {
			new TeamInfo(eles.get(5).text().trim(), awayUrl).save();
		}
	}

	public String setStartTime(String data) {
		String timeString;
		if (leagueYear.contains("/")) {
			String mouth = data.split("-")[0];
			if (Integer.valueOf(mouth) > 7) {
				timeString = "20" + leagueYear.split("/")[0] + "-" + data;
			} else {
				timeString = "20" + leagueYear.split("/")[1] + "-" + data;
			}
		} else {
			timeString = leagueYear + "-" + data;
		}
		try {
			this.time = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(
					timeString).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return timeString;
	}

	public String getStatusString() {
		switch (status) {
		case MATCH_END:
			return "已结束";
		case WILL_START:
			return "未开始";
		case MATCH_DELAY:
			return "延期";
		case MATCH_CANCEL:
			return "取消";
		default:
			return "正在进行";
		}
	}

	public int getStatusCode(String status) {
		if (status.equals("已结束")) {
			return MATCH_END;
		} else if (status.trim().equals("未开始")) {
			return WILL_START;
		} else if (status.trim().equals("延期")) {
			return MATCH_DELAY;
		} else if (status.trim().equals("取消")) {
			return MATCH_CANCEL;
		} else {
			return MATCHING;
		}
	}

	public void setRoundInfo(RoundInfo roundInfo) {
		this.roundInfo = roundInfo;
	}

	public TeamInfo getTeamInfo(String teamUrl) {
		return new Select().from(TeamInfo.class)
				.where("detailUrl = ?", teamUrl).executeSingle();
	}

	public TeamInfo getHomeTeamInfo() {
		return getTeamInfo(homeUrl);
	}

	public TeamInfo getAwayTeamInfo() {
		return getTeamInfo(awayUrl);
	}

	public static List<MatchInfo> getAllMatchInfos(long roundId) {
		return new Select().from(MatchInfo.class)
				.where("RoundInfo = ?", roundId).orderBy("time ASC").execute();
	}

}
