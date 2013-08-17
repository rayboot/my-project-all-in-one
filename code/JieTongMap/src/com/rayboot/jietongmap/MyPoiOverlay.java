package com.rayboot.jietongmap;

import android.app.Activity;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKSearch;
import com.rayboot.jietongmap.obj.POIObj;

public class MyPoiOverlay extends PoiOverlay {

	MKSearch mSearch;

	public MyPoiOverlay(Activity activity, MapView mapView, MKSearch search) {
		super(activity, mapView);
		mSearch = search;
	}

	@Override
	protected boolean onTap(int i) {
		super.onTap(i);
		MKPoiInfo info = getPoi(i);

		new POIObj(info.name, "", info.address, info.phoneNum,
				info.pt.getLatitudeE6(), info.pt.getLatitudeE6()).save();
		return true;
	}

}
