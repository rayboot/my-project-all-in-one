package com.rayboot.letsball.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "RoundInfos")
public class RoundInfo extends Model implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "roundNum")
	public String roundNum;
	@Column(name = "shooterUrl")
	public String shooterUrl;
	@Column(name = "tableUrl")
	public String tableUrl;
	@Column(name = "isAllFinish")
	boolean isAllFinish;
	@Column(name = "roundUrl")
	public String roundUrl;
	@Column(name = "delayAndCancelCount")
	int delayAndCancelCount = 0;

	// @Column(name = "Name")
	// ArrayList<MatchInfo> matchInfos = new ArrayList<MatchInfo>();

	// public List<MatchInfo> matchInfos() {
	// return getMany(MatchInfo.class, "match");
	// }

	public RoundInfo() {
		super();
		// TODO Auto-generated constructor stub
	}

	public RoundInfo(String roundNum, String tableUrl, String shooterUrl,
			String roundUrl, ArrayList<MatchInfo> datas) {
		// TODO Auto-generated constructor stub
		this.roundNum = roundNum;
		this.tableUrl = tableUrl;
		this.shooterUrl = shooterUrl;
		this.roundUrl = roundUrl;
		isAllFinish = true;
		for (MatchInfo mi : datas) {
			if (mi.roundNum == Integer.valueOf(roundNum)) {
				// matchInfos().add(mi);
				if (mi.status == MatchInfo.WILL_START
						|| mi.status == MatchInfo.MATCHING) {
					isAllFinish = false;
				}
				if (mi.status == MatchInfo.MATCH_CANCEL
						|| mi.status == MatchInfo.MATCH_DELAY) {
					delayAndCancelCount++;
				}
			}
		}
	}

	public boolean isHaveMatch() {
		return getAllMatchInfos().size() > 0;
	}

	public boolean isFinished() {
		return isAllFinish;
	}

	public List<MatchInfo> getAllMatchInfos() {
		return new Select().from(MatchInfo.class)
				.where("RoundInfo = ?", getId()).execute();
	}

	public static RoundInfo getRoundInfoFDBWkey(String urlKey) {
		return new Select().from(RoundInfo.class).where("roundUrl = ?", urlKey)
				.executeSingle();
	}
}
