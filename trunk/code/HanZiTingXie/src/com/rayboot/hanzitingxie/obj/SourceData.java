package com.rayboot.hanzitingxie.obj;

import java.util.List;

import android.util.Log;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

@Table(name = "SourceDataTable")
public class SourceData extends Model {
	@Column(name = "title")
	public String title;
	@Column(name = "pinyin")
	public String pinyin;
	@Column(name = "url")
	public String url;
	@Column(name = "right")
	public int right = 0;
	@Column(name = "wrong")
	public int wrong = 0;
	@Column(name = "isRight")
	public int isRight = 0;

	public SourceData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SourceData(String title, String pinyin, String url) {
		super();
		this.title = title;
		this.pinyin = pinyin;
		this.url = url;
	}

	public static SourceData getRandomData() {
		return new Select().from(SourceData.class).orderBy("RANDOM()")
				.executeSingle();
	}

	public static SourceData getChuangGuanRandomData() {
		return new Select().from(SourceData.class).where("isRight = ?", 0)
				.orderBy("RANDOM()").executeSingle();
	}

	public static List<SourceData> getAllWrongDatas() {
		return new Select().from(SourceData.class).where("wrong > ?", 0)
				.orderBy("wrong DESC").execute();
	}

	public static void updateItem(SourceData sData) {
		sData.save();
	}
}
