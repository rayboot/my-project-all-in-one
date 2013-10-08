package com.rayboot.hanzitingxie;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.rayboot.hanzitingxie.util.DataUtil;

public class JumpActivity extends MyBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jump);

		int count = DataUtil.getInfoFromShared(this, "wenzibi");
		if (count == 0) {
			DataUtil.setInfoToShared(this, "wenzibi", 20);
		} else {
			DataUtil.setInfoToShared(this, "wenzibi", count + 200);
		}
	}

	public void onChuangGuan(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("cur_play_type", MyApplication.PLAY_TYPE_CHUANGGUAN);
		startActivity(intent);
	}

	public void onWuJin(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		intent.putExtra("cur_play_type", MyApplication.PLAY_TYPE_WUJIN);
		startActivity(intent);
	}

	public void onHelp(View view) {

	}

	public void onData(View view) {
		Intent intent = new Intent(this, RankActivity.class);
		startActivity(intent);

	}

	public void onSettings(View view) {
		Intent intent = new Intent(this, SettingsActivity.class);
		startActivity(intent);
	}

}
