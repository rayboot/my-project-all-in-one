package com.rayboot.hanzitingxie;

import java.util.List;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Set;
import com.activeandroid.query.Update;
import com.activeandroid.util.SQLiteUtils;
import com.rayboot.hanzitingxie.obj.SourceData;
import com.rayboot.hanzitingxie.obj.WordData;
import com.umeng.common.net.u;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class LoadingActivity extends MyBaseActivity {
	boolean isDelay = false;
	boolean isUpdateDB = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				goMain(true, isUpdateDB);
			}
		}, 3000);
		doUpdateDataBase();
	}

	private void doUpdateDataBase() {
		List<SourceData> allData = SourceData.getAllDataByWrong();
		if (allData.size() > 0) {
			ActiveAndroid.beginTransaction();
			try {
				for (SourceData sourceData : allData) {
					new WordData(sourceData.title, sourceData.pinyin,
							sourceData.url, sourceData.isRight,
							sourceData.wrong).save();
					// new Update(WordData.class)
					// .set("isRight=?,wrong=?", sourceData.isRight,
					// sourceData.wrong)
					// .where("title=?", sourceData.title).execute();
				}
				ActiveAndroid.setTransactionSuccessful();
			} finally {
				ActiveAndroid.endTransaction();
			}
		}
		goMain(isDelay, true);
	}

	private void goMain(boolean delay, boolean update) {
		isDelay = delay;
		isUpdateDB = update;
		if (!isDelay || !isUpdateDB) {

			return;
		}
		Intent intent = new Intent(LoadingActivity.this, JumpActivity.class);
		LoadingActivity.this.startActivity(intent);
	}

}
