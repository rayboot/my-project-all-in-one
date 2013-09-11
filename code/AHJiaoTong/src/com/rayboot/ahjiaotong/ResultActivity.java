package com.rayboot.ahjiaotong;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;

public class ResultActivity extends SherlockActivity {

	String license;
	String frameNumber;
	String model;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		license = getIntent().getStringExtra("license").toUpperCase();
		frameNumber = getIntent().getStringExtra("frameNumber");
		model = getIntent().getStringExtra("model");

	}

}
