package com.rayboot.letsball.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.rayboot.letsball.R;
import com.rayboot.letsball.model.MatchStatisticObj;

public class MatchStatisticAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater mLayoutInflater;
	List<MatchStatisticObj> matchStatisticObjs = new ArrayList<MatchStatisticObj>();

	public MatchStatisticAdapter(Context mContext, List<MatchStatisticObj> datas) {
		super();
		this.mContext = mContext;
		mLayoutInflater = LayoutInflater.from(mContext);
		this.matchStatisticObjs = datas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return matchStatisticObjs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return matchStatisticObjs.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		if (convertView == null) {
			convertView = mLayoutInflater
					.inflate(R.layout.item_statistic, null);
			vh = new ViewHolder();
			vh.tvHomeStat = (TextView) convertView
					.findViewById(R.id.tvHomeStatistic);
			vh.tvStatName = (TextView) convertView
					.findViewById(R.id.tvStatisticName);
			vh.tvAwayStat = (TextView) convertView
					.findViewById(R.id.tvAwayStatistic);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		MatchStatisticObj mso = matchStatisticObjs.get(position);
		vh.tvHomeStat.setText(mso.home);
		vh.tvStatName.setText(mso.name);
		vh.tvAwayStat.setText(mso.away);

		return convertView;
	}

	private class ViewHolder {
		TextView tvHomeStat;
		TextView tvStatName;
		TextView tvAwayStat;
	}

}
