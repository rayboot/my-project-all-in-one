package com.rayboot.hanzitingxie;

import java.lang.reflect.Type;
import java.util.List;

import org.holoeverywhere.widget.Toast;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Delete;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.rayboot.hanzitingxie.obj.SourceData;
import com.rayboot.hanzitingxie.obj.WordData;
import com.rayboot.hanzitingxie.util.DataUtil;

public class LoadingActivity extends MyBaseActivity {
	boolean isDelay = false;
	boolean isUpdateDB = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				if (WordData.getAllDatas().size() == 0) {
					Toast.makeText(LoadingActivity.this, "正在升级数据库，请稍候",
							Toast.LENGTH_LONG).show();
					doUpdateDataBase();
					if (WordData.getAllDatas().size() > 0) {
						new Delete().from(SourceData.class).executeSingle();
					}
				}
				if (DataUtil.getInfoFromShared(LoadingActivity.this,
						"do_add_data_to_db") != 1) {
					doAddData2DB();
					Toast.makeText(LoadingActivity.this, "数据库升级成功",
							Toast.LENGTH_SHORT).show();
				}
				goMain(isDelay, true);
			}
		}, 10);
		new Handler().postDelayed(new Runnable() {
			public void run() {
				goMain(true, isUpdateDB);
			}
		}, 3000);
	}

	private void doAddData2DB() {
		DataUtil.setInfoToShared(this, "do_add_data_to_db", 1);
		Type locationInfoListType = new TypeToken<List<WordData>>() {
		}.getType();
		Gson gson = new GsonBuilder().registerTypeAdapter(locationInfoListType,
				new WordData.WordDataTypeAdapter()).create();
		List<WordData> datas = gson
				.fromJson(DataUtil.getFromAssets(this, "data.json"),
						locationInfoListType);
		ActiveAndroid.beginTransaction();
		try {
			for (WordData wordData : datas) {
				wordData.save();
			}
			ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}
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
				}
				ActiveAndroid.setTransactionSuccessful();
			} finally {
				ActiveAndroid.endTransaction();
			}
		}
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
