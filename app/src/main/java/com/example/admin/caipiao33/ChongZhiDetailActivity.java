package com.example.admin.caipiao33;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.ChongZhiDetailBean;
import com.example.admin.caipiao33.contract.IChongZhiDetailContract;
import com.example.admin.caipiao33.presenter.ChongZhiDetailPresenter;
import com.example.admin.caipiao33.views.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

//充值详情
public class ChongZhiDetailActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, IChongZhiDetailContract.View
{

    @BindView(R.id.chongzhidetail_dingdanhao_tv)
    TextView chongzhidetailDingdanhaoTv;
    @BindView(R.id.chongzhidetail_leixing_tv)
    TextView chongzhidetailLeixingTv;
    @BindView(R.id.chongzhidetail_cunkuanjine_tv)
    TextView chongzhidetailCunkuanjineTv;
    @BindView(R.id.chongzhidetail_youhuijine_tv)
    TextView chongzhidetailYouhuijineTv;
    @BindView(R.id.chongzhidetail_zengsongjine_tv)
    TextView chongzhidetailZengsongjineTv;
    @BindView(R.id.chongzhidetail_zuizhongcunru_tv)
    TextView chongzhidetailZuizhongcunruTv;
    @BindView(R.id.chongzhidetail_zhuangtai_tv)
    TextView chongzhidetailZhuangtaiTv;
    @BindView(R.id.chongzhidetail_time_tv)
    TextView chongzhidetailTimeTv;
    @BindView(R.id.chongzhidetail_remark_tv)
    TextView chongzhidetailRemarkTv;
    private IChongZhiDetailContract.Presenter mPresenter;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chongzhidetail);
        ButterKnife.bind(this);
        mPresenter = new ChongZhiDetailPresenter(this, null);
        id = getIntent().getStringExtra("id");
        mPresenter.getChongZhiDetail(id);
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
                mPresenter.getChongZhiDetail(id);
            }
        });
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle("充值详情");
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
    public void updata(ChongZhiDetailBean result)
    {
        chongzhidetailDingdanhaoTv.setText(result.getOrderNo());
        chongzhidetailLeixingTv.setText(result.getType());
        chongzhidetailCunkuanjineTv.setText(result.getAmount());
        chongzhidetailYouhuijineTv.setText(result.getAddAmount());
        chongzhidetailZengsongjineTv.setText(result.getGiftAmount());
        chongzhidetailZuizhongcunruTv.setText(result.getFAmount());
        if (result.getStatus() == 0)
        {
            chongzhidetailZhuangtaiTv.setText("待审核");
        }
        else if (result.getStatus() == 1)
        {
            chongzhidetailZhuangtaiTv.setText("已存入");
        }
        else
        {
            chongzhidetailZhuangtaiTv.setText("已取消");
        }
        chongzhidetailTimeTv.setText(result.getSaveTime());
        chongzhidetailRemarkTv.setText(result.getRemark());
    }
}

