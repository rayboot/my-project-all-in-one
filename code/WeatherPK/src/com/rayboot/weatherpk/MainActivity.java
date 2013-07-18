package com.rayboot.weatherpk;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// http://blog.mynook.info/2012/08/18/weather-com-cn-api.html
		startActivity(new Intent(this, FalshActivity.class));
	}

}
