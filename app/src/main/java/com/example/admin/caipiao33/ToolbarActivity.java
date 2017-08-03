package com.example.admin.caipiao33;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by lsd on 2016/3/15.
 */
public abstract class ToolbarActivity extends BaseActivity
{
    private ToolbarHelper mToolbarHelper;

    @Override
    public void setContentView(@LayoutRes int layoutResID)
    {
        super.setContentView(layoutResID);
        mToolbarHelper = new ToolbarHelper(this, layoutResID);
        Toolbar toolbar = mToolbarHelper.getToolbar();
        setContentView(mToolbarHelper.getContentView());
        /* 设置支持ActionBar */
        setSupportActionBar(toolbar);
        /* 自定义的一些操作 */
        onCreateCustomToolBar(toolbar);
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        toolbar.setContentInsetsRelative(0, 0);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
