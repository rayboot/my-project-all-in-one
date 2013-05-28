package com.rayboot.letsball.model;

import org.jsoup.select.Elements;

import com.rayboot.utility.JsoupUtility;

public class ShooterInfo {
	public PlayerInfo player;
	public String index;
	public String shoot;
	public TeamInfo team;

	public ShooterInfo(Elements eles) {
		// TODO Auto-generated constructor stub
		this.index = eles.get(0).text().trim();
		this.player = new PlayerInfo(eles.get(1).text().trim(), JsoupUtility
				.getAtagAttr(eles.get(1), "a", "href").trim());

		this.team = new TeamInfo(eles.get(2).text().trim(), JsoupUtility
				.getAtagAttr(eles.get(2), "a", "href").trim());
		this.shoot = eles.get(3).text().trim();
	}
}
