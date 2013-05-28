package com.rayboot.providentsearch;

import java.util.List;

import android.content.Context;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

@Table(name = "ProvidentObjs")
public class ProvidentObj extends Model {
	@Column(name = "idCard")
	public String idCard;
	@Column(name = "psd")
	public String psd;

	public ProvidentObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ProvidentObj(String idCard, String psd) {
		super();
		this.idCard = idCard;
		this.psd = psd;
	}

	public static List<ProvidentObj> getAllFromDB(Context context) {
		return new Select().from(ProvidentObj.class).execute();
	}

	public static ProvidentObj getFromDB(Context context, String key) {
		return new Select().from(ProvidentObj.class).where("idCard = ?", key)
				.executeSingle();
	}

	public static void deleteFromDB(Context context, String key) {
		new Delete().from(ProvidentObj.class).where("idCard = ?", key)
				.execute();
	}

	public static void deleteAllFromDB(Context context) {
		new Delete().from(ProvidentObj.class).execute();
	}
}
