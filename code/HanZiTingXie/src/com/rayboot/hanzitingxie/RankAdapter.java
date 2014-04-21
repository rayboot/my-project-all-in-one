package com.rayboot.hanzitingxie;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rayboot.hanzitingxie.obj.WordData;

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
			holder = new ViewHolder();
			holder.tvWord = (TextView) convertView.findViewById(R.id.tvWord);
			holder.tvWrong = (TextView) convertView.findViewById(R.id.tvWrong);
			convertView.setTag(holder);
		}

		WordData sd = (WordData) getItem(position);

		holder.tvWord.setText(sd.isRight == 1 ? sd.title : sd.pinyin.toLowerCase());
		holder.tvWrong.setText(sd.wrong + "");
		return convertView;
	}

	static class ViewHolder {
		TextView tvWord;
		TextView tvWrong;

	}
}
