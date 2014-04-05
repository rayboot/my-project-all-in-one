package com.rayboot.pinyincrazy.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.widget.TextView;
import com.rayboot.pinyincrazy.R;

public class SquaredTextView extends TextView
{

    public SquaredTextView(Context context)
    {
        super(context);
    }

    public SquaredTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
    }

    @Override protected void onDraw(Canvas canvas)
    {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        // 设置抗锯齿
        paint.setAntiAlias(true);
        // 设置线宽
        paint.setStrokeWidth(2);
        paint.setColor(getResources().getColor(R.color.line_1));
        paint.setColor(Color.DKGRAY);
        Path path = new Path();
        path.moveTo(0, getHeight() / 2);
        path.lineTo(getWidth(), getHeight() / 2);
        PathEffect effects = new DashPathEffect(new float[] { 15, 15, 15, 15 }, 5);
        paint.setPathEffect(effects);
        canvas.drawPath(path, paint);
        Path path2 = new Path();
        path2.moveTo(getWidth() / 2, 0);
        path2.lineTo(getWidth() / 2, getHeight());
        paint.setPathEffect(effects);
        canvas.drawPath(path2, paint);
        super.onDraw(canvas);
    }
}
