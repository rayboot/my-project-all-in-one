package com.rayboot.pinyincrazy.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.fourmob.tilteffect.TiltEffectAttacher;
import com.rayboot.pinyincrazy.R;
import com.rayboot.pinyincrazy.obj.MainDataObj;

public class MainAdapter<T> extends MyBaseAdapter<T> {

	public MainAdapter(Context context, List<T> datas) {
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
			convertView = this.mLayoutInflater.inflate(R.layout.item_main,
					parent, false);
			holder = new ViewHolder(convertView);
			convertView.setTag(holder);
		}
		MainDataObj mdo = (MainDataObj) getItem(position);
		holder.tvName.setText(mdo.name);
		holder.ivLogo.setBackgroundResource(mdo.imageId);
		convertView.setTag(R.string.tag_1, mdo.key);
		return convertView;
	}

	static class ViewHolder {
		@InjectView(R.id.tvName)
		TextView tvName;
		@InjectView(R.id.ivLogo)
		ImageView ivLogo;
		@InjectView(R.id.rlMain)
		RelativeLayout rlMain;

		public ViewHolder(View view) {
			ButterKnife.inject(this, view);
			TiltEffectAttacher.attach(rlMain);
		}
	}
}
