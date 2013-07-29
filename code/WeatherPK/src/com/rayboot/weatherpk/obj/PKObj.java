package com.rayboot.weatherpk.obj;

import java.util.Random;

import android.R.integer;
import android.content.ClipData.Item;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;

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
				new PKObj(random.nextInt(50), 1).save();
			}
			ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}
	}

}
