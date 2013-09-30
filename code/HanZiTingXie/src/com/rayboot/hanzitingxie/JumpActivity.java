package com.rayboot.hanzitingxie;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class JumpActivity extends MyBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jump);
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

	}

}
