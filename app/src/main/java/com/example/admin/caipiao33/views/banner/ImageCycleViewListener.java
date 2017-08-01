package com.example.admin.caipiao33.views.banner;

import android.view.View;
import android.widget.ImageView;

/**
 * 轮播控件的监听事件
 * Created by cxy on 2016/4/20.
 */
public interface ImageCycleViewListener
{
    /**
     * 加载图片资源
     *
     * @param imageURL
     * @param imageView
     */
    void displayImage(String imageURL, ImageView imageView);

    /**
     * 单击图片事件
     *
     * @param position
     * @param imageView
     */
    void onImageClick(int position, View imageView);
}
