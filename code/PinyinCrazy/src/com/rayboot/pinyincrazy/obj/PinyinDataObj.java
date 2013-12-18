package com.rayboot.pinyincrazy.obj;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "PinyinDataTable")
public class PinyinDataObj extends Model {
	@Column(name = "title")
	public String title;
	@Column(name = "pinyin")
	public String pinyin;
	@Column(name = "url")
	public String url;
	@Column(name = "wrong")
	public int wrong = 0;
	@Column(name = "isRight")
	public int isRight = 0;
	@Column(name = "level")
	public int level = 0;
	@Column(name = "wordtype")
	public String wordtype;
	@Column(name = "keytone")
	public int keytone;
	@Column(name = "keypinyin")
	public String keypinyin;
	@Column(name = "keychar")
	public String keychar;

	public PinyinDataObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static PinyinDataObj getRandomData() {
		return new Select().from(PinyinDataObj.class).orderBy("RANDOM()")
				.executeSingle();
	}

	public static PinyinDataObj getChuangGuanRandomData() {
		return new Select().from(PinyinDataObj.class).where("isRight = ?", 0)
				.orderBy("RANDOM()").executeSingle();
	}

	public static List<PinyinDataObj> getAllWrongDatas() {
		return new Select().from(PinyinDataObj.class).where("wrong > ?", 0)
				.orderBy("wrong DESC").execute();
	}

	public static List<PinyinDataObj> getAllRightDatas() {
		return new Select().from(PinyinDataObj.class).where("isRight = ?", 1)
				.execute();
	}

	public static List<PinyinDataObj> getAllDatas() {
		return new Select().from(PinyinDataObj.class).orderBy("wrong DESC")
				.execute();
	}
}
