package com.rayboot.pinyincrazy;


import android.os.Bundle;
import android.widget.ListView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.actionbarsherlock.view.MenuItem;
import com.rayboot.pinyincrazy.adapter.MyBaseAdapter;
import com.rayboot.pinyincrazy.adapter.RankAdapter;
import com.rayboot.pinyincrazy.obj.PinyinDataObj;

public class RankActivity extends MyBaseActivity {
	@InjectView(R.id.lvRank)
	ListView lvRank;
	MyBaseAdapter<PinyinDataObj> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		ButterKnife.inject(this);

		lvRank = (ListView) findViewById(R.id.lvRank);
		adapter = new RankAdapter<PinyinDataObj>(this,
				PinyinDataObj.getAllWrongDatas());
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
