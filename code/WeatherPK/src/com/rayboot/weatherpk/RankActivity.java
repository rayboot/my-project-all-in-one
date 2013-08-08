package com.rayboot.weatherpk;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import butterknife.InjectView;
import butterknife.Views;

import com.rayboot.weatherpk.obj.RankObj;
import com.rayboot.weatherpk.obj.WeatherSKObj;
import com.rayboot.weatherpk.utily.DataUtil;
import com.umeng.analytics.MobclickAgent;

public class RankActivity extends Activity {
	String curCityName;
	List<WeatherSKObj> listTemp;
	List<RankObj> listRankData = new ArrayList<RankObj>();
	List<RankObj> listSearchData = new ArrayList<RankObj>();
	@InjectView(R.id.listRank)
	ListView listRank;
	@InjectView(R.id.etSearch)
	EditText etSearch;
	@InjectView(R.id.btnBack)
	Button btnBack;

	BaseAdapter rankAdapter;
	BaseAdapter searchAdapter;
	RankObj curRankObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rank);
		Views.inject(this);

		int totalindex = 1;
		listTemp = WeatherSKObj.getAllTemp();
		for (WeatherSKObj wsko : listTemp) {
			RankObj ro = new RankObj();
			ro.index = totalindex;
			ro.temp = wsko.temp;
			ro.citys = new StringBuilder();
			List<WeatherSKObj> tempList = WeatherSKObj
					.getDataFromTemp(wsko.temp);
			if (tempList != null && tempList.size() > 0) {
				for (WeatherSKObj weatherSKObj : tempList) {
					if (weatherSKObj.city.equals(DataUtil
							.getInfoFromShared("curCityName"))) {
						ro.citys.append("<font color='red'>")
								.append(weatherSKObj.city).append("</font> ");
						continue;
					}
					ro.citys.append(weatherSKObj.city).append(" ");
				}
				totalindex += tempList.size();
				listRankData.add(ro);
			}
		}
		rankAdapter = new RankAdapter<RankObj>(this, listRankData);
		listRank.setAdapter(rankAdapter);

		etSearch.setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_ENTER) {// 修改回车键功能
					// 先隐藏键盘
					((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
							.hideSoftInputFromWindow(RankActivity.this
									.getCurrentFocus().getWindowToken(),
									InputMethodManager.HIDE_NOT_ALWAYS);
					listSearchData.clear();
					for (RankObj ro : listRankData) {
						if (ro.citys.indexOf(etSearch.getEditableText()
								.toString()) != -1) {
							listSearchData.add(ro);
						}
					}
					if (listSearchData.size() > 0) {
						searchAdapter = new RankAdapter<RankObj>(
								RankActivity.this, listSearchData);
						listRank.setAdapter(searchAdapter);
					} else {
						Toast.makeText(RankActivity.this, "没有搜索到该城市",
								Toast.LENGTH_LONG).show();
					}
				}
				return false;
			}
		});

		btnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				RankActivity.this.finish();
			}
		});
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}
