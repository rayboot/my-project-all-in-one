package com.rayboot.pinyincrazy.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class KeyButtonView extends Button
{

    public KeyButtonView(Context context)
    {
        super(context);
    }

    public KeyButtonView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth() * 3 / 2);
    }
}
