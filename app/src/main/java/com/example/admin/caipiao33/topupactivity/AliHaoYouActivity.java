package com.example.admin.caipiao33.topupactivity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.ToolbarActivity;
import com.example.admin.caipiao33.bean.AliPayBean;
import com.example.admin.caipiao33.bean.WeiXinPayBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.MyImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AliHaoYouActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{
    @BindView(R.id.alihaoyou_title)
    TextView alihaoyouTitle;
    @BindView(R.id.alihaoyou_subtitle)
    TextView alihaoyou_Subtitle;
    @BindView(R.id.alihaoyou_name)
    TextView alihaoyouName;
    @BindView(R.id.alihaoyou_erweima)
    ImageView alihaoyouErweima;
    private AliPayBean aliPayBean;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alihaoyou);
        ButterKnife.bind(this);
        aliPayBean = (AliPayBean) getIntent().getSerializableExtra(Constants.EXTRA_TOPUP_WEIXIN);
        initView();
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_ali_tianjiahaoyou);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView()
    {
        alihaoyouTitle.setText(aliPayBean.getPayName());
        alihaoyou_Subtitle.setText(aliPayBean.getPayDesc());
        alihaoyouName.setText("支付宝账号：" + aliPayBean.getCode() + "    支付宝昵称：" + aliPayBean.getName());
        MyImageLoader.displayImage(HttpUtil.mNewUrl + "/" + aliPayBean.getPayImg(), alihaoyouErweima, this);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }
}

