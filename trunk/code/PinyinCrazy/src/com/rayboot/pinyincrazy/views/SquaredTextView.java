package com.rayboot.pinyincrazy.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class SquaredTextView extends TextView {

	public SquaredTextView(Context context) {
		super(context);
	}

	public SquaredTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
	}
}
