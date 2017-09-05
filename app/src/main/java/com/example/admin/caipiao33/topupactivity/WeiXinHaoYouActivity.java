package com.example.admin.caipiao33.topupactivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.ToolbarActivity;
import com.example.admin.caipiao33.bean.WeiXinPayBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.MyImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class WeiXinHaoYouActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{
    @BindView(R.id.tianjiahaoyou_title)
    TextView tianjiahaoyouTitle;
    @BindView(R.id.tianjiahaoyou_subtitle)
    TextView tianjiahaoyou_Subtitle;
    @BindView(R.id.tianjiahaoyou_name)
    TextView tianjiahaoyouName;
    @BindView(R.id.tianjiahaoyou_erweima)
    ImageView tianjiahaoyouErweima;
    private WeiXinPayBean weiXinPayBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tianjiahaoyou);
        ButterKnife.bind(this);
        weiXinPayBean = (WeiXinPayBean) getIntent().getSerializableExtra(Constants.EXTRA_TOPUP_WEIXIN);
        initView();
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_weixin_tianjiahaoyou);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView()
    {
        tianjiahaoyouTitle.setText(weiXinPayBean.getPayName());
        tianjiahaoyou_Subtitle.setText(weiXinPayBean.getPayDesc());
        tianjiahaoyouName.setText("微信账号：" + weiXinPayBean.getCode() + "    微信昵称：" + weiXinPayBean.getName());
        MyImageLoader.displayImage(HttpUtil.mNewUrl + "/" + weiXinPayBean.getPayImg(), tianjiahaoyouErweima, this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }
}

