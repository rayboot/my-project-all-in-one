package com.rayboot.pinyincrazy.views;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

public class SquaredLinearLayoutView extends LinearLayout {

	public SquaredLinearLayoutView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public SquaredLinearLayoutView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		int squared = Math.min(getMeasuredWidth(), getMeasuredHeight());
		setMeasuredDimension(squared, squared);
	}
}
