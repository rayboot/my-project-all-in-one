package com.rayboot.weatherpk.obj;

import java.util.List;

import android.R.integer;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;

@Table(name = "WeatherSKObjTable")
public class WeatherSKObj extends Model {
	@Column(name = "time")
	public String time;
	@Column(name = "cityId")
	public String cityId;
	@Column(name = "temp")
	public double temp;
	@Column(name = "city")
	public String city;

	public WeatherSKObj() {
		super();
		// TODO Auto-generated constructor stub
	}

	public static List<WeatherSKObj> getWeatherSKObjTableAll() {
		return new Select().from(WeatherSKObj.class).execute();
	}

	public static WeatherSKObj getWeatherSKObjFromeID(String citycode) {
		return new Select().from(WeatherSKObj.class)
				.where("cityId = ?", citycode).executeSingle();
	}

	public static WeatherSKObj getRandom() {
		return new Select().from(WeatherSKObj.class).orderBy("RANDOM()")
				.executeSingle();
	}

	public static List<WeatherSKObj> getPaiMing(double curTemp) {
		return new Select().from(WeatherSKObj.class).where("temp > ?", curTemp)
				.execute();
	}

	public static void clearAll() {
		new Delete().from(WeatherSKObj.class).execute();
	}

	public static List<WeatherSKObj> getAllTemp() {
		return new Select().from(WeatherSKObj.class).groupBy("temp")
				.orderBy("temp desc").execute();
	}

	public static List<WeatherSKObj> getDataFromTemp(double curTemp) {
		return new Select().from(WeatherSKObj.class)
				.where("temp == ?", curTemp).orderBy("cityId").execute();
	}

}
