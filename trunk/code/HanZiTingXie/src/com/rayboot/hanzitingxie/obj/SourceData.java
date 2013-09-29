package com.rayboot.hanzitingxie.obj;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "SourceDataTable")
public class SourceData extends Model {
	@Column(name = "title")
	public String title;
	@Column(name = "pinyin")
	public String pinyin;
	@Column(name = "url")
	public String url;

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

}
