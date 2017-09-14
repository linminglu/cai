package com.example.admin.caipiao33;

import android.annotation.SuppressLint;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.TuiJianBean;
import com.example.admin.caipiao33.contract.ITuiJianContract;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.presenter.TuiJianPresenter;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.UserConfig;
import com.example.admin.caipiao33.views.LoadingLayout;
import com.socks.library.KLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TuiJianActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener, ITuiJianContract.View
{
    @BindView(R.id.loadingLayout)
    LoadingLayout loadingLayout;
    @BindView(R.id.tv_tuijian_id)
    TextView tvTuijianId;
    @BindView(R.id.tv_tuijian_url)
    TextView tvTuijianUrl;
    @BindView(R.id.tv_tuijian_shouyi)
    TextView tvTuijianShouyi;
    @BindView(R.id.tv_tuijian_shuoming)
    TextView tvTuijianShuoming;
    @BindView(R.id.tv_tuijian_huiyuan)
    TextView tvTuijianHuiyuan;
    @BindView(R.id.tv_tuijian_huiyuannum)
    TextView tvTuijianHuiYuannum;
    private ITuiJianContract.Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuijian);
        ButterKnife.bind(this);
        initView();
        mPresenter = new TuiJianPresenter(this, null);
        mPresenter.getTuiJian();
    }

    private void initView()
    {
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                mPresenter.getTuiJian();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_tuijian, menu);
        return true;
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_tuijian);
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
                    case R.id.action_tuijianjilu:
                        Intent intent = new Intent(TuiJianActivity.this, TuiJianJiLuActivity.class);
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
    public void updata(TuiJianBean result)
    {
        int id = 1;
        try
        {
            id = Integer.valueOf(UserConfig.getInstance()
                    .getToken(TuiJianActivity.this)
                    .getMemberId());
        }
        catch (Exception e)
        {
            KLog.e(e);
        }
        tvTuijianId.setText(id + 100000 + "");
        tvTuijianUrl.setText(HttpUtil.mNewUrl + "/common/register?tj=" + UserConfig.getInstance()
                .getToken(TuiJianActivity.this)
                .getMemberId());
        tvTuijianShouyi.setText("¥" + result.getMySpread().getSpreadIn());
        tvTuijianShuoming.setText("说明：每天的7点更新收益，如3号7点，会计算2号0点-24点之间的所以胡数据，然后增加您的收益。您的收益=推荐会员的有效投注额度总和÷100 x " + result
                .getSpread() + "（转换率），小输部分四舍五入！（风险账号不参与收益计算）");
        tvTuijianHuiYuannum.setText("我的推荐会员（" + result.getMySpread().getSpreadCount() + "）");
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < result.getMySpread().getSpreadMember().size(); i++)
        {
            if (result.getMySpread().getSpreadMember().get(i).getIsDanger() == 0)
            {
                buffer.append(result.getMySpread().getSpreadMember().get(i).getCode())
                        .append("        ");
            }
            else
            {
                buffer.append(result.getMySpread().getSpreadMember().get(i).getCode())
                        .append("（风险）");
            }
        }
        tvTuijianHuiyuan.setText(buffer);
    }

    @OnClick(R.id.tv_tuijian_url)
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_tuijian_url:
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 将文本内容放到系统剪贴板里。
                cm.setText(tvTuijianUrl.getText());
                ToastUtil.show("复制成功，可以发给朋友们了!");
                break;
        }
    }
}

