package com.rayboot.weatherpk;

import java.util.List;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

import com.nineoldandroids.animation.ObjectAnimator;
import com.rayboot.weatherpk.obj.PKObj;
import com.rayboot.weatherpk.obj.WeatherSKObj;
import com.umeng.analytics.MobclickAgent;

public class PKActivity extends Activity implements SensorEventListener {
	WeatherSKObj weatherMy;
	WeatherSKObj weatherYou;
	PKObj myPkObj;
	@InjectView(R.id.tvScore)
	TextView tvScore;
	@InjectView(R.id.tvIndex)
	TextView tvIndex;
	@InjectView(R.id.tvMyLoc)
	TextView tvMyLoc;
	@InjectView(R.id.tvOtherLoc)
	TextView tvOtherLoc;
	@InjectView(R.id.tvMyTemp)
	TextView tvMyTemp;
	@InjectView(R.id.tvOtherTemp)
	TextView tvOtherTemp;
	@InjectView(R.id.tvResult)
	TextView tvResult;
	@InjectView(R.id.llPKImg)
	LinearLayout llPKImg;
	@InjectView(R.id.rlPK)
	RelativeLayout rlPK;

	// Sensor管理器
	private SensorManager mSensorManager = null;
	// 震动
	private Vibrator mVibrator = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pk);
		Views.inject(this);
		llPKImg.setVisibility(View.VISIBLE);
		rlPK.setVisibility(View.GONE);

		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

		refelshSore();
	}

	private void doPK() {
		llPKImg.setVisibility(View.GONE);
		rlPK.setVisibility(View.VISIBLE);
		weatherMy = WeatherSKObj.getWeatherSKObjFromeID(getIntent()
				.getStringExtra("curCityCode"));
		weatherYou = WeatherSKObj.getRandom();

		ObjectAnimator.ofFloat(tvMyLoc, "alpha", 0, 1).setDuration(4000)
				.start();
		ObjectAnimator theTranslationX = ObjectAnimator.ofFloat(tvMyLoc,
				"translationX", -100, 0).setDuration(4000);
		theTranslationX.start();

		ObjectAnimator.ofFloat(tvOtherLoc, "alpha", 0, 1).setDuration(4000)
				.start();
		ObjectAnimator.ofFloat(tvOtherLoc, "translationX", 100, 0)
				.setDuration(4000).start();

		tvMyLoc.setText(weatherMy.city);
		tvOtherLoc.setText(weatherYou.city);
		tvMyTemp.setText(weatherMy.temp + "℃");
		tvOtherTemp.setText(weatherYou.temp + "℃");

		if (weatherMy.temp > weatherYou.temp) {
			tvResult.setText("你赢了！");
			myPkObj.sore += 3;
		} else if (weatherMy.temp < weatherYou.temp) {
			tvResult.setText("败北");
			myPkObj.sore += 0;
		} else {
			tvResult.setText("平手");
			myPkObj.sore += 1;
		}
		myPkObj.save();
		refelshSore();
	}

	private void refelshSore() {
		if (myPkObj == null) {
			myPkObj = PKObj.getMyData();
			if (myPkObj == null) {
				myPkObj = new PKObj();
				myPkObj.other = 0;
				myPkObj.sore = 0;
				myPkObj.save();
			}
		}
		List<PKObj> list = PKObj.getMyIndex(myPkObj.sore);

		tvScore.setText("积分：" + myPkObj.sore);
		tvIndex.setText("排名：" + list.size() + 1);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mSensorManager.registerListener(this,
				mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
				SensorManager.SENSOR_DELAY_NORMAL);
		MobclickAgent.onResume(this);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		mSensorManager.unregisterListener(this);
		super.onStop();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mSensorManager.unregisterListener(this);
		super.onPause();
		MobclickAgent.onPause(this);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		int sensorType = event.sensor.getType();
		float[] values = event.values;
		if (sensorType == Sensor.TYPE_ACCELEROMETER) {
			if (Math.abs(values[0]) > 14 || Math.abs(values[1]) > 14
					|| Math.abs(values[2]) > 14) {
				mVibrator.vibrate(100);
				doPK();
			}
		}
	}
}
