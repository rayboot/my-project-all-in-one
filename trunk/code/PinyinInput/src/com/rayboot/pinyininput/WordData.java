package com.rayboot.pinyininput;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "WordDataTable")
public class WordData extends Model {
	@Column(name = "title", unique = true, onUniqueConflict = Column.ConflictAction.IGNORE)
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

	public WordData() {
		super();
		// TODO Auto-generated constructor stub
	}

	public WordData(String title, String pinyin, String url) {
		super();
		this.title = title;
		this.pinyin = pinyin;
		this.url = url;
	}

	public WordData(String title, String pinyin, String url, int isRight,
			int wrong) {
		super();
		this.title = title;
		this.pinyin = pinyin;
		this.url = url;
		this.isRight = isRight;
		this.wrong = wrong;
	}

	public static List<WordData> getAllDatas() {
		return new Select().from(WordData.class).orderBy("wrong DESC")
				.execute();
	}

	public static WordData getOneData() {
		return new Select().from(WordData.class).executeSingle();
	}

	public static List<WordData> getAllRightDatas() {
		return new Select().from(WordData.class).where("isRight = ?", 1)
				.execute();
	}

	public static void updateItem(WordData sData) {
		sData.save();
	}

}
