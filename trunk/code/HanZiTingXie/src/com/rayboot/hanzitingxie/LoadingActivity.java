package com.rayboot.hanzitingxie;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoadingActivity extends MyBaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				Intent intent = new Intent(LoadingActivity.this,
						JumpActivity.class);
				LoadingActivity.this.startActivity(intent);
			}
		}, 3000);
	}

}
