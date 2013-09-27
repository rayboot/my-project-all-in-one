package com.rayboot.hanzitingxie;

import java.io.IOException;

import com.rayboot.hanzitingxie.util.DataUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onInitData(View view) {
		try {
			DataUtil.saveInitData("http://baike.baidu.com/link?url=4ppxyv-jM3B5fZy8dHVNJl1mlZ0vPWN6bitUYH_liqUNvekTCq-7gaDH2ZcvjxYDoScgWEuraW7HE2HavmxEiq");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
