package com.rayboot.hanzitingxie;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;

import com.rayboot.hanzitingxie.obj.WordData;

public class RankActivity extends MyBaseActivity {

	ListView lvRank;
	MyBaseAdapter<WordData> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Holo_Theme_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		lvRank = (ListView) findViewById(R.id.lvRank);
		adapter = new RankAdapter<WordData>(this,
				WordData.getAllWrongDatas());
		lvRank.setAdapter(adapter);

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
