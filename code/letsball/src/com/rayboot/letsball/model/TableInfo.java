package com.rayboot.letsball.model;

import org.jsoup.select.Elements;

import com.rayboot.utility.JsoupUtility;

public class TableInfo {
	public String index;
	public TeamInfo team;
	public String roundNum;
	public String win;
	public String draw;
	public String loss;
	public String winBall;
	public String lossBall;
	public String getBall;
	public String sorce;
	public String desc;

	public TableInfo(Elements eles) {
		// TODO Auto-generated constructor stub
		this.index = eles.get(0).text().trim();
		this.team = new TeamInfo(eles.get(1).text().trim(), JsoupUtility
				.getAtagAttr(eles.get(1), "a", "href").trim());
		this.roundNum = eles.get(2).text().trim();
		this.win = eles.get(3).text().trim();
		this.draw = eles.get(4).text().trim();
		this.loss = eles.get(5).text().trim();
		this.winBall = eles.get(6).text().trim();
		this.lossBall = eles.get(7).text().trim();
		this.getBall = eles.get(8).text().trim();
		this.sorce = eles.get(9).text().trim();
		this.desc = eles.get(10).text().trim();
	}
}
