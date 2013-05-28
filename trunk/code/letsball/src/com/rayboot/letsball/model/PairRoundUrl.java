package com.rayboot.letsball.model;

import java.io.Serializable;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "PairRoundUrls")
public class PairRoundUrl extends Model implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Column(name = "roundTitle")
	public String roundTitle;
	@Column(name = "roundUrl")
	public String roundUrl;
	@Column(name = "startTime")
	public long startTime = Long.MAX_VALUE;
	@Column(name = "endTime")
	public long endTime = 0;
	@Column(name = "allUrlKey")
	public int allUrlKey = 0;

	public PairRoundUrl() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PairRoundUrl(String roundTitle, String roundUrl, int allUrlKey) {
		super();
		this.roundTitle = roundTitle;
		this.roundUrl = roundUrl;
		this.allUrlKey = allUrlKey;
	}

	public void setStartEndTime(RoundInfo ri) {
		List<MatchInfo> allMatchInfos = ri.getAllMatchInfos();
		for (int i = 0; i < allMatchInfos.size() - ri.delayAndCancelCount; i++) {
			if (allMatchInfos.get(i).time < startTime) {
				startTime = allMatchInfos.get(i).time;
			}
			if (allMatchInfos.get(i).time > endTime) {
				endTime = allMatchInfos.get(i).time;
			}
		}
	}

	public static List<PairRoundUrl> getAllPairRoundUrlFromDB(int allKey) {
		return new Select().from(PairRoundUrl.class)
				.where("allUrlKey = ?", allKey).execute();
	}

}
