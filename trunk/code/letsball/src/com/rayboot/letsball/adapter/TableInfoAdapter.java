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

import com.nostra13.universalimageloader.core.ImageLoader;
import com.rayboot.letsball.R;
import com.rayboot.letsball.model.TableInfo;

public class TableInfoAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater mLayoutInflater;
	List<TableInfo> tableInfos = new ArrayList<TableInfo>();

	public TableInfoAdapter(Context context, ArrayList<TableInfo> datas) {
		// TODO Auto-generated constructor stub
		tableInfos = datas;
		mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return tableInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return tableInfos.get(position);
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
			convertView = mLayoutInflater.inflate(R.layout.item_table, null);
			vh = new ViewHolder();
			vh.tvIndex = (TextView) convertView.findViewById(R.id.tvIndex);
			vh.ivLogo = (ImageView) convertView.findViewById(R.id.ivLogo);
			vh.tvTeamName = (TextView) convertView
					.findViewById(R.id.tvTeamName);
			vh.tvScore = (TextView) convertView.findViewById(R.id.tvScore);
			vh.tvRound = (TextView) convertView.findViewById(R.id.tvRound);
			vh.tvBalls = (TextView) convertView.findViewById(R.id.tvBalls);
			vh.tvWin = (TextView) convertView.findViewById(R.id.tvWin);
			vh.tvDraw = (TextView) convertView.findViewById(R.id.tvDraw);
			vh.tvLoss = (TextView) convertView.findViewById(R.id.tvLoss);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		TableInfo ti = tableInfos.get(position);

		vh.tvIndex.setText(ti.index);
		ImageLoader.getInstance().displayImage(ti.team.logoName, vh.ivLogo);
		// vh.ivLogo.setImageResource(ti.team.getTeamLogo());
		vh.tvTeamName.setText(ti.team.teamName);
		vh.tvScore.setText(ti.sorce);
		vh.tvRound.setText(ti.roundNum);
		vh.tvBalls.setText(ti.winBall + "/" + ti.lossBall);
		vh.tvWin.setText(ti.win);
		vh.tvDraw.setText(ti.draw);
		vh.tvLoss.setText(ti.loss);

		return convertView;
	}

	private class ViewHolder {
		TextView tvIndex;
		ImageView ivLogo;
		TextView tvTeamName;
		TextView tvScore;
		TextView tvRound;
		TextView tvBalls;
		TextView tvWin;
		TextView tvDraw;
		TextView tvLoss;
	}

}
