package com.rayboot.letsball.model;

import java.io.Serializable;

import org.jsoup.select.Elements;

import android.text.TextUtils;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.rayboot.utility.JsoupUtility;

@Table(name = "PlayerInfos")
public class PlayerInfo extends Model implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "name")
	public String name;
	@Column(name = "detailUrl")
	public String detailUrl;
	@Column(name = "num")
	public String num;
	@Column(name = "pos")
	public String pos;
	@Column(name = "onData")
	public String on;
	@Column(name = "firstOn")
	public String firstOn;
	@Column(name = "assistOn")
	public String assistOn;
	@Column(name = "timeOn")
	public String timeOn;
	@Column(name = "goal")
	public String goal;
	@Column(name = "assist")
	public String assist;
	@Column(name = "yCard")
	public String yCard;
	@Column(name = "rCard")
	public String rCard;

	public PlayerInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlayerInfo(String name, String detailUrl) {
		super();
		this.name = name.trim().replaceAll("\\u00A0", "");
		this.detailUrl = detailUrl;
	}

	public PlayerInfo(Elements eles) {
		// TODO Auto-generated constructor stub

		this.num = eles.get(0).text().trim();
		this.name = eles.get(1).text().trim();

		this.detailUrl = JsoupUtility.getAtagAttr(eles.get(1), "a", "href")
				.trim();
		this.pos = eles.get(2).text().trim();
		this.on = eles.get(3).text().trim();
		this.firstOn = eles.get(4).text().trim();
		this.assistOn = eles.get(5).text().trim();
		this.timeOn = eles.get(6).text().trim();
		this.goal = eles.get(7).text().trim();
		this.assist = eles.get(8).text().trim();
		this.yCard = eles.get(9).text().trim();
		this.rCard = eles.get(10).text().trim();
	}

	public static PlayerInfo getPlayerInfoFromDB(String urlKey) {
		if (TextUtils.isEmpty(urlKey)) {
			return null;
		}
		return new Select().from(PlayerInfo.class)
				.where("detailUrl = ?", urlKey).executeSingle();
	}
}
