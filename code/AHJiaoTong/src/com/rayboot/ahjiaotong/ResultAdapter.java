package com.rayboot.ahjiaotong;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rayboot.ahjiaotong.ResultObj.ViolateObj;

public class ResultAdapter<T> extends MyBaseAdapter<T> {

	public ResultAdapter(Context context, List<T> datas) {
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
			convertView = this.mLayoutInflater.inflate(R.layout.item_result,
					parent, false);
			holder = new ViewHolder();
			holder.tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.tvDesc = (TextView) convertView.findViewById(R.id.tvDesc);
			holder.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			convertView.setTag(holder);
		}

		ViolateObj vo = (ViolateObj) getItem(position);
		holder.tvTitle.setText(vo.address);
		holder.tvDesc.setText(vo.action);
		holder.tvTime.setText(vo.time.replace(".0", ""));
		return convertView;
	}

	static class ViewHolder {
		TextView tvTitle;
		TextView tvDesc;
		TextView tvTime;
	}
}
