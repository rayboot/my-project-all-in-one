package com.rayboot.pinyincrazy.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import com.rayboot.pinyincrazy.R;

public class SquaredLinearLayoutView extends LinearLayout
{

    private float sW = -1;
    private float sH = -1;
    private boolean isFixWidth = true;

    public SquaredLinearLayoutView(Context context, float sW, float sH,
            boolean isFixWidth)
    {
        super(context);
        this.sW = sW;
        this.sH = sH;
        this.isFixWidth = isFixWidth;
    }

    public SquaredLinearLayoutView(Context context, float sW, float sH)
    {
        super(context);
        this.sW = sW;
        this.sH = sH;
        isFixWidth = true;
    }

    public SquaredLinearLayoutView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray array = context.obtainStyledAttributes(attrs,
                R.styleable.ScaledImageView);
        sW = array.getInteger(R.styleable.ScaledImageView_sWidth, -1);
        sH = array.getInteger(R.styleable.ScaledImageView_sHeight, -1);
        isFixWidth =
                array.getBoolean(R.styleable.ScaledImageView_isFixWidth, true);
        array.recycle(); //一定要调用，否则这次的设定会对下次的使用造成影响
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (sW == -1)
        {
            setMeasuredDimension(getMeasuredWidth(), getMeasuredHeight());
        }
        else
        {
            if (isFixWidth)
            {
                setMeasuredDimension(getMeasuredWidth(),
                        (int) (getMeasuredWidth() * sH / sW));
            }
            else
            {
                int w = getMeasuredWidth();
                int h = getMeasuredHeight();
                setMeasuredDimension((int) (getMeasuredHeight() * sW / sH),
                        getMeasuredHeight());
            }
        }
    }
}
