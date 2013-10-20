package com.rayboot.ahjiaotong;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.text.Html;
import android.widget.ListView;
import android.widget.TextView;
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.rayboot.ahjiaotong.ResultObj.ViolateObj;
import com.rayboot.ahjiaotong.util.HttpUtil;
import com.umeng.analytics.MobclickAgent;

public class ResultActivity extends SherlockActivity {

	String license;
	String frameNumber;
	String model;
	String modelName;

	TextView tvResult;
	ListView lvContentListView;
	MyBaseAdapter<ViolateObj> adapter;
	ResultObj resultObj;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
		setContentView(R.layout.activity_result);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);

		tvResult = (TextView) findViewById(R.id.tvResult);
		lvContentListView = (ListView) findViewById(R.id.lvContent);

		setTitle("违章信息列表");

		license = getIntent().getStringExtra("license").toUpperCase();
		frameNumber = getIntent().getStringExtra("frameNumber");
		model = getIntent().getStringExtra("model");
		modelName = getIntent().getStringExtra("modelName");

		RequestParams params = new RequestParams();
		params.put("license", license);
		params.put("frameNumber", frameNumber);
		params.put("model", model);

		if (Global.isShowAD.equals("1")) {
			AppConnect.getInstance(this).initPopAd(this);
			AppConnect.getInstance(this).showPopAd(this);
		}

		HttpUtil.post("http://61.191.44.170/clt/clt/getViolation.msp", params,
				new AsyncHttpResponseHandler() {

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();
						setSupportProgressBarIndeterminateVisibility(false);
					}

					@Override
					public void onSuccess(String arg0) {
						// TODO Auto-generated method stub
						super.onSuccess(arg0);
						Gson gson = new Gson();
						resultObj = gson.fromJson(arg0, ResultObj.class);
						if (resultObj.result != null
								&& resultObj.result.equals("0")) {
							if (resultObj.violate.size() == 0) {
								tvResult.setText("恭喜您，没有查到违章记录！");
							} else {
								tvResult.setText(Html
										.fromHtml("系统中查到您有 <font color=\"red\">"
												+ resultObj.violate.size()
												+ "</font> 条违章记录"));

								if (Global.isShowAD.equals("1")) {
									if (Global.app_count - 10 >= 0) {
										AppConnect
												.getInstance(
														ResultActivity.this)
												.spendPoints(
														10,
														new UpdatePointsNotifier() {

															@Override
															public void getUpdatePointsFailed(
																	String arg0) {
																// TODO
																// Auto-generated
																// method stub
																
															}

															@Override
															public void getUpdatePoints(
																	String arg0,
																	int arg1) {
																// TODO
																// Auto-generated
																// method stub
																
															}
														});
										Global.app_count -= 10;
										loadContent();
									} else {

										AlertDialog.Builder builder = new Builder(
												ResultActivity.this);
										builder.setMessage("系统查询到您有"
												+ resultObj.violate.size()
												+ "条违章记录，但是您目前积分不足，无权查看详情，请获取积分。");
										builder.setTitle("提示");
										builder.setPositiveButton("积分获取",
												new OnClickListener() {

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														AppConnect
																.getInstance(
																		ResultActivity.this)
																.showAppOffers(
																		ResultActivity.this);
													}
												});
										builder.setNegativeButton("取消",
												new OnClickListener() {

													@Override
													public void onClick(
															DialogInterface dialog,
															int which) {
														dialog.dismiss();
														ResultActivity.this
																.finish();
													}
												});
										builder.create().show();
									}
								} else {
									loadContent();
								}

							}
						} else {
							tvResult.setText("输入有误，请检查您的车辆信息。");
						}
					}

				});
		setSupportProgressBarIndeterminateVisibility(true);

		HistoryObj.removeData(license);
		new HistoryObj(license, frameNumber, model, modelName).save();

	}

	private void loadContent() {
		adapter = new ResultAdapter<ResultObj.ViolateObj>(this,
				resultObj.violate);
		lvContentListView.setAdapter(adapter);
	}

	public void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
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
