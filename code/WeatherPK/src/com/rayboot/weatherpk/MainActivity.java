package com.rayboot.weatherpk;

import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

import com.activeandroid.ActiveAndroid;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.rayboot.weatherpk.obj.CityObj;
import com.rayboot.weatherpk.obj.PKObj;
import com.rayboot.weatherpk.obj.SKResultObj;
import com.rayboot.weatherpk.obj.WeatherSKObj;
import com.rayboot.weatherpk.utily.DataUtil;
import com.rayboot.weatherpk.utily.Encrypt;
import com.rayboot.weatherpk.utily.HttpUtil;
import com.rayboot.weatherpk.utily.ScreenShot;
import com.rayboot.weatherpk.utily.Utilly;
import com.umeng.analytics.MobclickAgent;
import com.umeng.fb.FeedbackAgent;
import com.umeng.update.UmengUpdateAgent;
import com.umeng.update.UmengUpdateListener;
import com.umeng.update.UpdateResponse;

public class MainActivity extends Activity {
	public LocationClient mLocationClient = null;
	public MyLocationListenner myListener = new MyLocationListenner();

	@InjectView(R.id.tvTemp)
	TextView tvTemp;
	@InjectView(R.id.tvTime)
	TextView tvTime;
	@InjectView(R.id.tvPKDesc)
	TextView tvPKDesc;
	@InjectView(R.id.btnPK)
	Button btnPK;
	@InjectView(R.id.btnPaihang)
	Button btnPaihang;
	@InjectView(R.id.btnShare)
	Button btnShare;
	@InjectView(R.id.btnAbout)
	ImageButton btnAbout;

	String curCityCode;
	Typeface fontFace;

	int totalCity = 0;
	int curIndex = 0;
	String port = "7e448edb2920690dad244d4d2fc429225debec724d207d823f574f8bcee033eb91137cb8971c437a0973c38f3d8b913592c07d725e9e8c3c";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Views.inject(this);

		startActivity(new Intent(this, FalshActivity.class));
		getAllWeather();
		initLocation();
		btnPK.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.this.startActivity(new Intent(MainActivity.this,
						PKActivity.class).putExtra("curCityCode", curCityCode));
			}

		});
		btnPaihang.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.this.startActivity(new Intent(MainActivity.this,
						RankActivity.class));
			}
		});
		btnShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String file = ScreenShot.shoot(MainActivity.this);
				if (TextUtils.isEmpty(file)) {
					Utilly.shareSomethingText(MainActivity.this, "分享",
							"我使用  #哪最热#  看到现在我这里温度全国排名第几，很有意思，还能PK，赶紧来试试吧。");
				} else {
					Utilly.shareSomethingTextPhoto(MainActivity.this, "分享",
							"我使用  #哪最热#  看到现在我这里" + "全国" + totalCity
									+ "个省市温度PK中第" + curIndex + "名！真的好热啊，你那呢？",
							file);
				}
			}
		});
		btnAbout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MainActivity.this.startActivity(new Intent(MainActivity.this,
						AboutActivity.class));
			}
		});
		fontFace = Typeface.createFromAsset(getAssets(), "fonts/Ba.otf");
		tvTemp.setTypeface(fontFace);
		initPKDB();
		initUMeng();
	}

	private void initUMeng() {
		MobclickAgent.onError(this);
		MobclickAgent.updateOnlineConfig(this);
		UmengUpdateAgent.update(this);
		UmengUpdateAgent.setUpdateAutoPopup(false);
		UmengUpdateAgent.setUpdateListener(new UmengUpdateListener() {
			@Override
			public void onUpdateReturned(int updateStatus,
					UpdateResponse updateInfo) {
				switch (updateStatus) {
				case 0: // has update
					UmengUpdateAgent.showUpdateDialog(MainActivity.this,
							updateInfo);
					break;
				}
			}
		});
	}

	private void initPKDB() {
		PKObj.clearAll();
		PKObj.autoAddRadomN(new Random().nextInt(514));
		PKObj myPKObj = new PKObj();
		myPKObj.sore = 0;
		myPKObj.other = 0;
		myPKObj.save();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		long lastTime = Long.valueOf(DataUtil.getInfoFromShared("allsktime"));
		if (System.currentTimeMillis() - lastTime > 31 * 60 * 1000) {
			getAllWeather();
		}
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}

	private void getAllWeather() {
		DataUtil.setInfoToShared("allsktime", System.currentTimeMillis() + "");
		HttpUtil.get(Encrypt.decrypt(port, "rayboot.work"),
				new AsyncHttpResponseHandler() {

					@Override
					public void onFinish() {
						// TODO Auto-generated method stub
						super.onFinish();
					}

					@Override
					public void onSuccess(String json) {
						// TODO Auto-generated method stub
						super.onSuccess(json);
						json = json.replace("暂无实况", "" + "-100");
						Gson gson = new Gson();
						SKResultObj skro = gson.fromJson(json,
								SKResultObj.class);
						if (skro.code.equals("0000")) {
							WeatherSKObj.clearAll();
							ActiveAndroid.beginTransaction();
							try {
								for (WeatherSKObj wsdo : skro.list) {
									if (wsdo.temp == -100) {
										continue;
									}
									wsdo.save();
								}
								ActiveAndroid.setTransactionSuccessful();
							} finally {
								ActiveAndroid.endTransaction();
							}
						}
						setCurSKTemp(curCityCode);
					}

				});
	}

	public class MyLocationListenner implements BDLocationListener {
		@Override
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {
				return;
			}
			String surCityname = location.getCity().replace("市", "");
			if (TextUtils.isEmpty(surCityname)) {
				return;
			}
			DataUtil.setInfoToShared("curCityName", surCityname);
			mLocationClient.stop();
			setCurSKTemp(CityObj.getCityObjFromeName(surCityname).citycode);
		}

		public void onReceivePoi(BDLocation poiLocation) {
		}
	}

	public void setCurSKTemp(String id) {
		curCityCode = id;
		if (TextUtils.isEmpty(id)) {
			return;
		}

		WeatherSKObj wsko = WeatherSKObj.getWeatherSKObjFromeID(id);
		if (wsko == null) {
			return;
		}
		tvTemp.setText((int) (wsko.temp) + "");
		tvTime.setText("发布时间：" + wsko.time);
		List<WeatherSKObj> all = WeatherSKObj.getWeatherSKObjTableAll();
		int total = all.size();
		all.clear();
		all = WeatherSKObj.getPaiMing(wsko.temp);
		int index = all.size() + 1;
		all.clear();
		tvPKDesc.setText("全国" + total + "个省市温度PK中第" + index
				+ "名！\n敢不敢随机PK一下？？！！");
		totalCity = total;
		curIndex = index;
	}

	private void initLocation() {
		mLocationClient = new LocationClient(this);
		mLocationClient.registerLocationListener(myListener);
		LocationClientOption option = new LocationClientOption();
		option.setAddrType("all");
		option.setOpenGps(true);
		option.disableCache(false);
		option.setCoorType("bd09ll");
		option.setProdName(getResources().getString(R.string.app_name));
		option.setScanSpan(60000);
		option.setPriority(LocationClientOption.NetWorkFirst);
		mLocationClient.setLocOption(option);
		mLocationClient.start();
	}
}
