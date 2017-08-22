package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.QianDaoBean;
import com.example.admin.caipiao33.contract.IQianDaoContract;
import com.example.admin.caipiao33.presenter.QianDaoPresenter;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.views.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class QianDaoActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, IQianDaoContract.View
{

    @BindView(R.id.tv_qiandao_yue)
    TextView tvQiandaoYue;
    @BindView(R.id.tv_qiandao_jilu1)
    TextView tvQiandaoJilu1;
    @BindView(R.id.tv_qiandao_jilu2)
    TextView tvQiandaoJilu2;
    @BindView(R.id.tv_qiandao_jilu3)
    TextView tvQiandaoJilu3;
    @BindView(R.id.tv_qiandao_zongji)
    TextView tvQiandaoZongji;
    @BindView(R.id.tv_qiandao_zengsong1)
    TextView tvQiandaoZengsong1;
    @BindView(R.id.tv_qiandao_zengsong2)
    TextView tvQiandaoZengsong2;
    @BindView(R.id.tv_qiandao_hongbao)
    TextView tvQiandaoHongbao;
    @BindView(R.id.btn_qiandao_ok)
    Button btnQiandaoOk;
    private IQianDaoContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qiandao);
        ButterKnife.bind(this);
        initView();
        mPresenter = new QianDaoPresenter(this, null);
        mPresenter.getQianDao();
    }

    private void initView()
    {
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                mPresenter.getQianDao();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_qiandao, menu);
        return true;
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_qiandao);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener()
        {
            @Override
            public boolean onMenuItemClick(MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.action_qiandaojilu:
                        Intent intent = new Intent(QianDaoActivity.this, QianDaoJiLuActivity.class);
                        startActivity(intent);
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void updata(QianDaoBean result)
    {
        tvQiandaoYue.setText(result.getBalance() + "元");
        tvQiandaoJilu1.setText(result.getRechargeList()
                .get(0)
                .getKey() + "   " + result.getRechargeList().get(0).getValue());
        tvQiandaoJilu2.setText(result.getRechargeList()
                .get(1)
                .getKey() + "   " + result.getRechargeList().get(1).getValue());
        tvQiandaoJilu3.setText(result.getRechargeList()
                .get(2)
                .getKey() + "   " + result.getRechargeList().get(2).getValue());
        tvQiandaoZongji.setText(result.getRechargeList()
                .get(3)
                .getKey() + "   " + result.getRechargeList().get(3).getValue());
        tvQiandaoZengsong1.setText(result.getPercent().get(0).getMin() + "~" + result.getPercent()
                .get(0)
                .getMax() + "    " + result.getPercent().get(0).getValue());
        tvQiandaoZengsong2.setText(result.getPercent()
                .get(1)
                .getMin() + "    " + result.getPercent().get(1).getValue());
        tvQiandaoHongbao.setText(result.getGiftAmount() + "元");
    }

    @Override
    public void submit(String result)
    {
        ToastUtil.show("签到成功！");
        Intent intent = new Intent(QianDaoActivity.this, QianDaoJiLuActivity.class);
        startActivity(intent);
        finish();
    }

    @OnClick(R.id.btn_qiandao_ok)
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_qiandao_ok:
                mPresenter.submitQianDao();
                break;
        }
    }
}

