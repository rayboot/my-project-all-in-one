package com.rayboot.weatherpk;

import java.util.List;

import com.rayboot.weatherpk.obj.RankObj;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

public class RankAdapter<T> extends MyBaseAdapter<T> {

	public RankAdapter(Context context, List<T> datas) {
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
			convertView = this.mLayoutInflater.inflate(R.layout.item_rank,
					parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}

		RankObj ro = (RankObj) getItem(position);
		holder.tvIndex.setText(ro.index + "");
		holder.tvTemp.setText(ro.temp + "℃");
		holder.tvCitys.setText(Html.fromHtml(ro.citys.toString()));
		return convertView;
	}

	static class ViewHolder {
		@InjectView(R.id.tvIndex)
		TextView tvIndex;
		@InjectView(R.id.tvTemp)
		TextView tvTemp;
		@InjectView(R.id.tvCitys)
		TextView tvCitys;

		public ViewHolder(View view) {
			Views.inject(this, view);
		}
	}
}
