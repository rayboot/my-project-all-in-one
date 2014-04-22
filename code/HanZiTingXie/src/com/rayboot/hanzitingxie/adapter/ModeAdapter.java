package com.rayboot.hanzitingxie.adapter;

import com.rayboot.hanzitingxie.R;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ModeAdapter<T> extends MyBaseAdapter<T>
{

	public ModeAdapter(Context context, List<T> datas) {
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
			convertView = this.mLayoutInflater.inflate(R.layout.item_mode,
					parent, false);
			holder = new ViewHolder();
			holder.tvs[0] = (TextView) convertView.findViewById(R.id.tv1);
			holder.tvs[1] = (TextView) convertView.findViewById(R.id.tv2);
			holder.tvs[2] = (TextView) convertView.findViewById(R.id.tv3);
			convertView.setTag(holder);
		}

		String name = (String) getItem(position);
		for (int i = 0; i < name.length(); i++) {
			holder.tvs[i].setText(name.subSequence(i, i + 1));
			holder.tvs[i].setVisibility(View.VISIBLE);
		}

		return convertView;
	}

	static class ViewHolder {
		TextView[] tvs = new TextView[3];

	}
}
