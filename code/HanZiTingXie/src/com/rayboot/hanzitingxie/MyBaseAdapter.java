package com.rayboot.hanzitingxie;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MyBaseAdapter<T> extends BaseAdapter {
	Context mContext;
	LayoutInflater mLayoutInflater;
	List<T> datas;

	public MyBaseAdapter(Context context, List<T> datas) {
		// TODO Auto-generated constructor stub
		this.mContext = context;
		this.datas = datas;
		mLayoutInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datas == null ? 0 : datas.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datas == null ? null : datas.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}

}
