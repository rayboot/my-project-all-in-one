package com.rayboot.letsball.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.rayboot.letsball.R;
import com.rayboot.letsball.model.MatchInfo;

public class MatchInfoAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater mLayoutInflater;
	List<MatchInfo> matchInfos = new ArrayList<MatchInfo>();

	public MatchInfoAdapter(Context context, List<MatchInfo> matchList) {
		// TODO Auto-generated constructor stub
		matchInfos = matchList;
		mContext = context;
		if (mContext != null) {
			mLayoutInflater = LayoutInflater.from(mContext);
		}
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return matchInfos.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return matchInfos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder vh = null;
		if (mLayoutInflater == null) {
			return null;
		}
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(R.layout.item_match_info,
					null);
			vh = new ViewHolder();
			vh.llHome = (LinearLayout) convertView.findViewById(R.id.llHome);
			vh.ivHome = (ImageView) convertView.findViewById(R.id.ivHome);
			vh.tvHome = (TextView) convertView.findViewById(R.id.tvHome);
			vh.llScore = (LinearLayout) convertView.findViewById(R.id.llScore);
			vh.tvScore = (TextView) convertView.findViewById(R.id.tvScore);
			vh.tvStatus = (TextView) convertView.findViewById(R.id.tvStatues);
			vh.llAway = (LinearLayout) convertView.findViewById(R.id.llAway);
			vh.ivAway = (ImageView) convertView.findViewById(R.id.ivAway);
			vh.tvAway = (TextView) convertView.findViewById(R.id.tvAway);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		MatchInfo ai = (MatchInfo) getItem(position);

		vh.llHome.setTag(ai.getHomeTeamInfo().detailUrl);

		ImageLoader.getInstance().displayImage(ai.getHomeTeamInfo().logoName,
				vh.ivHome);
		// vh.ivHome.setImageResource(ai.getHomeTeamInfo().getTeamLogo());
		// vh.ivHome.setBackgroundResource(ai.home.resTeamLogo);
		vh.tvHome.setText(ai.getHomeTeamInfo().teamName);

		vh.llScore.setTag(ai.detailUrl);
		vh.tvScore.setText(ai.score);

		vh.tvStatus.setText(ai.getStatusString());

		vh.llAway.setTag(ai.getAwayTeamInfo().detailUrl);
		ImageLoader.getInstance().displayImage(ai.getAwayTeamInfo().logoName,
				vh.ivAway);
		// vh.ivAway.setImageResource(ai.getAwayTeamInfo().getTeamLogo());
		// vh.ivAway.setBackgroundResource(ai.away.resTeamLogo);
		vh.tvAway.setText(ai.getAwayTeamInfo().teamName);

		return convertView;
	}

	private class ViewHolder {
		LinearLayout llHome;
		ImageView ivHome;
		TextView tvHome;
		LinearLayout llScore;
		TextView tvScore;
		TextView tvStatus;
		LinearLayout llAway;
		ImageView ivAway;
		TextView tvAway;
	}

}
