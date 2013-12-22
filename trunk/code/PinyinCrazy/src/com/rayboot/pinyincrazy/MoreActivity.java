package com.rayboot.pinyincrazy;

import android.os.Bundle;
import android.view.MenuItem;

public class MoreActivity extends MyBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Holo_Theme_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// This uses the imported MenuItem from ActionBarSherlock
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return true;
	}

}
