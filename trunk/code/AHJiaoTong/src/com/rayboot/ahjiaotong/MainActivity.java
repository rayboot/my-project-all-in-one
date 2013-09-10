package com.rayboot.ahjiaotong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.actionbarsherlock.app.SherlockActivity;

public class MainActivity extends SherlockActivity {

	Button btnHead;
	Button btnType;
	Button btnSearch;
	EditText etNum;
	EditText etFrameNum;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		btnHead = (Button) findViewById(R.id.btnHead);
		btnType = (Button) findViewById(R.id.btnType);
		btnSearch = (Button) findViewById(R.id.btnSearch);

		etNum = (EditText) findViewById(R.id.etNum);
		etFrameNum = (EditText) findViewById(R.id.etFrameNum);

		btnHead.setOnClickListener(onClickListener);
		btnType.setOnClickListener(onClickListener);
		btnSearch.setOnClickListener(onClickListener);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		btnHead.setText(Global.headNum);
		btnType.setText(Global.carType);
		btnType.setTag(Global.carTypeNum);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = null;
			switch (v.getId()) {
			case R.id.btnHead:
				intent = new Intent(MainActivity.this, HeadNumSelActivity.class);
				MainActivity.this.startActivity(intent);
				break;
			case R.id.btnType:
				intent = new Intent(MainActivity.this, TypeSelActivity.class);
				MainActivity.this.startActivity(intent);
				break;
			case R.id.btnSearch:
				intent = new Intent(MainActivity.this, ResultActivity.class);
				intent.putExtra("license", btnHead.getText().toString()
						+ etNum.getText().toString());
				intent.putExtra("frameNumber", etFrameNum.getText().toString());
				intent.putExtra("model", btnType.getTag() + "");
				MainActivity.this.startActivity(intent);
				break;
			default:
				break;
			}
		}
	};

}
