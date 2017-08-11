package com.example.admin.caipiao33.views;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

public class GridView4ScrollView extends GridView
{
    public GridView4ScrollView(Context context)
    {
        super(context);
    }

    public GridView4ScrollView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public GridView4ScrollView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    /**
     * 重写该方法，达到使ListView适应ScrollView的效果
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }


}
