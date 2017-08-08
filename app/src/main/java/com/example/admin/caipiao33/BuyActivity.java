package com.example.admin.caipiao33;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.views.LoadingLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 调用者需要传入房间id
 *   -- Constants.EXTRA_ROOM_ID
 *
 * 两种情况
 * room
 *  -- 显示房间列表，再次选择一项进入page页面
 * page
 *  -- 立即购买页面
 */
public class BuyActivity extends BaseActivity
{

    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.center_frame)
    RelativeLayout centerFrame;
    private String mRoomId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy);
        initView();
        mRoomId = getIntent().getStringExtra(Constants.EXTRA_ROOM_ID);
        HashMap<String, String> map = new HashMap<>();
        map.put("gid", mRoomId);
        HttpUtil.requestFirst("buy", map, String.class, this, new MyResponseListener()
        {
            @Override
            public void onSuccess(Object result)
            {

            }

            @Override
            public void onFailed(int code, String msg)
            {

            }

            @Override
            public void onFinish()
            {

            }
        }, null);
    }

    private void initView()
    {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mLoadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
//                mPresenter.loadData();
            }
        });
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
