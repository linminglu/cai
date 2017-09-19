package com.example.admin.caipiao33.views.loadmore;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.utils.Tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lsd on 2016/5/25.
 */
public class PointProgressView extends View implements IProgressView
{
    //Sizes (with defaults in DP)
    private static final int DEFAULT_SIZE = 30;
    private static final float SCALE = 1.0f;
    private Context mContext;
    private List<Animator> animators = new ArrayList<>();

    //scale x ,y
    private float[] scaleFloats = new float[]{SCALE, SCALE, SCALE};
    private Paint mPaint;

    public PointProgressView(Context context)
    {
        this(context, null, 0);
    }

    public PointProgressView(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public PointProgressView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        init();
    }

    private void init()
    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(getResources().getColor(R.color.red));
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        float circleSpacing = 4;
        float radius = (Math.min(getWidth(), getHeight()) - circleSpacing * 2) / 6;
        float x = getWidth() / 2 - (radius * 2 + circleSpacing);
        float y = getHeight() / 2;
        for (int i = 0; i < 3; i++)
        {
            canvas.save();
            float translateX = x + (radius * 2) * i + circleSpacing * i;
            canvas.translate(translateX, y);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            canvas.drawCircle(0, 0, radius, mPaint);
            canvas.restore();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        int width = measureDimension(Tools.dp2px(mContext, DEFAULT_SIZE), widthMeasureSpec);
        int height = measureDimension(Tools.dp2px(mContext, DEFAULT_SIZE), heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureDimension(int defaultSize, int measureSpec)
    {
        int result = defaultSize;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        if (specMode == MeasureSpec.EXACTLY)
        {
            result = specSize;
        }
        else if (specMode == MeasureSpec.AT_MOST)
        {
            result = Math.min(defaultSize, specSize);
        }
        else
        {
            result = defaultSize;
        }
        return result;
    }

    @Override
    public View getView()
    {
        return this;
    }

    @Override
    public void startAnim()
    {
        if (animators.size() > 0)
        {
            endAnim();
            for (Animator animator : animators)
            {
                animator.start();
            }
            return;
        }
        int[] delays = new int[]{120, 240, 360};
        for (int i = 0; i < 3; i++)
        {
            final int index = i;

            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.3f, 1);

            scaleAnim.setDuration(750);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);

            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
            {
                @Override
                public void onAnimationUpdate(ValueAnimator animation)
                {
                    scaleFloats[index] = (float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            scaleAnim.start();
            animators.add(scaleAnim);
        }
    }

    @Override
    public void endAnim()
    {
        for (Animator animator : animators)
        {
            if (animator.isStarted())
            {
                animator.cancel();
            }
        }
    }
}
