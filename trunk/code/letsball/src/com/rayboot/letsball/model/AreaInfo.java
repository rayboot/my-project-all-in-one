package com.rayboot.letsball.model;

import java.util.List;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.rayboot.letsball.R;
import com.rayboot.utility.Utility;

@Table(name = "AreaInfos")
public class AreaInfo extends Model {
	final public static int TYPE_ITALY = 1;
	final public static int TYPE_ENGLAND = 2;
	final public static int TYPE_SPAIN = 3;
	final public static int TYPE_GERMANY = 4;

	public final static int[] TYPE_AREA_ARRAY = { TYPE_ITALY, TYPE_ENGLAND,
			TYPE_SPAIN, TYPE_GERMANY };

	final public static int TYPE_LEAGUE = 1;
	final public static int TYPE_CUP = 2;
	@Column(name = "areaId")
	public int areaId;
	@Column(name = "name")
	public String name;
	@Column(name = "colorValue")
	public int bgColor;
	@Column(name = "leagueInfoFileName")
	public String leagueInfoFileName;
	@Column(name = "cupInfoFileName")
	public String cupInfoFileName;
	@Column(name = "flagFile")
	public String flagFile;

	public AreaInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AreaInfo(Context context, int areaId) {
		super();
		this.areaId = areaId;
		switch (areaId) {
		case TYPE_ITALY:
			name = "意大利";
			bgColor = context.getResources().getColor(R.color.ita_bg);
			leagueInfoFileName = "ita_league_round_list";
			cupInfoFileName = "ita_cup_round_list";
			flagFile = "assets://Flag_of_Italy.png";

			if (getLeagueInfo(leagueInfoFileName) == null) {
				new LeagueTypeInfo(areaId * 10 + TYPE_LEAGUE, "意甲",
						"assets://m_seriea.jpg", leagueInfoFileName).save();
			}
			if (getLeagueInfo(cupInfoFileName) == null) {
				new LeagueTypeInfo(areaId * 10 + TYPE_CUP, "意大利杯",
						"assets://m_coppa.jpg", cupInfoFileName).save();
			}
			break;
		case TYPE_ENGLAND:
			name = "英格兰";
			bgColor = context.getResources().getColor(R.color.eng_bg);
			leagueInfoFileName = "eng_league_round_list";
			cupInfoFileName = "eng_cup_round_list";
			flagFile = "assets://Flag_of_England.png";

			if (getLeagueInfo(leagueInfoFileName) == null) {
				new LeagueTypeInfo(areaId * 10 + TYPE_LEAGUE, "英超",
						"assets://m_epl.jpg", leagueInfoFileName).save();
			}
			if (getLeagueInfo(cupInfoFileName) == null) {
				new LeagueTypeInfo(areaId * 10 + TYPE_CUP, "足总杯",
						"assets://m_facup.jpg", cupInfoFileName).save();
			}
			break;
		case TYPE_SPAIN:
			name = "西班牙";
			bgColor = context.getResources().getColor(R.color.span_bg);
			leagueInfoFileName = "span_league_round_list";
			cupInfoFileName = "span_cup_round_list";
			flagFile = "assets://Flag_of_Spain.png";

			if (getLeagueInfo(leagueInfoFileName) == null) {
				new LeagueTypeInfo(areaId * 10 + TYPE_LEAGUE, "西甲",
						"assets://m_liga.jpg", leagueInfoFileName).save();
			}
			if (getLeagueInfo(cupInfoFileName) == null) {
				new LeagueTypeInfo(areaId * 10 + TYPE_CUP, "国王杯",
						"assets://m_delrey.jpg", cupInfoFileName).save();
			}
			break;
		case TYPE_GERMANY:
			name = "德国";
			bgColor = context.getResources().getColor(R.color.de_bg);
			leagueInfoFileName = "de_league_round_list";
			cupInfoFileName = "de_cup_round_list";
			flagFile = "assets://Flag_of_Germany.png";

			if (getLeagueInfo(leagueInfoFileName) == null) {
				new LeagueTypeInfo(areaId * 10 + TYPE_LEAGUE, "德甲",
						"assets://m_bund.jpg", leagueInfoFileName).save();
			}
			if (getLeagueInfo(cupInfoFileName) == null) {
				new LeagueTypeInfo(areaId * 10 + TYPE_CUP, "德国足协杯",
						"assets://m_dfbp.jpg", cupInfoFileName).save();
			}
			break;
		}
	}

	public LeagueTypeInfo getLeagueTypeInfo(boolean isLeague) {
		return isLeague ? getLeagueInfo(leagueInfoFileName)
				: getLeagueInfo(cupInfoFileName);
	}

	public LeagueTypeInfo getLeagueInfo(String fileName) {
		return new Select()
				.from(LeagueTypeInfo.class)
				.where("allUrlKey = ?",
						Utility.getAllMatchUrl(fileName).hashCode())
				.executeSingle();
	}

	public static AreaInfo getAreaInfoFromDB(int aId) {
		return new Select().from(AreaInfo.class).where("areaId = ?", aId)
				.executeSingle();
	}

	public static List<AreaInfo> getAllAreaInfoFromDB() {
		return new Select().from(AreaInfo.class).execute();
	}

}
