package com.rayboot.letsball.model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "TeamMapInfos")
public class TeamMapInfo extends Model {
	@Column(name = "nameKey")
	public String nameKey;
	@Column(name = "nameEx")
	public String nameEx;
	@Column(name = "logoFile")
	public String logoFile;

	public TeamMapInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeamMapInfo(String nameKey, String nameEx, String logoFile) {
		super();
		this.nameKey = nameKey;
		this.nameEx = nameEx;
		this.logoFile = logoFile;
	}

	public static TeamMapInfo getTeamMapInfoFromDB(String name) {
		return new Select().from(TeamMapInfo.class).where("nameKey = ?", name)
				.executeSingle();
	}

	public static TeamMapInfo getTeamMapInfoFromDB() {
		return new Select().from(TeamMapInfo.class).executeSingle();
	}
}
