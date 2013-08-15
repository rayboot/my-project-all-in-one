package com.rayboot.housecalculator;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class ResultActivity extends SherlockActivity {
	@InjectView(R.id.btnType1)
	Button btnType1;
	@InjectView(R.id.btnType2)
	Button btnType2;
	@InjectView(R.id.tvTotal)
	TextView tvTotal;
	@InjectView(R.id.tvTotalLiXi)
	TextView tvTotalLiXi;
	@InjectView(R.id.tvTotalHuanKuan)
	TextView tvTotalHuanKuan;
	@InjectView(R.id.lvContent)
	ListView lvContent;

	double gjjRate = 0;
	double sdRate = 0;
	double gjjValue = 0;
	double sdValue = 0;
	int gjjMonth = 0;
	int sdMonth = 0;

	double[] gjjPay;
	double[] gjjBace;
	double[] sdPay;
	double[] sdBace;

	double[] payMonth;
	double[] baseMonth;

	double total;
	double totalType1;
	double totalType2;

	BaseAdapter resultType1Adapter;
	BaseAdapter resultType2Adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.MyTheme);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		Views.inject(this);

		gjjRate = getIntent().getDoubleExtra("gjjRate", 0.045) / 12;
		sdRate = getIntent().getDoubleExtra("sdRate", 0.0655) / 12;
		gjjValue = getIntent().getDoubleExtra("gjjValue", 0) * 10000;
		sdValue = getIntent().getDoubleExtra("sdValue", 0) * 10000;
		gjjMonth = getIntent().getIntExtra("gjjMonth", 0);
		sdMonth = getIntent().getIntExtra("sdMonth", 0);

		btnType1.setOnClickListener(onClickListener);
		btnType2.setOnClickListener(onClickListener);
		getResult(1);
		getResult(2);
		btnType1.performClick();
		btnType1.setSelected(true);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btnType1:
				changeTitle(totalType1);
				changeList(resultType1Adapter);
				btnType1.setSelected(true);
				btnType2.setSelected(false);
				break;
			case R.id.btnType2:
				changeTitle(totalType2);
				changeList(resultType2Adapter);
				btnType1.setSelected(false);
				btnType2.setSelected(true);
				break;
			default:
				break;
			}
		}
	};

	private void changeList(BaseAdapter adapter) {
		if (adapter == null) {
			return;
		}
		lvContent.setAdapter(adapter);

	}

	private void changeTitle(double totalValue) {
		tvTotal.setText(gjjValue + sdValue + "");
		tvTotalLiXi.setText("+" + (int) (totalValue - gjjValue - sdValue));
		tvTotalHuanKuan.setText("=" + (int) totalValue);
	}

	private void getResult(int rateType) {
		if (gjjMonth > 0) {
			gjjPay = new double[gjjMonth];
			gjjBace = new double[gjjMonth];
		}
		if (sdMonth > 0) {
			sdPay = new double[sdMonth];
			sdBace = new double[sdMonth];
		}

		for (int i = 0; i < gjjMonth; i++) {
			double rate = gjjRate;
			if (rateType == 1) {
				gjjPay[i] = (gjjValue * rate * Math.pow((1 + rate), gjjMonth))
						/ (Math.pow((1 + rate), gjjMonth) - 1);
				gjjBace[i] = (gjjValue * rate * Math.pow((1 + rate), i))
						/ (Math.pow((1 + rate), gjjMonth) - 1);
			} else {
				gjjPay[i] = (gjjValue / gjjMonth)
						+ (gjjValue - gjjValue / gjjMonth * i) * rate;
				gjjBace[i] = gjjValue / gjjMonth;
			}
		}

		for (int i = 0; i < sdMonth; i++) {
			double rate = sdRate;
			if (rateType == 1) {
				sdPay[i] = (sdValue * rate * Math.pow((1 + rate), sdMonth))
						/ (Math.pow((1 + rate), sdMonth) - 1);
				sdBace[i] = (sdValue * rate * Math.pow((1 + rate), i))
						/ (Math.pow((1 + rate), sdMonth) - 1);
			} else {
				sdPay[i] = (sdValue / sdMonth)
						+ (sdValue - sdValue / sdMonth * i) * rate;
				sdBace[i] = sdValue / sdMonth;
			}

		}
		total = 0;
		if (gjjMonth > sdMonth) {
			payMonth = new double[gjjMonth];
			baseMonth = new double[gjjMonth];
			for (int i = 0; i < gjjMonth; i++) {
				double other = 0;
				double otherBace = 0;
				if (i < sdMonth) {
					other = sdPay[i];
					otherBace = sdBace[i];
				}
				payMonth[i] = gjjPay[i] + other;
				baseMonth[i] = gjjBace[i] + otherBace;
				total = total + payMonth[i];
			}
		} else {
			payMonth = new double[sdMonth];
			baseMonth = new double[sdMonth];
			for (int i = 0; i < sdMonth; i++) {
				double other = 0;
				double otherBace = 0;
				if (i < gjjMonth) {
					other = gjjPay[i];
					otherBace = gjjBace[i];
				}
				payMonth[i] = sdPay[i] + other;
				baseMonth[i] = sdBace[i] + otherBace;
				total = total + payMonth[i];

			}
		}

		List<ResultObj> resultDatas = new ArrayList<ResultObj>();
		double rest = 0;
		for (int i = 0; i < payMonth.length; i++) {
			resultDatas.add(new ResultObj(i + 1, gjjValue + sdValue - rest,
					baseMonth[i], payMonth[i] - baseMonth[i], payMonth[i]));
			rest = rest + baseMonth[i];
		}
		if (rateType == 1) {
			if (resultType1Adapter == null) {
				resultType1Adapter = new ResultAdapter<ResultObj>(this,
						resultDatas);
			}
			totalType1 = total;
		} else {
			if (resultType2Adapter == null) {
				resultType2Adapter = new ResultAdapter<ResultObj>(this,
						resultDatas);
			}
			totalType2 = total;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			this.finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
