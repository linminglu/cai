package com.example.admin.caipiao33.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

/**
 * web页面进度条
 */

public class WebviewProgressBar
{

    private static final int SHAM_PROGRESS = 80;

    private ProgressBar progressBar;
    private boolean isStart = false;
    private ObjectAnimator shamAnimator;

    public WebviewProgressBar(ProgressBar progressBar)
    {
        this.progressBar = progressBar;
    }

    /**
     * 在 WebViewClient onPageStarted 调用
     */
    public void onProgressStart()
    {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setAlpha(1.0f);
    }

    /**
     * 在 WebChromeClient onProgressChange 调用
     */
    public void onProgressChange(int newProgress)
    {
        int currentProgress = progressBar.getProgress();
        if (newProgress >= 100 && !isStart)
        {
            /** 防止调用多次动画 */
            isStart = true;
            /** 开启属性动画让进度条平滑消失*/
            startDismissAnimation(currentProgress);
        }
        else
        {
            /** 开启属性动画让进度条平滑递增 */
            startProgressAnimation(newProgress, currentProgress);
        }
    }

    /**
     * progressBar 进度缓慢递增，300ms/次
     */
    private void startProgressAnimation(int newProgress, int currentProgress)
    {
        if (newProgress == 0 && shamAnimator != null && shamAnimator.isStarted()) {
            // 重复进入
            return;
        }
        if(currentProgress == 0 && newProgress == 0) {
            // 开始虚假进度到80%
            shamAnimator = ObjectAnimator.ofInt(progressBar, "progress", currentProgress, SHAM_PROGRESS);
            shamAnimator.setDuration(1500);
            shamAnimator.setInterpolator(new AccelerateDecelerateInterpolator()); /** 加速减速形式的加速器，个人喜好 */
            shamAnimator.start();
        } else if (newProgress > SHAM_PROGRESS) {
            // 取消虚假进度
            if (null != shamAnimator) {
                shamAnimator.cancel();
                shamAnimator = null;
            }
            // 实际测试中发现有连续一样的数据调用
            if (currentProgress == newProgress) {
                return;
            }
            // 开始正常进度到实际进度位置
            ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", currentProgress, newProgress);
            animator.setDuration(300);
            animator.setInterpolator(new DecelerateInterpolator()); /** 减速形式的加速器，个人喜好 */
            animator.start();
        }
    }

    private void startDismissAnimation(int progress)
    {
        // 取消虚假进度
        if (null != shamAnimator) {
            shamAnimator.cancel();
            shamAnimator = null;
        }
        if (progress < SHAM_PROGRESS) {
            progress = SHAM_PROGRESS;
        }
        ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", progress, 100);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator()); /** 减速形式的加速器，个人喜好 */
        ObjectAnimator anim = ObjectAnimator.ofFloat(progressBar, "alpha", 1.0f, 0.0f);
        anim.setDuration(1500);  // 动画时长
        anim.setInterpolator(new AccelerateInterpolator());     // 减速
        anim.addListener(new AnimatorListenerAdapter()
        {

            @Override
            public void onAnimationEnd(Animator animation)
            {
                if (null != progressBar) {
                    progressBar.setProgress(0);
                    progressBar.setVisibility(View.GONE);
                    isStart = false;
                }
            }
        });
        AnimatorSet set = new AnimatorSet();
        set.playTogether(animator, anim);
        set.start();
    }

    public void destroy()
    {
        progressBar = null;
    }
}



