package com.rayboot.beiyingcaia;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class IndexAdapter<T> extends MyBaseAdapter<T> {

	public IndexAdapter(Context context, List<T> datas) {
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
			convertView = this.mLayoutInflater.inflate(R.layout.item_index,
					parent, false);
			holder = new ViewHolder();
			holder.tvIndex = (TextView) convertView.findViewById(R.id.tvIndex);
			convertView.setTag(holder);
		}

		String indexString = (String) getItem(position);
		holder.tvIndex.setText(indexString);
		return convertView;
	}

	static class ViewHolder {
		TextView tvIndex;
	}
}
