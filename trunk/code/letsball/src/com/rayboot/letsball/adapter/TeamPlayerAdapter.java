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
import com.rayboot.letsball.model.PlayerInfo;

public class TeamPlayerAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater mLayoutInflater;
	List<Object> datas = new ArrayList<Object>();

	public TeamPlayerAdapter(Context mContext, List<Object> datas) {
		super();
		this.mContext = mContext;
		mLayoutInflater = LayoutInflater.from(mContext);
		this.datas = datas;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return datas.get(arg0);
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
		if (convertView == null) {
			convertView = mLayoutInflater.inflate(
					R.layout.item_team_detail_player, null);
			vh = new ViewHolder();
			vh.tvNum = (TextView) convertView.findViewById(R.id.tvNum);
			vh.tvPai = (TextView) convertView.findViewById(R.id.tvPai);
			vh.tvGoal = (TextView) convertView.findViewById(R.id.tvGoal);
			vh.tvOn = (TextView) convertView.findViewById(R.id.tvOn);
			vh.tvPos = (TextView) convertView.findViewById(R.id.tvPos);
			vh.tvName = (TextView) convertView.findViewById(R.id.tvName);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		PlayerInfo pi = (PlayerInfo) getItem(position);
		vh.tvNum.setText(pi.num);
		vh.tvPai.setText(pi.yCard + "/" + pi.rCard);
		vh.tvGoal.setText(pi.goal + "/" + pi.assist);
		vh.tvOn.setText(pi.on);
		vh.tvPos.setText(pi.pos);
		vh.tvName.setText(pi.name);

		return convertView;
	}

	private class ViewHolder {
		TextView tvNum;
		TextView tvPai;
		TextView tvGoal;
		TextView tvOn;
		TextView tvPos;
		TextView tvName;
	}

}
