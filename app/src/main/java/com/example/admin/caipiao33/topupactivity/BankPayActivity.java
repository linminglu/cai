package com.example.admin.caipiao33.topupactivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.example.admin.caipiao33.ChongZhiJiLuActivity;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.ToolbarActivity;
import com.example.admin.caipiao33.WebUrlActivity;
import com.example.admin.caipiao33.bean.BankPayDetailBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.StringUtils;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.TopupEvent;
import com.example.admin.caipiao33.views.LoadingLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class BankPayActivity extends ToolbarActivity implements Toolbar.OnMenuItemClickListener
{
    @BindView(R.id.bankpay_dingdanhao_tv)
    TextView bankpayDingdanhaoTv;
    @BindView(R.id.bankpay_weixinhao_tv)
    TextView bankpayWeixinhaoTv;
    @BindView(R.id.bankpay_weixinming_tv)
    TextView bankpayWeixinmingTv;
    @BindView(R.id.bankpay_zhanghao_tv)
    TextView bankpayZhanghaoTv;
    @BindView(R.id.bankpay_wangdian_tv)
    TextView bankpayWangdianTv;
    @BindView(R.id.bankpay_time_tv)
    TextView bankpayTimeTv;
    @BindView(R.id.bankpay_amount_tv)
    TextView bankpayAmountTv;
    @BindView(R.id.bankpay_name_et)
    EditText bankpayNameEt;
    @BindView(R.id.bankpay_yinhangzhuanzhang_cb)
    CheckBox bankpayYinhangzhuanzhangCb;
    @BindView(R.id.bankpay_atmzidongguiyuanji_cb)
    CheckBox bankpayAtmzidongguiyuanjiCb;
    @BindView(R.id.bankpay_atmxianjinrukuan_cb)
    CheckBox bankpayAtmxianjinrukuanCb;
    @BindView(R.id.bankpay_yinhangguitaizhuanzhang_cb)
    CheckBox bankpayYinhangguitaizhuanzhangCb;
    @BindView(R.id.bankpay_shoujiyinhangzhuanzhang_cb)
    CheckBox bankpayShoujiyinhangzhuanzhangCb;
    @BindView(R.id.bankpay_qita_cb)
    CheckBox bankpayQitaCb;
    @BindView(R.id.bankpay_shangyibu)
    Button bankpayShangyibu;
    @BindView(R.id.bankpay_ok)
    Button bankpayOk;
    private String topupamount;
    private String payId;
    private String type = "1";
    private BankPayDetailBean bankPayDetailBean;
    private ArrayList<CheckBox> boxes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bankpay);
        ButterKnife.bind(this);
        topupamount = getIntent().getStringExtra(Constants.EXTRA_TOPUP_TOPUPAMOUNT);
        payId = getIntent().getStringExtra(Constants.EXTRA_TOPUP_PAYID);
        initData();
        initView();
    }

    private void initData()
    {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", payId);
        map.put("money", topupamount);

        HttpUtil.requestSecond("user", "rbankNext", map, BankPayDetailBean.class, BankPayActivity.this, new MyResponseListener<BankPayDetailBean>()
        {
            @Override
            public void onSuccess(BankPayDetailBean result)
            {
                bankPayDetailBean = result;
                bankpayDingdanhaoTv.setText(result.getOrderNo());
                if (result.getBank() != null)
                {
                    bankpayWeixinhaoTv.setText(result.getBank().getBankName());
                    bankpayWeixinmingTv.setText(result.getBank().getAccountName());
                    bankpayZhanghaoTv.setText(result.getBank().getAccountCode());
                    bankpayWangdianTv.setText(result.getBank().getBankAddr());
                }
                bankpayTimeTv.setText(result.getSaveTime());
                bankpayAmountTv.setText(result.getMoney());
                hideLoadingLayout();
            }

            @Override
            public void onFailed(int code, String msg)
            {
                ToastUtil.show(msg);
                showLoadingLayoutError();
            }

            @Override
            public void onFinish()
            {
            }
        }, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu_topup, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_jilu: // 充值记录.
                Intent intent = new Intent(BankPayActivity.this, ChongZhiJiLuActivity.class);
                startActivity(intent);
                break;
            case R.id.action_kefu: // 在线客服
                showLoadingDialog();
                HttpUtil.requestFirst("kefu", String.class, BankPayActivity.this, new MyResponseListener<String>()
                {
                    @Override
                    public void onSuccess(String result)
                    {
                        toWebUrlActivity(result, "在线客服");
                    }

                    @Override
                    public void onFailed(int code, String msg)
                    {
                        ToastUtil.show(msg);
                    }

                    @Override
                    public void onFinish()
                    {
                        hideLoadingDialog();
                    }
                }, null);
                break;
            default:
                break;
        }
        return false;
    }


    // 跳转到网页
    private void toWebUrlActivity(String url, String title)
    {
        Intent intent = new Intent(BankPayActivity.this, WebUrlActivity.class);
        intent.putExtra(Constants.EXTRA_URL, url);
        intent.putExtra(Constants.EXTRA_TITLE, title);
        startActivity(intent);
    }

    private void toNext()
    {
        showLoadingDialog(false);

        HashMap<String, String> map = new HashMap<>();
        map.put("name", bankpayNameEt.getText().toString());
        map.put("time", bankPayDetailBean.getSaveTime());
        map.put("money", topupamount);
        map.put("orderNo", bankPayDetailBean.getOrderNo());
        map.put("type", type);

        HttpUtil.requestSecond("user", "rbankSubmit", map, String.class, BankPayActivity.this, new MyResponseListener<String>()
        {
            @Override
            public void onSuccess(String result)
            {
                ToastUtil.show("提交成功，请稍后查询充值记录！");
                finish();
                EventBus.getDefault().post(new TopupEvent(""));
            }

            @Override
            public void onFailed(int code, String msg)
            {
                ToastUtil.show(msg);
            }

            @Override
            public void onFinish()
            {
                hideLoadingDialog();
            }
        }, null);
    }

    public void onCreateCustomToolBar(Toolbar toolbar)
    {
        super.onCreateCustomToolBar(toolbar);
        toolbar.setTitle(R.string.s_bank_yinhangzhuanzhang);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void initView()
    {
        boxes.add(bankpayYinhangzhuanzhangCb);
        boxes.add(bankpayAtmzidongguiyuanjiCb);
        boxes.add(bankpayAtmxianjinrukuanCb);
        boxes.add(bankpayYinhangguitaizhuanzhangCb);
        boxes.add(bankpayShoujiyinhangzhuanzhangCb);
        boxes.add(bankpayQitaCb);

        mLoadingLayout = (LoadingLayout) findViewById(R.id.loadingLayout);
        mLoadingLayout.setOnReloadingListener(new LoadingLayout.OnReloadingListener()
        {
            @Override
            public void onReload(View v)
            {
                initData();
            }
        });
    }

    @OnClick({R.id.bankpay_shangyibu, R.id.bankpay_ok, R.id.bankpay_yinhangzhuanzhang_cb, R.id.bankpay_atmzidongguiyuanji_cb, R.id.bankpay_atmxianjinrukuan_cb, R.id.bankpay_yinhangguitaizhuanzhang_cb, R.id.bankpay_shoujiyinhangzhuanzhang_cb, R.id.bankpay_qita_cb})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.bankpay_shangyibu:
                finish();
                break;
            case R.id.bankpay_ok:
                if (StringUtils.isEmpty2(bankpayNameEt.getText().toString()))
                {
                    ToastUtil.show("请输入转账人姓名！");
                    return;
                }
                toNext();
                break;
            case R.id.bankpay_yinhangzhuanzhang_cb:
                type = "1";
                setSelete(0);
                break;
            case R.id.bankpay_atmzidongguiyuanji_cb:
                type = "2";
                setSelete(1);
                break;
            case R.id.bankpay_atmxianjinrukuan_cb:
                type = "3";
                setSelete(2);
                break;
            case R.id.bankpay_yinhangguitaizhuanzhang_cb:
                type = "4";
                setSelete(3);
                break;
            case R.id.bankpay_shoujiyinhangzhuanzhang_cb:
                type = "5";
                setSelete(4);
                break;
            case R.id.bankpay_qita_cb:
                type = "10";
                setSelete(5);
                break;
        }
    }

    private void setSelete(int num)
    {
        for (int i = 0; i < boxes.size(); i++)
        {
            boxes.get(i).setChecked(false);
        }
        boxes.get(num).setChecked(true);
    }
}

