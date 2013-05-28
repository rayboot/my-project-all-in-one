package com.rayboot.letsball.model;

import org.jsoup.select.Elements;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "MatchStatisticObjs")
public class MatchStatisticObj extends Model {
	@Column(name = "home")
	public String home;
	@Column(name = "name")
	public String name;
	@Column(name = "away")
	public String away;
	@Column(name = "MatchDetailInfo")
	public MatchDetailInfo mdi;

	public MatchStatisticObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MatchStatisticObj(MatchDetailInfo mdi, Elements ele) {
		super();
		// TODO Auto-generated constructor stub
		this.mdi = mdi;
		this.home = ele.get(0).text().trim().replaceAll("\\u00A0", "");
		this.name = ele.get(1).text().trim().replaceAll("\\u00A0", "");
		this.away = ele.get(2).text().trim().replaceAll("\\u00A0", "");
	}

}
