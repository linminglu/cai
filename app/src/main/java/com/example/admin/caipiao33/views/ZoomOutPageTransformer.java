package com.example.admin.caipiao33.views;

import android.support.v4.view.ViewPager.PageTransformer;
import android.view.View;

public class ZoomOutPageTransformer implements PageTransformer
{
    private static float MIN_SCALE = 0.85f;
    private static float MIN_ALPHA = 0.5f;

    @Override
    public void transformPage(View view, float position)
    {

        if (position < -1)
        { // [-Infinity,-1)
            // This page is way off-screen to the left.
            view.setAlpha(0);
            view.setTranslationX(0);
        }
        else if (position <= 1)
        { // [-1,1]
            // Modify the default slide transition to
            // shrink the page as well
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            view.setAlpha(MIN_ALPHA + (scaleFactor - MIN_SCALE) / (1 - MIN_SCALE) * (1 - MIN_ALPHA));
        }
        else
        { // (1,+Infinity]
            // This page is way off-screen to the right.
            view.setAlpha(0);
            view.setTranslationX(0);
        }
    }
}