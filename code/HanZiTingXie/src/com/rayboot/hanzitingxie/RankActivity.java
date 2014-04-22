package com.rayboot.hanzitingxie;

import android.os.Bundle;
import android.widget.ListView;

import com.actionbarsherlock.view.MenuItem;
import com.rayboot.hanzitingxie.adapter.MyBaseAdapter;
import com.rayboot.hanzitingxie.adapter.RankAdapter;
import com.rayboot.hanzitingxie.obj.WordData;

public class RankActivity extends MyBaseActivity {

	ListView lvRank;
	MyBaseAdapter<WordData> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
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
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
