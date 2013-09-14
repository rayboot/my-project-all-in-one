package com.rayboot.ahjiaotong;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class HeadNumSelActivity extends SherlockActivity {

	GridView gvContent;
	MyBaseAdapter<String> adapter;
	List<String> dataList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_head_num_sel);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		gvContent = (GridView) findViewById(R.id.gvContent);

		for (int i = 0; i < 26; i++) {
			dataList.add("皖" + String.valueOf((char) ('A' + i)));
		}
		adapter = new SampleAdapter<String>(this, dataList);
		gvContent.setAdapter(adapter);

		gvContent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Global.headNum = dataList.get(arg2);
				HeadNumSelActivity.this.finish();
			}
		});
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
		}
		return super.onOptionsItemSelected(item);
	}
}
