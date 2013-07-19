package com.rayboot.weatherpk.obj;

import java.util.List;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

@Table(name = "CityObjTable")
public class CityObj extends Model {
	@Column(name = "cityname")
	public String cityname;
	@Column(name = "citycode")
	public String citycode;
	@Column(name = "province")
	public String province;

	public CityObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static CityObj getCityObjFromeName(String name) {
		String whereString = "cityname like '%" + name + "%'";

		return new Select().from(CityObj.class).where(whereString)
				.executeSingle();
	}

	public static List<CityObj> getCityObjAll() {
		return new Select().from(CityObj.class).execute();
	}
}
