package com.rayboot.pinyininput;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.util.EncodingUtils;

import android.app.Activity;
import android.content.ClipData.Item;
import android.os.Bundle;
import android.util.Log;

import com.activeandroid.ActiveAndroid;

public class MainActivity extends Activity {
	List<WordData> datas = new ArrayList<WordData>();
	List<PinyinData> pinyinDatas = new ArrayList<PinyinData>();
	String yinbiaoString = "āáǎàōóǒòēéěèīíǐìūúǔùǖǘǚǜ";
	String meiyinbiao = "aaaaooooeeeeiiiiuuuuüüüü";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// WordData onedata = WordData.getOneData();
		datas = WordData.getAllDatas();
		for (WordData onedata : datas) {
			for (int i = 0; i < onedata.title.length(); i++) {
				String keypinyin = onedata.pinyin.split(" ")[i].toLowerCase();
				int keytone = 0;
				for (int j = 0; j < yinbiaoString.length(); j++) {
					if (keypinyin.contains("ü")) {
						boolean youyu = true;
					}
					if (keypinyin.contains(yinbiaoString.substring(j, j + 1))) {
						keypinyin = keypinyin.replaceAll(
								yinbiaoString.substring(j, j + 1),
								meiyinbiao.substring(j, j + 1));
						keytone = (j % 4) + 1;
					}
				}
				pinyinDatas
						.add(new PinyinData(onedata.title.substring(i, i + 1),
								keypinyin, keytone, onedata.title,
								onedata.pinyin, onedata.url, 0, 0, 0, ""));
			}

		}

		int size = pinyinDatas.size();
		Log.i("111111", "列表中有多少个？？" + size);
		ActiveAndroid.beginTransaction();
		try {
			for (PinyinData pinyinData : pinyinDatas) {
				pinyinData.save();
			}
	        ActiveAndroid.setTransactionSuccessful();
		} finally {
			ActiveAndroid.endTransaction();
		}
		Log.i("111111", "数据库中有多少个？？" + PinyinData.getAllDatasCount());
		
	}
}
