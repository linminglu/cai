package com.example.admin.caipiao33;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;


public class SplashActivity extends BaseActivity
{
    private Handler handler = new Handler();
    private ImageView mImageView;
    private boolean isLoadFail = true;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mImageView = (ImageView) findViewById(R.id.splash_iv);

        mImageView.setVisibility(View.VISIBLE);
        reconfirmBaseUrl();
        handler.postDelayed(initRunnbale, 500);
    }

    @Override
    public void onBackPressed()
    {
        //不允许退出
    }

    private Runnable initRunnbale = new Runnable()
    {

        @Override
        public void run()
        {
            handler.postDelayed(new Runnable()
            {
                @Override
                public void run()
                {
                    if (isLoadFail)
                    {
                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        finish();
                    }
                }
            }, 3000);
        }
    };
}

