package com.rayboot.ahjiaotong;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class TypeSelActivity extends SherlockActivity {

	GridView gvContent;
	MyBaseAdapter<String> adapter;
	List<String> dataList = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_type_sel);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		gvContent = (GridView) findViewById(R.id.gvContent);

		initType();

		adapter = new TypeSelAdapter<String>(this, dataList);
		gvContent.setAdapter(adapter);

		gvContent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Global.carType = dataList.get(arg2);
				Global.carTypeNum = String.format("%02d", arg2);
				TypeSelActivity.this.finish();
			}
		});
	}

	private void initType() {
		dataList.add("大型汽车");
		dataList.add("小型汽车");
		dataList.add("使馆汽车");
		dataList.add("领馆汽车");
		dataList.add("境外汽车");
		dataList.add("外籍汽车");
		dataList.add("普通摩托车");
		dataList.add("轻便摩托车");
		dataList.add("使馆摩托车");
		dataList.add("领馆摩托车");
		dataList.add("境外摩托车");
		dataList.add("外籍摩托车");
		dataList.add("农用运输车");
		dataList.add("拖拉机");
		dataList.add("挂车");
		dataList.add("教练汽车");
		dataList.add("教练摩托车");
		dataList.add("试验汽车");
		dataList.add("试验摩托车");
		dataList.add("临时入境汽车");
		dataList.add("临时入境摩托");
		dataList.add("临时行驶车");
		dataList.add("警用汽车");
		dataList.add("警用摩托");
		dataList.add("武警汽车");
		dataList.add("军车");
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
