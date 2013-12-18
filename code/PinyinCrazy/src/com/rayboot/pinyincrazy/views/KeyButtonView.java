package com.rayboot.pinyincrazy.views;

import org.holoeverywhere.widget.Button;

import android.R.integer;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;

import com.rayboot.pinyincrazy.R;

public class KeyButtonView extends Button {

	public KeyButtonView(Context context) {
		super(context);
	}

	public KeyButtonView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth() * 3 / 2);
	}
}
