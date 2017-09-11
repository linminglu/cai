package com.example.admin.caipiao33.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.TopupActivity;
import com.example.admin.caipiao33.WebUrlActivity;
import com.example.admin.caipiao33.bean.AliPayBean;
import com.example.admin.caipiao33.fragment.adapter.AliPayAdapter;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.topupactivity.Ali3SaoMaActivity;
import com.example.admin.caipiao33.topupactivity.AliHaoYouActivity;
import com.example.admin.caipiao33.topupactivity.AliPingTaiActivity;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.UserConfig;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 支付宝充值页面
 */
public class AliPayFragment extends BaseFragment implements View.OnClickListener
{
    @BindView(R.id.ali_pay_lv)
    ListView aliPayLv;
    @BindView(R.id.ali_pay_btn)
    Button aliPayBtn;
    Unbinder unbinder;
    private LayoutInflater mInflater;
    private TopupActivity topupActivity;
    private AliPayAdapter payAdapter;

    public AliPayFragment()
    {

    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View parentView = inflater.inflate(R.layout.fragment_ali_pay, container, false);
        mInflater = inflater;
        topupActivity = (TopupActivity) getActivity();
        unbinder = ButterKnife.bind(this, parentView);
        initView();
        initData();
        return parentView;
    }

    private void initData()
    {
        HttpUtil.requestSecond("user", "ralipayList", null, new TypeToken<ArrayList<AliPayBean>>()
        {
        }.getType(), topupActivity, new MyResponseListener<ArrayList<AliPayBean>>()
        {
            @Override
            public void onSuccess(ArrayList<AliPayBean> result)
            {
                result.get(0).setSelete(true);
                payAdapter = new AliPayAdapter(result, mInflater, aliPayLv, topupActivity);
                aliPayLv.setAdapter(payAdapter);
                payAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg)
            {
                //                ToastUtil.show(msg);
            }

            @Override
            public void onFinish()
            {
            }
        }, null);
    }

    private void initView()
    {
        aliPayLv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                for (int i = 0; i < payAdapter.getBeanContents().size(); i++)
                {
                    payAdapter.getBeanContents().get(i).setSelete(false);
                }
                payAdapter.getBeanContents().get(position).setSelete(true);
                payAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.ali_pay_btn)
    public void onViewClicked(View v)
    {
        Intent intent;
        switch (v.getId())
        {
            case R.id.ali_pay_btn: //下一步
                for (int i = 0; i < payAdapter.getBeanContents().size(); i++)
                {
                    if (payAdapter.getBeanContents().get(i).isSelete())
                    {
                        if (!payAdapter.getBeanContents().get(i).getCode().equals("#scan#"))
                        {
                            //跳转添加好友页面
                            if (payAdapter.getBeanContents().get(i).getPayType() == 3)
                            {
                                intent = new Intent(topupActivity, AliHaoYouActivity.class);
                                intent.putExtra(Constants.EXTRA_TOPUP_WEIXIN, payAdapter.getBeanContents()
                                        .get(i));
                                startActivity(intent);
                            }
                            //跳转微信平台支付页面
                            else
                            {
                                if (isCanNext(topupActivity.getTopupAmount(), payAdapter.getBeanContents()
                                        .get(i)
                                        .getPayMin(), payAdapter.getBeanContents()
                                        .get(i)
                                        .getPayMax()))
                                {
                                    intent = new Intent(topupActivity, AliPingTaiActivity.class);
                                    intent.putExtra(Constants.EXTRA_TOPUP_PAYID, payAdapter.getBeanContents()
                                            .get(i)
                                            .getId());
                                    intent.putExtra(Constants.EXTRA_TOPUP_TOPUPAMOUNT, topupActivity
                                            .getTopupAmount() + "");
                                    startActivity(intent);
                                }
                            }
                        }
                        //跳转第三方支付页面
                        //                        else if (payAdapter.getBeanContents().get(i).getType() == 2)
                        else
                        {
                            if (payAdapter.getBeanContents().get(i).getType() == 1)//第三方扫码
                            {
                                if (isCanNext(topupActivity.getTopupAmount(), payAdapter.getBeanContents()
                                        .get(i)
                                        .getPayMin(), payAdapter.getBeanContents()
                                        .get(i)
                                        .getPayMax()))
                                {
                                    intent = new Intent(topupActivity, Ali3SaoMaActivity.class);
                                    intent.putExtra(Constants.EXTRA_TOPUP_PAYID, payAdapter.getBeanContents()
                                            .get(i)
                                            .getId());
                                    intent.putExtra(Constants.EXTRA_TOPUP_TOPUPAMOUNT, topupActivity
                                            .getTopupAmount() + "");
                                    startActivity(intent);
                                }
                            }
                            else if (payAdapter.getBeanContents().get(i).getType() == 2)//第三方跳转WEB
                            {
                                if (isCanNext(topupActivity.getTopupAmount(), payAdapter.getBeanContents()
                                        .get(i)
                                        .getPayMin(), payAdapter.getBeanContents()
                                        .get(i)
                                        .getPayMax()))
                                {
                                    String url = payAdapter.getBeanContents()
                                            .get(i)
                                            .getPayImg() + "/common/recharge/third?memberId=" + UserConfig
                                            .getInstance()
                                            .getToken(topupActivity)
                                            .getMemberId() + "&type=2&payId=" + payAdapter.getBeanContents()
                                            .get(i)
                                            .getId() + "&amount=" + topupActivity.getTopupAmount() + "&bankName=&baseUrl=" + HttpUtil.mNewUrl;
                                    toWebUrlActivity(url, "支付宝支付");
                                    //                                    startActivity(intent);
                                }
                            }
                        }
                    }
                }
                break;
            default:
                break;
        }
    }

    private boolean isCanNext(int amount, int min, int max)
    {
        if (min > amount)
        {
            ToastUtil.show("充值金额不足" + min + "元！");
            return false;
        }
        else if (max < amount)
        {
            ToastUtil.show("充值金额超过" + max + "元！");
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public void onClick(View v)
    {

    }

    private void toWebUrlActivity(String url, String title)
    {
        Intent intent = new Intent(topupActivity, WebUrlActivity.class);
        intent.putExtra(Constants.EXTRA_URL, url);
        intent.putExtra(Constants.EXTRA_TITLE, title);
        startActivity(intent);
    }
}

