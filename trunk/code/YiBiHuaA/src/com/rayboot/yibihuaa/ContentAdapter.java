package com.rayboot.yibihuaa;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ContentAdapter<T> extends MyBaseAdapter<T> {

	private String path;

	private DisplayImageOptions options;

	public ContentAdapter(Context context, List<T> datas, String path) {
		super(context, datas);
		// TODO Auto-generated constructor stub
		options = new DisplayImageOptions.Builder().showStubImage(
				R.drawable.ic_launcher).build();
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
		ImageLoader.getInstance().displayImage(
				"assets://" + path + "/" + ao + ".jpg", holder.ivContent,
				options);
		holder.tvContent.setText(ao + "");
		return convertView;
	}

	static class ViewHolder {
		ImageView ivContent;
		TextView tvContent;
	}
}
