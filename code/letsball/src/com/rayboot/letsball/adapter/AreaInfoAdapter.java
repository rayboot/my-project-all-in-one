package com.rayboot.letsball.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.rayboot.letsball.R;
import com.rayboot.letsball.activity.LeagueActivity;
import com.rayboot.letsball.model.AreaInfo;

public class AreaInfoAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater mLayoutInflater;
	List<AreaInfo> areaInfos = new ArrayList<AreaInfo>();

	public AreaInfoAdapter(Context mContext, List<AreaInfo> areaInfos) {
		super();
		this.mContext = mContext;
		mLayoutInflater = LayoutInflater.from(mContext);
		this.areaInfos = areaInfos;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return areaInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return areaInfos.get(position);
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
					.inflate(R.layout.item_area_info, null);
			vh = new ViewHolder();
			vh.llAreaInfo = (LinearLayout) convertView
					.findViewById(R.id.rlAreaInfo);
			vh.tvName = (TextView) convertView.findViewById(R.id.tvName);
			vh.llLeagueType = (LinearLayout) convertView
					.findViewById(R.id.llLeagueType);
			vh.llLeague = (LinearLayout) convertView
					.findViewById(R.id.llLeague);
			vh.tvLeague = (TextView) convertView.findViewById(R.id.tvLeague);
			vh.ivLeague = (ImageView) convertView.findViewById(R.id.ivLeague);
			vh.ivFlag = (ImageView) convertView.findViewById(R.id.ivFlag);
			vh.llCup = (LinearLayout) convertView.findViewById(R.id.llCup);
			vh.tvCup = (TextView) convertView.findViewById(R.id.tvCup);
			vh.ivCup = (ImageView) convertView.findViewById(R.id.ivCup);
			vh.llLeague.setTag(Integer.valueOf(position));
			vh.llCup.setTag(Integer.valueOf(position));
			vh.llCup.setOnClickListener(clickListener);
			vh.llLeague.setOnClickListener(clickListener);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		AreaInfo ai = areaInfos.get(position);

		vh.llAreaInfo.setBackgroundColor(ai.bgColor);
		// vh.rlAreaInfo.setBackgroundColor(ai.resId);
		vh.tvName.setText(ai.name);
		ImageLoader.getInstance().displayImage(
				ai.getLeagueTypeInfo(true).logoFile, vh.ivLeague);
		ImageLoader.getInstance().displayImage(ai.flagFile, vh.ivFlag);
		// vh.ivLeague.setImageResource(ai.getLeagueTypeInfo(true).logoId);
		vh.tvLeague.setText(ai.getLeagueTypeInfo(true).name);
		ImageLoader.getInstance().displayImage(
				ai.getLeagueTypeInfo(false).logoFile, vh.ivCup);
		// vh.ivCup.setImageResource(ai.getLeagueTypeInfo(false).logoId);
		vh.tvCup.setText(ai.getLeagueTypeInfo(false).name);
		vh.llLeague.setTag(R.string.league_or_cup, AreaInfo.TYPE_LEAGUE);
		vh.llCup.setTag(R.string.league_or_cup, AreaInfo.TYPE_CUP);
		return convertView;
	}

	OnClickListener clickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int position = (Integer) v.getTag();
			boolean isLeague = (Integer) v.getTag(R.string.league_or_cup) == 1 ? true
					: false;
			Intent intent = new Intent(mContext, LeagueActivity.class);
			intent.putExtra("league_or_cup", isLeague);
			intent.putExtra("area_id", areaInfos.get(position).areaId);
			mContext.startActivity(intent);
			((Activity) mContext).overridePendingTransition(
					R.anim.base_slide_right_in, R.anim.stay_anim);
		}
	};

	private class ViewHolder {
		LinearLayout llAreaInfo;
		TextView tvName;
		ImageView ivFlag;
		LinearLayout llLeagueType;
		LinearLayout llLeague;
		ImageView ivLeague;
		TextView tvLeague;
		LinearLayout llCup;
		ImageView ivCup;
		TextView tvCup;
	}

}
