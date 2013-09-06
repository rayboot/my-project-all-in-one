package com.rayboot.jietongmap;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.rayboot.jietongmap.obj.POIObj;
import com.rayboot.jietongmap.util.Global;

public class BehindMenuFragment extends Fragment {

	GridView gvType;
	MyBaseAdapter<POIObj> adapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.menu_behind, null);
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		List<POIObj> poiObjs = POIObj.getAllType();
		if (Global.selTypeMap.size() <= 0) {
			for (POIObj poiObj : poiObjs) {
				Global.selTypeMap.put(poiObj.type, true);
			}
		}
		gvType = (GridView) getActivity().findViewById(R.id.gvType);
		adapter = new TypeAdapter<POIObj>(getActivity(), poiObjs);
		gvType.setAdapter(adapter);

	}
}
