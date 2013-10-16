package com.rayboot.hanzitingxie;

import java.util.List;

import com.rayboot.hanzitingxie.obj.SourceData;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class RankActivity extends MyBaseActivity {

	ListView lvRank;
	MyBaseAdapter<SourceData> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Holo_Theme_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		lvRank = (ListView) findViewById(R.id.lvRank);
		adapter = new RankAdapter<SourceData>(this,
				SourceData.getAllWrongDatas());
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
