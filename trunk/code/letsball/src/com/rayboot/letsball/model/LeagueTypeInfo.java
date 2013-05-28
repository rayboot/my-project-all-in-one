package com.rayboot.letsball.model;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.rayboot.utility.Utility;

@Table(name = "LeagueTypeInfos")
public class LeagueTypeInfo extends Model {
	public final static String SP_NAME = "leaguage_type_info";
	@Column(name = "name")
	public String name;
	@Column(name = "logoFile")
	public String logoFile;
	@Column(name = "allUrl")
	public String allUrl;
	@Column(name = "fileName")
	public String fileName;
	@Column(name = "leagueId")
	public int leagueId;
	@Column(name = "allUrlKey")
	public int allUrlKey;

	public LeagueTypeInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LeagueTypeInfo(int leagueId, String name, String logoFile,
			String fileName) {
		super();
		this.leagueId = leagueId;
		this.name = name;
		this.logoFile = logoFile;
		this.fileName = fileName;
		this.allUrl = getAllUrlInfoFromFile(fileName);
		this.allUrlKey = allUrl.hashCode();
	}

	private String getAllUrlInfoFromFile(String fileName) {
		return Utility.getAllMatchUrl(fileName);
	}

	public List<PairRoundUrl> getMatchUrls() {
		return new Select().from(PairRoundUrl.class)
				.where("allUrlKey = ?", allUrl.hashCode()).execute();
	}
}
