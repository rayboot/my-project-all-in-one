package com.rayboot.housecalculator;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

public class ResultAdapter<T> extends MyBaseAdapter<T> {

	public ResultAdapter(Context context, List<T> datas) {
		super(context, datas);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub ViewHolder holder;
		ViewHolder holder;
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = this.mLayoutInflater.inflate(R.layout.item_result,
					parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}

		ResultObj ro = (ResultObj) getItem(position);
		holder.tvTime.setText("第" + ro.time + "期");
		holder.tvRest.setText((int)ro.rest + "");
		holder.tvBenjin.setText((int)ro.benjin + "");
		holder.tvLixi.setText((int)ro.lixi + "");
		holder.tvEdu.setText((int)ro.edu + "");
		return convertView;
	}

	static class ViewHolder {
		@InjectView(R.id.tvTime)
		TextView tvTime;
		@InjectView(R.id.tvRest)
		TextView tvRest;
		@InjectView(R.id.tvBenjin)
		TextView tvBenjin;
		@InjectView(R.id.tvLixi)
		TextView tvLixi;
		@InjectView(R.id.tvEdu)
		TextView tvEdu;

		public ViewHolder(View view) {
			Views.inject(this, view);
		}
	}
}
