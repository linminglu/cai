package com.example.admin.caipiao33;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.admin.caipiao33.contract.IPromotionsContract;
import com.example.admin.caipiao33.presenter.PromotionsPresenter;
import com.example.admin.caipiao33.utils.Tools;
import com.example.admin.caipiao33.views.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 优惠活动
 */
public class PromotionsActivity extends ToolbarActivity implements IPromotionsContract.View
{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar mToolbar;
    private IPromotionsContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotions);
        initView();
        mPresenter = new PromotionsPresenter(this);
        mPresenter.loadData();
    }

    private void initView()
    {
        ButterKnife.bind(this);
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        Tools.setSwipeRefreshColor(swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener()
        {
            @Override
            public void onRefresh()
            {
                mPresenter.refreshData();
            }
        });
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                mPresenter.loadData();
            }
        });
    }

    @Override
    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        mToolbar = toolbar;
        toolbar.setTitle(R.string.s_promotions);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void updateHomePage(Object bean)
    {

    }

    @Override
    public void hideRefreshing()
    {
        swipeRefreshLayout.setRefreshing(false);
    }
}
