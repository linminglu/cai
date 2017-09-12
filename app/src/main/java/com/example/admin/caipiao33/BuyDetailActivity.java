package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.encryption.CreateCode;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Constants;
import com.socks.library.KLog;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 投注中奖记录
 *
 * 需要调用者传入
 *     Constants.EXTRA_BUY_RECORD_ID
 *     Constants.EXTRA_BUY_GAME_ID
 */
public class BuyDetailActivity extends BaseActivity
{
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.webView)
    WebView webView;
    @BindView(R.id.progressbar)
    ProgressBar mProgressbar;
    private String mRecordId;
    private String mUrl;
    private TextToSpeech tts;
    private String mGameId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_detail);
        Intent intent = getIntent();
        mRecordId = intent.getStringExtra(Constants.EXTRA_BUY_RECORD_ID);
        mGameId = intent.getStringExtra(Constants.EXTRA_BUY_GAME_ID);
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
        HashMap<String, String> map = new HashMap<>();
        map.put("id", mRecordId);
        mUrl = HttpUtil.getRequestSecondUrl("user", "betDetail", map);
        webView.loadUrl(mUrl);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient()
        {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                mProgressbar.setVisibility(View.VISIBLE);
                mUrl = url;
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl)
            {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });

        webView.setWebChromeClient(new WebChromeClient()
        {
            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100)
                {
                    mProgressbar.setVisibility(View.GONE);
                }
                else
                {
                    mProgressbar.setProgress(newProgress);
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title)
            {
                super.onReceivedTitle(view, title);
            }
        });
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    @OnClick({R.id.tv_again})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.tv_again: // 再来一注
                showLoadingDialog();
                HashMap<String, String> map = new HashMap<>();
                map.put("gid", mGameId);
                HttpUtil.requestFirst("buy", map, BuyRoomBean.class, BuyDetailActivity.this, new MyResponseListener<BuyRoomBean>()
                {
                    @Override
                    public void onSuccess(BuyRoomBean result)
                    {
                        /**
                         * 两种情况
                         * room
                         *  -- 显示房间列表，再次选择一项进入page页面
                         * page
                         *  -- 立即购买页面
                         */

                        if (result.getPage().equals("room"))
                        {
                            Intent intent = new Intent(BuyDetailActivity.this, BuyRoomActivity.class);
                            intent.putExtra(Constants.EXTRA_TITLE, result.getPlayName());
                            intent.putExtra(Constants.EXTRA_BUY_ROOM_BEAN, result);
                            startActivity(intent);
                            finish();
                        }
                        else
                        {
                            String roomId = result.getRoomId();
                            String playId = null;
                            String playId1 = null;
                            List<BuyRoomBean.PlayListBean> playList = result.getPlayList();
                            if (null != playList && playList.size() > 0)
                            {
                                BuyRoomBean.PlayListBean playListBean = playList.get(0);
                                playId = playListBean.getPlayId();
                                playId1 = playListBean.getPlayId1();
                            }
                            Intent intent = new Intent(BuyDetailActivity.this, BuyActivity.class);
                            intent.putExtra(Constants.EXTRA_TITLE, result.getPlayName());
                            intent.putExtra(Constants.EXTRA_NUMBER, result.getNum());
                            intent.putExtra(Constants.EXTRA_ROOM_ID, roomId);
                            intent.putExtra(Constants.EXTRA_PLAY_ID, playId);
                            intent.putExtra(Constants.EXTRA_PLAY_ID1, playId1);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onFailed(int code, String msg)
                    {

                    }

                    @Override
                    public void onFinish()
                    {
                        hideLoadingDialog();
                    }
                }, null);
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
