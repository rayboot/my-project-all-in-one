package com.rayboot.ahjiaotong;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

@Table(name = "HistoryObjTable")
public class HistoryObj extends Model {
	@Column(name = "license")
	public String license;
	@Column(name = "frameNumber")
	public String frameNumber;
	@Column(name = "model")
	public String model;
	@Column(name = "modelName")
	public String modelName;

	public HistoryObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public HistoryObj(String license, String frameNumber, String model,
			String modelName) {
		super();
		this.license = license;
		this.frameNumber = frameNumber;
		this.model = model;
		this.modelName = modelName;
	}

	public static void removeData(String license) {
		new Delete().from(HistoryObj.class).where("license = ?", license)
				.execute();
	}

	public static List<HistoryObj> getAllData() {
		return new Select().from(HistoryObj.class).orderBy("Id DESC").execute();
	}

	public static void removeAllData() {
		new Delete().from(HistoryObj.class).execute();
	}
}
