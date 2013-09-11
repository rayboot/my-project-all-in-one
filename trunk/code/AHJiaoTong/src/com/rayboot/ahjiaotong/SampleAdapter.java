package com.rayboot.ahjiaotong;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SampleAdapter<T> extends MyBaseAdapter<T> {

	public SampleAdapter(Context context, List<T> datas) {
		super(context, datas);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = this.mLayoutInflater.inflate(
					R.layout.item_sample_text, parent, false);
			holder = new ViewHolder();
			holder.tvName = (TextView) convertView.findViewById(R.id.tvName);
			convertView.setTag(holder);
		}

		String indexString = (String) getItem(position);
		holder.tvName.setText(indexString);
		return convertView;
	}

	static class ViewHolder {
		TextView tvName;
	}
}
