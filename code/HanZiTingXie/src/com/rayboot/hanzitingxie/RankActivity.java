package com.rayboot.hanzitingxie;

import com.rayboot.hanzitingxie.obj.SourceData;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;

public class RankActivity extends MyBaseActivity {

	ListView lvRank;
	MyBaseAdapter<SourceData> adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank);

		lvRank = (ListView) findViewById(R.id.lvRank);
		adapter = new RankAdapter<SourceData>(this,
				SourceData.getAllWrongDatas());
		lvRank.setAdapter(adapter);

	}

}
