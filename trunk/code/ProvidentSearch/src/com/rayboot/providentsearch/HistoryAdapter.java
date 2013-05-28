package com.rayboot.providentsearch;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import butterknife.InjectView;
import butterknife.Views;

public class HistoryAdapter extends BaseAdapter {
	Context mContext;
	LayoutInflater mLayoutInflater;
	List<ProvidentObj> datas;

	public HistoryAdapter(Context context, List<ProvidentObj> list) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.datas = list;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (datas == null) {
			return 0;
		}
		return datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas.get(position);
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
			convertView = mLayoutInflater.inflate(R.layout.item_history, null);
			vh = new ViewHolder(convertView);
			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}
		ProvidentObj pdo = (ProvidentObj) getItem(position);
		if (pdo != null && !TextUtils.isEmpty(pdo.idCard) && pdo.idCard.length() >= 18) {
			String showHide = pdo.idCard.substring(0, 8) + "******"
					+ pdo.idCard.substring(pdo.idCard.length() - 4);
			vh.tvCard.setText(showHide);
		}
		return convertView;
	}

	static class ViewHolder {
		@InjectView(R.id.tvCard)
		TextView tvCard;

		public ViewHolder(View view) {
			Views.inject(this, view);
		}
	}
}
