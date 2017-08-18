package com.example.admin.caipiao33.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.GridView;

import com.example.admin.caipiao33.R;

/**
 * 设置最大高度的gridview
 */

public class MaxHeightGridView extends GridView
{
    public static int WITHOUT_MAX_HEIGHT_VALUE = -1;
    private int maxHeight = WITHOUT_MAX_HEIGHT_VALUE;

    public MaxHeightGridView(Context context)
    {
        super(context);
    }

    public MaxHeightGridView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MaxHeightGridView, 0, 0);
        try {
            setMaxHeight((int) a.getDimension(R.styleable.MaxHeightGridView_maxHeight, WITHOUT_MAX_HEIGHT_VALUE));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        try {
            int heightSize = MeasureSpec.getSize(heightMeasureSpec);
            if (maxHeight != WITHOUT_MAX_HEIGHT_VALUE
                    && heightSize > maxHeight) {
                heightSize = maxHeight;
            }
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize, MeasureSpec.AT_MOST);
            getLayoutParams().height = heightSize;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void setMaxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
    }
}
