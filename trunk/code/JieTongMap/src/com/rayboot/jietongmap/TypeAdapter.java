package com.rayboot.jietongmap;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.rayboot.jietongmap.obj.POIObj;
import com.rayboot.jietongmap.util.Global;

public class TypeAdapter<T> extends MyBaseAdapter<T> {

	public TypeAdapter(Context context, List<T> datas) {
		super(context, datas);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_type, null);
			vh = new ViewHolder();
			vh.cbType = (CheckBox) convertView.findViewById(R.id.cbType);
			vh.cbType.setOnCheckedChangeListener(onCheckedChangeListener);
			vh.cbType.setTag(position);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		POIObj aio = (POIObj) getItem(position);

		vh.cbType.setText(aio.type);
		vh.cbType.setChecked(Global.selTypeMap.get(aio.type) == null ? true
				: Global.selTypeMap.get(aio.type));

		return convertView;
	}

	private OnCheckedChangeListener onCheckedChangeListener = new OnCheckedChangeListener() {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub
			int index = (Integer) buttonView.getTag();
			POIObj aio = (POIObj) getItem(index);
			Global.selTypeMap.put(aio.type, isChecked);
		}
	};

	static class ViewHolder {
		CheckBox cbType;
	}
}
