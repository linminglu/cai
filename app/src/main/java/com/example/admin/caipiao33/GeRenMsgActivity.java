package com.example.admin.caipiao33;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.GeRenMsgBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

//个人消息详情
public class GeRenMsgActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{
    @BindView(R.id.gerenmsg_title_tv)
    TextView gerenmsgTitleTv;
    @BindView(R.id.gerenmsg_content_tv)
    TextView gerenmsgContentTv;
    @BindView(R.id.gerenmsg_content_wv)
    WebView gerenmsgContentWv;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gerenmsg);
        ButterKnife.bind(this);
        id = getIntent().getStringExtra("id");
        getHomePageUrl();
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle("我的消息详情");
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void getHomePageUrl()
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", id);
        HttpUtil.requestSecond("user", "msgDetail", map, GeRenMsgBean.class, GeRenMsgActivity.this, new MyResponseListener<GeRenMsgBean>()
        {
            @Override
            public void onSuccess(GeRenMsgBean result)
            {
                gerenmsgTitleTv.setText(result.getTitle());
                //                gerenmsgContentTv.setText(result.getContent());
                gerenmsgContentWv.loadDataWithBaseURL("about:blank", result.getContent(), "text/html", "utf-8", null);
            }

            @Override
            public void onFailed(int code, String msg)
            {
                ToastUtil.show(msg);
            }

            @Override
            public void onFinish()
            {
            }
        }, null);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        return false;
    }

}
