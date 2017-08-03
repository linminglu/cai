package com.example.admin.caipiao33;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;


public class LoginActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_user_login);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }
}

