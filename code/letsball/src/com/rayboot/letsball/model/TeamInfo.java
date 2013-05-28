package com.rayboot.letsball.model;

import java.io.Serializable;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "TeamInfos")
public class TeamInfo extends Model implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "teamName")
	public String teamName;
	@Column(name = "detailUrl")
	public String detailUrl;
	@Column(name = "teamNameEx")
	String teamNameEx;
	@Column(name = "logoName")
	public String logoName;

	public TeamInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TeamInfo(String name, String detailUrl) {
		super();

		this.teamName = name.trim().replaceAll("\\u00A0", "");
		this.detailUrl = detailUrl;
		setTeamMapInfo();
	}

	private void setTeamMapInfo() {
		// TODO Auto-generated method stub
		TeamMapInfo tmi = TeamMapInfo.getTeamMapInfoFromDB(teamName.trim());
		if (tmi == null) {
			this.teamNameEx = "unknow";
			this.logoName = "assets://default_60.png";
		} else {
			this.teamNameEx = tmi.nameEx;
			this.logoName = tmi.logoFile;
		}
	}

	public static TeamInfo getTeamInfoFromDB(String url) {
		return new Select().from(TeamInfo.class).where("detailUrl = ?", url)
				.executeSingle();
	}

}
