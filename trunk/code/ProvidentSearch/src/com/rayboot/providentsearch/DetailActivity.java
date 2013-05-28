package com.rayboot.providentsearch;

import android.os.Bundle;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.google.gson.Gson;
import com.umeng.analytics.MobclickAgent;

public class DetailActivity extends SherlockActivity {
	SearchDataObj sdo;

	@InjectView(R.id.tvName)
	TextView tvName;
	@InjectView(R.id.tvCard)
	TextView tvCard;
	@InjectView(R.id.tvCBYH)
	TextView tvCBYH;
	@InjectView(R.id.tvDWYJED)
	TextView tvDWYJED;
	@InjectView(R.id.tvGJD)
	TextView tvGJD;
	@InjectView(R.id.tvGJJKH)
	TextView tvGJJKH;
	@InjectView(R.id.tvGRYJED)
	TextView tvGRYJED;
	@InjectView(R.id.tvGZJS)
	TextView tvGZJS;
	@InjectView(R.id.tvJCBL)
	TextView tvJCBL;
	@InjectView(R.id.tvZHYE)
	TextView tvZHYE;
	@InjectView(R.id.tvDWMC)
	TextView tvDWMC;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Theme_Sherlock_Light);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_detail);
		Views.inject(this);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		Gson gson = new Gson();
		sdo = gson.fromJson(getIntent().getExtras().getString("data_string"),
				SearchDataObj.class);

		fillContent();
	}

	private void fillContent() {
		tvName.setText(sdo.zgxm);
		tvCard.setText(sdo.sfzhm);
		tvCBYH.setText(sdo.yhdm);
		tvDWYJED.setText("￥" + sdo.dwyje);
		tvGJD.setText(sdo.gjd);
		tvGJJKH.setText(sdo.gjjkh);
		tvGRYJED.setText("￥" + sdo.zgyje);
		tvGZJS.setText("￥" + sdo.ygz);
		tvJCBL.setText(sdo.jjbl);
		tvZHYE.setText("￥" + sdo.scje);
		tvDWMC.setText(sdo.dwmc);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// This uses the imported MenuItem from ActionBarSherlock
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			overridePendingTransition(R.anim.stay_anim,
					R.anim.base_slide_right_out);
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
