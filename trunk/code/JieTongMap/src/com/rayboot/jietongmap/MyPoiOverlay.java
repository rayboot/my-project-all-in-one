package com.rayboot.jietongmap;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKSearch;
import com.rayboot.jietongmap.obj.POIObj;

public class MyPoiOverlay extends PoiOverlay {

	MKSearch mSearch;
	Activity activity;
	MKPoiInfo curIinfo;

	public MyPoiOverlay(Activity activity, MapView mapView, MKSearch search) {
		super(activity, mapView);
		mSearch = search;
		this.activity = activity;
	}

	@Override
	protected boolean onTap(int i) {
		super.onTap(i);
		curIinfo = getPoi(i);

		AlertDialog.Builder builder = new Builder(activity);
		builder.setMessage("要保存吗？");

		builder.setTitle("提示");

		builder.setPositiveButton("确认", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				new POIObj(curIinfo.name, "", curIinfo.address,
						curIinfo.phoneNum, curIinfo.pt.getLatitudeE6(),
						curIinfo.pt.getLatitudeE6()).save();
				dialog.dismiss();

			}
		});

		builder.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		builder.create().show();
		return true;
	}

}
