package com.rayboot.beiyingcaia;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rayboot.beiyingcaia.obj.AObj;
import com.squareup.picasso.Picasso;

public class ContentAdapter<T> extends MyBaseAdapter<T> {

	public ContentAdapter(Context context, List<T> datas) {
		super(context, datas);
		// TODO Auto-generated constructor stub
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (convertView != null) {
			holder = (ViewHolder) convertView.getTag();
		} else {
			convertView = this.mLayoutInflater.inflate(R.layout.item_content,
					parent, false);
			holder = new ViewHolder();
			holder.ivContent = (ImageView) convertView
					.findViewById(R.id.ivContent);
			holder.tvContent = (TextView) convertView
					.findViewById(R.id.tvContent);
			convertView.setTag(holder);
		}

		AObj ao = (AObj) getItem(position);
		Picasso.with(mContext)
				.load("http://61.191.44.170/clt/publish/clt/resource/images/temp/"
						+ ao.file_name).into(holder.ivContent);
		holder.tvContent.setText(ao.levelid + "");
		return convertView;
	}

	static class ViewHolder {
		ImageView ivContent;
		TextView tvContent;
	}
}
