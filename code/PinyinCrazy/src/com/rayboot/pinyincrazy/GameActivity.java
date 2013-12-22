package com.rayboot.pinyincrazy;

import org.holoeverywhere.widget.Toast;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rayboot.pinyincrazy.obj.PinyinDataObj;

public class GameActivity extends MyBaseActivity {
	private PinyinDataObj pinyinData;
	@InjectView(R.id.tvKeyword)
	TextView tvKeyword;
	@InjectView(R.id.tvTone)
	TextView tvTone;
	@InjectView(R.id.tvTip)
	TextView tvTip;
	@InjectView(R.id.tvTitle)
	TextView tvTitle;

	String[] toneArray = { " ", "―", "╱", "∨", "╲" };
	int rightCount = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Holo_Theme_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		ButterKnife.inject(this);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		rightCount = PinyinDataObj.getAllRightDatas().size();
		getSupportActionBar().setSubtitle(
				"共" + PinyinDataObj.getAllDatas().size() + "");
		getSupportActionBar().setTitle("第" + (rightCount + 1) + "关");
		setGameData();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		getSupportActionBar().setTitle("第" + (rightCount + 1) + "关");
	}

	public void setGameData() {
		pinyinData = PinyinDataObj.getChuangGuanRandomData();
		tvTitle.setText(pinyinData.keychar);
		tvTip.setText(pinyinData.title);
		onClearClick(null);
	}

	public void onKeyClick(View view) {
		String keyString = ((TextView) view).getText().toString();
		String nowString = tvKeyword.getText().toString();
		tvKeyword.setText(nowString + keyString);
	}

	public void onClearClick(View view) {
		tvKeyword.setText("");
		tvTone.setText("");
		tvTone.setTag("0");
	}

	public void onOKClick(View view) {
		if (pinyinData.keypinyin.trim().equals(
				tvKeyword.getText().toString().trim())
				&& pinyinData.keytone == Integer.valueOf((String) tvTone
						.getTag())) {
			Toast.makeText(this, "恭喜你答对了", Toast.LENGTH_SHORT).show();
			pinyinData.isRight = 1;
			pinyinData.save();
			rightCount++;
			getSupportActionBar().setTitle("第" + (rightCount + 1) + "关");
			setGameData();
			return;
		} else {
			Toast.makeText(this, "回答错误。", Toast.LENGTH_SHORT).show();
			pinyinData.wrong += 1;
			pinyinData.save();
		}
	}

	public void onToneClick(View view) {
		String toneString = ((TextView) view).getText().toString();
		if (toneString.equals("轻声")) {
			toneString = "";
		}
		tvTone.setText(toneString);
		tvTone.setTag(view.getTag());
	}

	public void onHelpClick(View view) {
		switch (Integer.valueOf((String) view.getTag())) {
		case 1:
			// 求助好友
			break;
		case 2:
			// 显示答案
			tvKeyword.setText(pinyinData.keypinyin);
			tvTone.setText(toneArray[pinyinData.keytone]);
			tvTone.setTag(pinyinData.keytone + "");
			break;
		case 3:
			// 听一听
			break;
		case 4:
			// 跳过该题
			setGameData();
			break;
		case 5:
			// 字面意思
			Intent intent = new Intent(this, TipActivity.class);
			intent.putExtra("data_answer", pinyinData.title);
			intent.putExtra("data_tip", pinyinData.url);
			intent.putExtra("data_pinyin", pinyinData.pinyin);
			intent.putExtra("data_key_pinyin", pinyinData.keypinyin);
			intent.putExtra("data_key_char", pinyinData.keychar);
			this.startActivity(intent);
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// This uses the imported MenuItem from ActionBarSherlock
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return true;
	}
}
