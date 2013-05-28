package com.rayboot.letsball.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.rayboot.letsball.R;
import com.rayboot.letsball.model.MatchTimeLineObj;
import com.rayboot.letsball.model.PlayerInfo;

public class MatchTimeLineAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater mLayoutInflater;
	List<MatchTimeLineObj> matchTimeLineObjs = new ArrayList<MatchTimeLineObj>();

	public MatchTimeLineAdapter(Context mContext, List<MatchTimeLineObj> datas) {
		super();
		this.mContext = mContext;
		mLayoutInflater = LayoutInflater.from(mContext);
		this.matchTimeLineObjs = datas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return matchTimeLineObjs.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return matchTimeLineObjs.get(position);
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
			convertView = mLayoutInflater.inflate(R.layout.item_timeline, null);
			vh = new ViewHolder();
			vh.tvHomeEvent = (TextView) convertView
					.findViewById(R.id.tvHomeEvent);
			vh.ivHomeEvent = (ImageView) convertView
					.findViewById(R.id.ivHomeStatus);
			vh.tvTime = (TextView) convertView.findViewById(R.id.tvTime);
			vh.tvAwayEvent = (TextView) convertView
					.findViewById(R.id.tvAwayEvent);
			vh.ivAwayEvent = (ImageView) convertView
					.findViewById(R.id.ivAwayStatus);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		MatchTimeLineObj mtlo = (MatchTimeLineObj) getItem(position);
		if (PlayerInfo.getPlayerInfoFromDB(mtlo.homePlayerUrl) == null) {
			vh.tvHomeEvent.setVisibility(View.INVISIBLE);
			vh.ivHomeEvent.setVisibility(View.INVISIBLE);
		} else {
			vh.tvHomeEvent.setVisibility(View.VISIBLE);
			vh.ivHomeEvent.setVisibility(View.VISIBLE);
			vh.tvHomeEvent.setText(PlayerInfo
					.getPlayerInfoFromDB(mtlo.homePlayerUrl).name);
			vh.ivHomeEvent.setImageResource(mtlo.getStatusRes(mtlo.homeStatus));
		}

		vh.tvTime.setText(" " + mtlo.time);

		if (PlayerInfo.getPlayerInfoFromDB(mtlo.awayPlayerUrl) == null) {
			vh.tvAwayEvent.setVisibility(View.INVISIBLE);
			vh.ivAwayEvent.setVisibility(View.INVISIBLE);
		} else {
			vh.tvAwayEvent.setVisibility(View.VISIBLE);
			vh.ivAwayEvent.setVisibility(View.VISIBLE);
			vh.tvAwayEvent.setText(PlayerInfo
					.getPlayerInfoFromDB(mtlo.awayPlayerUrl).name);
			vh.ivAwayEvent.setImageResource(mtlo.getStatusRes(mtlo.awayStatus));
		}

		return convertView;
	}

	private class ViewHolder {
		TextView tvHomeEvent;
		ImageView ivHomeEvent;
		TextView tvTime;
		TextView tvAwayEvent;
		ImageView ivAwayEvent;
	}

}
