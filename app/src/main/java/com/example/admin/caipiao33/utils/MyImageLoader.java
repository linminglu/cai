package com.example.admin.caipiao33.utils;

import android.content.Context;
import android.util.Base64;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.encryption.Base64Utils;

import java.io.File;

/**
 * Created by cxy on 2016/3/21.
 */
public class MyImageLoader
{

    public static void displayImage(String uri, ImageView imageView, Context context)
    {
        if (null == imageView)
        {
            return;
        }
        if (!StringUtils.isEmpty2(uri) && context != null)
        {
            Glide.with(context).load(uri).placeholder(R.mipmap.beijing).into(imageView);
            //            Picasso.with(context).load(uri).resize(250, 250).into(imageView);
        }
        else
        {
            imageView.setImageResource(R.mipmap.photo_error);
        }
    }

    public static void displayBase64Image(String uri, ImageView imageView, Context context)
    {
        if (null == imageView)
        {
            return;
        }
        if (!StringUtils.isEmpty2(uri) && context != null)
        {
            Glide.with(context)
                    .load(Base64.decode(uri, Base64.DEFAULT))
                    .placeholder(R.mipmap.beijing)
                    .into(imageView);
        }
        else
        {
            imageView.setImageResource(R.mipmap.photo_error);
        }
    }

    public static void displayCircleImage(String uri, ImageView imageView, Context context)
    {
        if (null == imageView)
        {
            return;
        }
        if (!StringUtils.isEmpty2(uri) && context != null)
        {
            Glide.with(context)
                    .load(uri)
                    .placeholder(R.mipmap.beijing)
                    .transform(new GlideCircleTransform(context))
                    .into(imageView);
            //            Picasso.with(context).load(uri).resize(250, 250).into(imageView);
        }
        else
        {
            imageView.setImageResource(R.mipmap.photo_error);
        }
    }

    public static void displayImageNoDefault(String uri, ImageView imageView, Context context)
    {
        if (null == imageView)
        {
            return;
        }
        if (!StringUtils.isEmpty2(uri) && context != null)
        {
            Glide.with(context).load(uri).into(imageView);
            //            Picasso.with(context).load(uri).resize(250, 250).into(imageView);
        }
        else
        {
            //            imageView.setImageResource(R.mipmap.beijing);
        }
    }

    public static void displayImage(String uri, ImageView imageView, Context context, int width, int height)
    {
        if (null == imageView)
        {
            return;
        }
        if (!StringUtils.isEmpty2(uri) && context != null)
        {
            Glide.with(context).load(uri).placeholder(R.mipmap.beijing).into(imageView);
            //            Picasso.with(context).load(uri).resize(width, height).into(imageView);
        }
        else
        {
            imageView.setImageResource(R.mipmap.photo_error);
        }
    }

    public static void displayImage1(String uri, ImageView imageView, Context context, int width, int height, RequestListener listener)
    {
        if (null == imageView)
        {
            return;
        }
        if (!StringUtils.isEmpty2(uri) && context != null)
        {
            Glide.with(context).load(uri).listener(listener).dontAnimate().into(imageView);
        }
        else
        {
            imageView.setImageResource(R.mipmap.photo_error);
        }
    }

    public static void displayNativeImage(String uri, ImageView imageView, Context context)
    {
        if (null == imageView)
        {
            return;
        }
        if (!StringUtils.isEmpty2(uri) && context != null)
        {
            Glide.with(context).load(uri).into(imageView);
        }
        else
        {
            imageView.setImageResource(R.mipmap.photo_error);
        }
    }

    public static void displayImageForList(String uri, ImageView imageView, Context context)
    {
        if (null == imageView)
        {
            return;
        }
        if (!StringUtils.isEmpty2(uri) && context != null)
        {
            Glide.with(context).load(uri).placeholder(R.mipmap.beijing).crossFade().into(imageView);
        }
        else
        {
            imageView.setImageResource(R.mipmap.beijing);
        }
    }

    public static void displayImageForListAd(ImageView imageView, String uri, Context context, RequestListener<String, GlideDrawable> listener)
    {
        if (null == imageView)
        {
            return;
        }
        if (!StringUtils.isEmpty2(uri) && context != null)
        {
            Glide.with(context)
                    .load(uri)
                    .placeholder(R.mipmap.default_ad_load)
                    .crossFade()
                    .listener(listener)
                    .into(imageView);
        }
        else
        {
            imageView.setImageResource(R.mipmap.default_ad_load);
        }
    }

    public static void displayNativeImageForList(String file, ImageView imageView, Context context)
    {
        if (null == imageView)
        {
            return;
        }
        if (null != file && context != null)
        {
            Glide.with(context)
                    .load(new File(file))
                    .centerCrop()
                    .placeholder(R.mipmap.photo_error)
                    .crossFade()
                    .into(imageView);
        }
        else
        {
            imageView.setImageResource(R.mipmap.photo_error);
        }
    }

    public static void displayResourceImage(int id, ImageView imageView, Context context)
    {
        if (null == imageView)
        {
            return;
        }
        if (0 != id && context != null)
        {
            Glide.with(context).load(id).placeholder(R.mipmap.beijing).into(imageView);
        }
        else
        {
            imageView.setImageResource(R.mipmap.photo_error);
        }
    }
}
