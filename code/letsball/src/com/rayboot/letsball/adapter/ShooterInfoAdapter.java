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
import com.rayboot.letsball.model.ShooterInfo;

public class ShooterInfoAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater mLayoutInflater;
	List<ShooterInfo> shooterInfos = new ArrayList<ShooterInfo>();

	public ShooterInfoAdapter(Context context, ArrayList<ShooterInfo> datas) {
		// TODO Auto-generated constructor stub
		shooterInfos = datas;
		mContext = context;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return shooterInfos.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return shooterInfos.get(position);
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
			convertView = mLayoutInflater.inflate(R.layout.item_shooter, null);
			vh = new ViewHolder();
			vh.tvIndex = (TextView) convertView.findViewById(R.id.tvIndex);
			vh.tvShoot = (TextView) convertView.findViewById(R.id.tvShoot);
			vh.tvPlayer = (TextView) convertView.findViewById(R.id.tvPlayer);
			vh.ivTeamLogo = (ImageView) convertView
					.findViewById(R.id.ivTeamLogo);
			vh.tvTeamName = (TextView) convertView
					.findViewById(R.id.tvTeamName);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		ShooterInfo si = shooterInfos.get(position);

		vh.tvIndex.setText(si.index);
		vh.tvShoot.setText(si.shoot);
		vh.tvPlayer.setText(si.player.name);
		ImageLoader.getInstance().displayImage(si.team.logoName, vh.ivTeamLogo);
		// vh.ivTeamLogo.setImageResource(si.team.getTeamLogo());
		vh.tvTeamName.setText(si.team.teamName);

		return convertView;
	}

	private class ViewHolder {
		TextView tvIndex;
		TextView tvShoot;
		TextView tvPlayer;
		TextView tvTeamName;
		ImageView ivTeamLogo;
	}

}
