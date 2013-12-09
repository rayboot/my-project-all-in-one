package com.rayboot.yibihuaa;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ContentAdapter<T> extends MyBaseAdapter<T> {

	private String path;

	public ContentAdapter(Context context, List<T> datas, String path) {
		super(context, datas);
		// TODO Auto-generated constructor stub
		this.path = path;
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

		String ao = (String) getItem(position);

		String urlString = "http://61.191.44.170/clt/publish/clt/resource/images/temp/"
				+ path + "/" + ao + ".jpg";
		Picasso.with(mContext).load(urlString).into(holder.ivContent);
		holder.tvContent.setText(ao + "");
		return convertView;
	}

	static class ViewHolder {
		ImageView ivContent;
		TextView tvContent;
	}
}
