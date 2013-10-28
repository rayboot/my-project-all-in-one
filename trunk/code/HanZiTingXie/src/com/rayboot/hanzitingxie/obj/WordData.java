package com.rayboot.hanzitingxie.obj;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.annotations.SerializedName;

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

	public WordData(String title, String pinyin, String url, int isRight, int wrong) {
		super();
		this.title = title;
		this.pinyin = pinyin;
		this.url = url;
		this.isRight = isRight;
		this.wrong = wrong;
	}

	public static WordData getRandomData() {
		return new Select().from(WordData.class).orderBy("RANDOM()")
				.executeSingle();
	}

	public static WordData getChuangGuanRandomData() {
		return new Select().from(WordData.class).where("isRight = ?", 0)
				.orderBy("RANDOM()").executeSingle();
	}

	public static List<WordData> getAllWrongDatas() {
		return new Select().from(WordData.class).where("wrong > ?", 0)
				.orderBy("wrong DESC").execute();
	}

	public static List<WordData> getAllDatas() {
		return new Select().from(WordData.class).orderBy("wrong DESC")
				.execute();
	}

	public static List<WordData> getAllRightDatas() {
		return new Select().from(WordData.class).where("isRight = ?", 1)
				.execute();
	}

	public static void updateItem(WordData sData) {
		sData.save();
	}

	public static class WordDataTypeAdapter implements
			JsonDeserializer<List<WordData>> {
		public List<WordData> deserialize(JsonElement json, Type typeOfT,
				JsonDeserializationContext ctx) {
			List<WordData> vals = new ArrayList<WordData>();
			if (json.isJsonArray()) {
				for (JsonElement e : json.getAsJsonArray()) {
					vals.add((WordData) ctx.deserialize(e, WordData.class));
				}
			} else if (json.isJsonObject()) {
				vals.add((WordData) ctx.deserialize(json, WordData.class));
			} else {
				throw new RuntimeException("Unexpected JSON type: "
						+ json.getClass());
			}
			return vals;
		}
	}
}
