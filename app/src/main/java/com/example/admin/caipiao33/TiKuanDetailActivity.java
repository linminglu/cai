package com.example.admin.caipiao33;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.TiKuanDetailBean;
import com.example.admin.caipiao33.contract.ITiKuanDetailContract;
import com.example.admin.caipiao33.presenter.TiKuanDetailPresenter;
import com.example.admin.caipiao33.views.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

//提款详情
public class TiKuanDetailActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, ITiKuanDetailContract.View
{
    @BindView(R.id.tikuandetail_dingdanhao_tv)
    TextView tikuandetailDingdanhaoTv;
    @BindView(R.id.tikuandetail_leixing_tv)
    TextView tikuandetailLeixingTv;
    @BindView(R.id.tikuandetail_tikuanjine_tv)
    TextView tikuandetailTikuanjineTv;
    @BindView(R.id.tikuandetail_shouxvfei_tv)
    TextView tikuandetailShouxvfeiTv;
    @BindView(R.id.tikuandetail_xingzhengfei_tv)
    TextView tikuandetailXingzhengfeiTv;
    @BindView(R.id.tikuandetail_zhuangtai_tv)
    TextView tikuandetailZhuangtaiTv;
    @BindView(R.id.tikuandetail_time_tv)
    TextView tikuandetailTimeTv;
    @BindView(R.id.tikuandetail_remark_tv)
    TextView tikuandetailRemarkTv;
    private String id;
    private ITiKuanDetailContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tikuandetail);
        ButterKnife.bind(this);
        mPresenter = new TiKuanDetailPresenter(this, null);
        id = getIntent().getStringExtra("id");
        mPresenter.getTiKuanDetail(id);
        initView();
    }

    private void initView()
    {
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                mPresenter.getTiKuanDetail(id);
            }
        });
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle("提款详情");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }

    @Override
    public void updata(TiKuanDetailBean result)
    {
        tikuandetailDingdanhaoTv.setText(result.getOrderNo());
        tikuandetailLeixingTv.setText(result.getAccountCode().equals("-") ? "手动扣款" : "会员出款");
        tikuandetailTikuanjineTv.setText(result.getAmount());
        tikuandetailShouxvfeiTv.setText(result.getFee());
        tikuandetailXingzhengfeiTv.setText(result.getFeeAdmin());
        if (result.getStatus() == 0)
        {
            tikuandetailZhuangtaiTv.setText("待审核");
        }
        else if (result.getStatus() == -1)
        {
            tikuandetailZhuangtaiTv.setText("已取消");
        }
        else if (result.getStatus() == 1)
        {
            tikuandetailZhuangtaiTv.setText("已提款");
        }
        else if (result.getStatus() == 2)
        {
            tikuandetailZhuangtaiTv.setText("审核中");
        }
        tikuandetailTimeTv.setText(result.getAddTime());
    }
}

