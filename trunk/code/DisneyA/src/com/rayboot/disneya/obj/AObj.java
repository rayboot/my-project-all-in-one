package com.rayboot.disneya.obj;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.annotations.Expose;

@Table(name = "AObjs")
public class AObj extends Model {
	@Expose
	@Column(name = "levelid")
	public String levelid = "";
	@Expose
	@Column(name = "song_name")
	public String song_name = "";
	@Expose
	@Column(name = "file_name")
	public String file_name = "";
	@Expose
	@Column(name = "tips")
	public String tips = "";
	@Expose
	@Column(name = "confuse_word")
	public String confuse_word = "";

	public AObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static List<AObj> getAllData() {
		return new Select().from(AObj.class).execute();
	}

	public static List<AObj> getDataByRank(int start, int end) {
		return new Select().from(AObj.class)
				.where("Id >= ? AND Id <= ?", start, end).execute();
	}
}
