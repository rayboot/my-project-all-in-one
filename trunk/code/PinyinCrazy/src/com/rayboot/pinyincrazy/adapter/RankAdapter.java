package com.rayboot.pinyincrazy.adapter;

import java.util.List;

import android.R.integer;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.rayboot.pinyincrazy.R;
import com.rayboot.pinyincrazy.obj.PinyinDataObj;

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
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}

		PinyinDataObj pdo = (PinyinDataObj) getItem(position);

		if (pdo.isRight == 1) {
			holder.tvWord.setText(pdo.keychar + "("
					+ pdo.pinyin.split(" ")[pdo.title.indexOf(pdo.keychar)]
					+ ")" + "--" + pdo.title);
		} else {
			holder.tvWord.setText(pdo.keychar + "--" + pdo.title);
		}
		holder.tvWrong.setText(pdo.wrong + "");
		return convertView;
	}

	static class ViewHolder {
		@InjectView(R.id.tvWord)
		TextView tvWord;
		@InjectView(R.id.tvWrong)
		TextView tvWrong;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
		}

	}
}
