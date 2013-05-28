package com.rayboot.letsball.model;

import org.jsoup.select.Elements;

import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.rayboot.letsball.R;
import com.rayboot.utility.JsoupUtility;

@Table(name = "MatchTimeLineObjs")
public class MatchTimeLineObj extends Model {
	public static final int STATUS_ERROR = 0;
	public static final int STATUS_ASSIST = 1;
	public static final int STATUS_GOAL = 2;
	public static final int STATUS_GOAL_PLUS = 3;
	public static final int STATUS_INJURY = 4;
	public static final int STATUS_OWN_GOAL = 5;
	public static final int STATUS_PENALTY = 6;
	public static final int STATUS_PENALTY_MISS = 7;
	public static final int STATUS_RED_CARD = 8;
	public static final int STATUS_SUB_IN = 9;
	public static final int STATUS_SUB_OUT = 10;
	public static final int STATUS_YELLOW_RED = 11;
	public static final int STATUS_YELLOW_CARD = 12;

	@Column(name = "time")
	public String time;
	@Column(name = "homePlayerUrl")
	public String homePlayerUrl;
	@Column(name = "homeStatus")
	public int homeStatus = 0;
	@Column(name = "awayPlayerUrl")
	public String awayPlayerUrl;
	@Column(name = "awayStatus")
	public int awayStatus = 0;
	@Column(name = "MatchDetailInfo")
	public MatchDetailInfo mdi;

	public MatchTimeLineObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MatchTimeLineObj(MatchDetailInfo mdi, Elements ele) {
		// TODO Auto-generated constructor stub
		this.mdi = mdi;
		String name;
		String classStatus;
		name = ele.get(0).text().trim().replaceAll("\\u00A0", "");
		this.homePlayerUrl = null;
		if (!TextUtils.isEmpty(name)) {
			this.homePlayerUrl = JsoupUtility.getAtagAttr(ele.get(0), "a",
					"href");
			if (PlayerInfo.getPlayerInfoFromDB(homePlayerUrl) == null) {
				new PlayerInfo(name, homePlayerUrl).save();
			}
			classStatus = JsoupUtility.getAtagAttr(ele.get(0), "span", "class");
			homeStatus = setStatus(classStatus);
		}

		this.time = ele.get(1).text().trim().replaceAll("\\u00A0", "");

		name = ele.get(2).text().trim().replaceAll("\\u00A0", "");
		this.awayPlayerUrl = null;
		if (!TextUtils.isEmpty(name)) {
			this.awayPlayerUrl = JsoupUtility.getAtagAttr(ele.get(2), "a",
					"href");
			if (PlayerInfo.getPlayerInfoFromDB(awayPlayerUrl) == null) {
				new PlayerInfo(name, awayPlayerUrl).save();
			}
			classStatus = JsoupUtility.getAtagAttr(ele.get(2), "span", "class");
			awayStatus = setStatus(classStatus);
		}
	}

	public int setStatus(String data) {
		if (data.equals("dq")) {
			return STATUS_PENALTY;
		} else if (data.equals("ypai")) {
			return STATUS_YELLOW_CARD;
		} else if (data.equals("huanjin")) {
			return STATUS_SUB_IN;
		} else if (data.equals("huanchu")) {
			return STATUS_SUB_OUT;
		} else if (data.equals("jq")) {
			return STATUS_GOAL;
		} else if (data.equals("rypai")) {
			return STATUS_YELLOW_RED;
		} else if (data.equals("redpai")) {
			return STATUS_RED_CARD;
		} else if (data.equals("wlq")) {
			return STATUS_OWN_GOAL;
		} else if (data.equals("dqfs")) { // 点球罚失
			return STATUS_PENALTY_MISS;
		} else if (data.equals("jinqiu")) {// 金球
			return STATUS_OWN_GOAL;
		} else if (data.equals("ss")) {// 受伤
			return STATUS_INJURY;
		} else if (data.equals("zg")) {// 助攻
			return STATUS_ASSIST;
		}
		return STATUS_ERROR;
	}

	public int getStatusRes(int status) {
		int res = 0;
		switch (status) {
		case STATUS_ASSIST:
			res = R.drawable.assist;
			break;
		case STATUS_GOAL:
			res = R.drawable.goal;
			break;
		case STATUS_GOAL_PLUS:
			res = R.drawable.goal_plus;
			break;
		case STATUS_INJURY:
			res = R.drawable.injury;
			break;
		case STATUS_OWN_GOAL:
			res = R.drawable.own_goal;
			break;
		case STATUS_PENALTY:
			res = R.drawable.penalty;
			break;
		case STATUS_PENALTY_MISS:
			res = R.drawable.penalty_miss;
			break;
		case STATUS_RED_CARD:
			res = R.drawable.red_card;
			break;
		case STATUS_SUB_IN:
			res = R.drawable.sub_in;
			break;
		case STATUS_SUB_OUT:
			res = R.drawable.sub_out;
			break;
		case STATUS_YELLOW_RED:
			res = R.drawable.yellowred;
			break;
		case STATUS_YELLOW_CARD:
			res = R.drawable.yellow_card;
			break;
		}
		return res;
	}
}
