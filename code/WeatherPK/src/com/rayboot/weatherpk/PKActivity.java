package com.rayboot.weatherpk;

import com.rayboot.weatherpk.obj.WeatherSKObj;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class PKActivity extends Activity {
	WeatherSKObj weatherMy;
	WeatherSKObj weatherYou;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pk);
		doPK();
	}

	private void doPK() {
		weatherMy = WeatherSKObj.getWeatherSKObjFromeID(getIntent()
				.getStringExtra("curCityCode"));
		weatherYou = WeatherSKObj.getRandom();
	}

}
