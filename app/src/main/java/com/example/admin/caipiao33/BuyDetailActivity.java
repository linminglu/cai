package com.example.admin.caipiao33;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 投注中奖记录
 */
public class BuyDetailActivity extends BaseActivity
{
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_detail);
        ButterKnife.bind(this);
        initView();

        // 修改toolbar内容
        mToolbar.setTitle("");
        mToolbarTitle.setText("订单详情");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView()
    {
    }

    @OnClick({R.id.toolbar_title})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.toolbar_title: // 玩法选择
                break;
        }
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
