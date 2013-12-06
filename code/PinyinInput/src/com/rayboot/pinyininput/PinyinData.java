package com.rayboot.pinyininput;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "PinyinDataTable")
public class PinyinData extends Model {
	@Column(name = "keychar")
	public String keychar;
	@Column(name = "keypinyin")
	public String keypinyin;
	@Column(name = "keytone")
	public int keytone;
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

	public PinyinData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PinyinData(String keychar, String keypinyin, int keytone,
			String title, String pinyin, String url, int wrong, int isRight,
			int level, String wordtype) {
		super();
		this.keychar = keychar;
		this.keypinyin = keypinyin;
		this.keytone = keytone;
		this.title = title;
		this.pinyin = pinyin;
		this.url = url;
		this.wrong = wrong;
		this.isRight = isRight;
		this.level = level;
		this.wordtype = wordtype;
	}


	public static int getAllDatasCount() {
		return new Select().from(PinyinData.class).execute().size();
	}
}
