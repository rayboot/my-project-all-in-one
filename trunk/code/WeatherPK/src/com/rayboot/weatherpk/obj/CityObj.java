package com.rayboot.weatherpk.obj;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

@Table(name = "CityObjTable")
public class CityObj extends Model {
	@Column(name = "cityname")
	public String cityname;
	@Column(name = "citycode")
	public String citycode;

	public CityObj() {
		super();
		// TODO Auto-generated constructor stub
	}

}
