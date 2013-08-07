package com.rayboot.weatherpk.obj;

import java.util.List;
import java.util.Random;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

@Table(name = "PKObjTable")
public class PKObj extends Model {
	@Column(name = "sore")
	public int sore;
	@Column(name = "other")
	public int other;

	public PKObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PKObj(int sore, int other) {
		super();
		// TODO Auto-generated constructor stub
		this.sore = sore;
		this.other = other;
	}

	public static void clearAll() {
		new Delete().from(PKObj.class).execute();
	}

	public static void autoAddRadomN(int n) {
		Random random = new Random();
		ActiveAndroid.beginTransaction();
		try {
			for (int i = 0; i < n; i++) {
				new PKObj(random.nextInt(845), 1).save();
			}
			ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}
	}

	public static PKObj getMyData() {
		return new Select().from(PKObj.class).where("other = ?", 0)
				.executeSingle();
	}

	public static List<PKObj> getMyIndex(int sore) {
		return new Select().from(PKObj.class).where("sore > ?", sore).execute();
	}

}
