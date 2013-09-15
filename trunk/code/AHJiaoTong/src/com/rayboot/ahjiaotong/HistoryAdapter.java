package com.rayboot.ahjiaotong;

import java.util.List;

import com.rayboot.ahjiaotong.ResultAdapter.ViewHolder;
import com.rayboot.ahjiaotong.ResultObj.ViolateObj;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HistoryAdapter<T> extends MyBaseAdapter<T> {

	public HistoryAdapter(Context context, List<T> datas) {
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
			convertView = this.mLayoutInflater.inflate(R.layout.item_history,
					parent, false);
			holder = new ViewHolder();
			holder.tvContent = (TextView) convertView
					.findViewById(R.id.tvContent);
			holder.tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
			convertView.setTag(holder);
		}

		HistoryObj ho = (HistoryObj) getItem(position);
		holder.tvContent.setText(ho.license);
		holder.tvDesc.setText(ho.modelName);
		convertView.setTag(R.string.tag1, ho);
		return convertView;
	}

	static class ViewHolder {
		TextView tvContent;
		TextView tvDesc;
	}

}
