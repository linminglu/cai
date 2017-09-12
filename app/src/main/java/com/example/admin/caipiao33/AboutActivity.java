package com.example.admin.caipiao33;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;

import butterknife.BindView;
import butterknife.ButterKnife;

//关于我们
public class AboutActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{

    @BindView(R.id.about_tv)
    TextView aboutTv;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
        getHomePageUrl();
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.about);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getHomePageUrl()
    {
        HttpUtil.requestSecond("setting", "aboutUs", null, String.class, AboutActivity.this, new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                aboutTv.setText(result);
            }

            @Override
            public void onFailed(int code, String msg)
            {

            }

            @Override
            public void onFinish()
            {

            }
        }, null);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }

}
