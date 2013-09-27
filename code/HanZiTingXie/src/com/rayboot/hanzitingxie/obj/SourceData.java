package com.rayboot.hanzitingxie.obj;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

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

}
