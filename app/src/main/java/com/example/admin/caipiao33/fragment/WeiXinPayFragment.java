package com.example.admin.caipiao33.fragment;

import android.content.Intent;
import android.net.Uri;
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
import com.example.admin.caipiao33.bean.WeiXinPayBean;
import com.example.admin.caipiao33.fragment.adapter.WeiXinPayAdapter;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.topupactivity.AliBankActivity;
import com.example.admin.caipiao33.topupactivity.WeiXin3SaoMaActivity;
import com.example.admin.caipiao33.topupactivity.WeiXinBankActivity;
import com.example.admin.caipiao33.topupactivity.WeiXinHaoYouActivity;
import com.example.admin.caipiao33.topupactivity.WeiXinPingTaiActivity;
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
 * 微信充值页面
 */
public class WeiXinPayFragment extends BaseFragment implements View.OnClickListener
{
    @BindView(R.id.weixin_pay_lv)
    ListView weixinPayLv;
    @BindView(R.id.weixin_pay_btn)
    Button weixinPayBtn;
    Unbinder unbinder;
    private LayoutInflater mInflater;
    private TopupActivity topupActivity;
    private WeiXinPayAdapter payAdapter;
    private View mNotifyNullLayout;

    public WeiXinPayFragment()
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
        View parentView = inflater.inflate(R.layout.fragment_weixin_pay, container, false);
        mNotifyNullLayout = parentView.findViewById(R.id.notify_null_layout);
        mInflater = inflater;
        topupActivity = (TopupActivity) getActivity();
        unbinder = ButterKnife.bind(this, parentView);
        initView();
        initData();
        return parentView;
    }

    private void initData()
    {
        HttpUtil.requestSecond("user", "rwechatList", null, new TypeToken<ArrayList<WeiXinPayBean>>()
        {
        }.getType(), topupActivity, new MyResponseListener<ArrayList<WeiXinPayBean>>()
        {
            @Override
            public void onSuccess(ArrayList<WeiXinPayBean> result)
            {
                if (null == result || result.size() == 0)
                {
                    weixinPayLv.setVisibility(View.GONE);
                    weixinPayBtn.setVisibility(View.GONE);
                    mNotifyNullLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    weixinPayLv.setVisibility(View.VISIBLE);
                    weixinPayBtn.setVisibility(View.VISIBLE);
                    mNotifyNullLayout.setVisibility(View.GONE);
                }
                result.get(0).setSelete(true);
                payAdapter = new WeiXinPayAdapter(result, mInflater, weixinPayLv, topupActivity);
                weixinPayLv.setAdapter(payAdapter);
                payAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg)
            {
                weixinPayLv.setVisibility(View.GONE);
                weixinPayBtn.setVisibility(View.GONE);
                mNotifyNullLayout.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish()
            {
                //                hideLoadingDialog();
            }
        }, null);
    }

    private void initView()
    {
        weixinPayLv.setOnItemClickListener(new AdapterView.OnItemClickListener()
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

    @OnClick(R.id.weixin_pay_btn)
    public void onViewClicked(View v)
    {
        Intent intent;
        switch (v.getId())
        {
            case R.id.weixin_pay_btn: //下一步
                if (payAdapter.getBeanContents() != null)
                {
                    for (int i = 0; i < payAdapter.getBeanContents().size(); i++)
                    {
                        if (payAdapter.getBeanContents().get(i).isSelete())
                        {
                            if (!payAdapter.getBeanContents().get(i).getCode().equals("#scan#"))
                            {
                                //跳转添加好友页面
                                if (payAdapter.getBeanContents().get(i).getPayType() == 3)
                                {
                                    intent = new Intent(topupActivity, WeiXinHaoYouActivity.class);
                                    intent.putExtra(Constants.EXTRA_TOPUP_WEIXIN, payAdapter.getBeanContents()
                                            .get(i));
                                    startActivity(intent);
                                }
                                //跳转支付宝转账银行卡页面
                                else if (payAdapter.getBeanContents().get(i).getPayType() == 5)
                                {
                                    if (isCanNext(topupActivity.getTopupAmount(), payAdapter.getBeanContents()
                                            .get(i)
                                            .getPayMin(), payAdapter.getBeanContents()
                                            .get(i)
                                            .getPayMax()))
                                    {
                                        intent = new Intent(topupActivity, WeiXinBankActivity.class);
                                        intent.putExtra(Constants.EXTRA_TOPUP_PAYID, payAdapter.getBeanContents()
                                                .get(i)
                                                .getId());
                                        intent.putExtra(Constants.EXTRA_TOPUP_TOPUPAMOUNT, topupActivity
                                                .getTopupAmount() + "");
                                        startActivity(intent);
                                    }
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
                                        intent = new Intent(topupActivity, WeiXinPingTaiActivity.class);
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
                                        intent = new Intent(topupActivity, WeiXin3SaoMaActivity.class);
                                        intent.putExtra(Constants.EXTRA_TOPUP_PAYID, payAdapter.getBeanContents()
                                                .get(i)
                                                .getId());
                                        intent.putExtra(Constants.EXTRA_TOPUP_TOPUPAMOUNT, topupActivity
                                                .getTopupAmount() + "");
                                        startActivity(intent);
                                    }
                                }
                                else if (payAdapter.getBeanContents()
                                        .get(i)
                                        .getType() == 2)//第三方跳转WEB
                                {
                                    if (isCanNext(topupActivity.getTopupAmount(), payAdapter.getBeanContents()
                                            .get(i)
                                            .getPayMin(), payAdapter.getBeanContents()
                                            .get(i)
                                            .getPayMax()))
                                    {
                                        String url = payAdapter.getBeanContents()
                                                .get(i)
                                                .getPayImg() + "/common/recharge/third?isH5=1&memberId=" + UserConfig
                                                .getInstance()
                                                .getToken(topupActivity)
                                                .getMemberId() + "&type=1&payId=" + payAdapter.getBeanContents()
                                                .get(i)
                                                .getId() + "&amount=" + topupActivity.getTopupAmount() + "&bankName=&baseUrl=" + HttpUtil.mNewUrl;

                                        final Uri uri = Uri.parse(url);
                                        final Intent it = new Intent(Intent.ACTION_VIEW, uri);
                                        startActivity(it);
                                        //                                    toWebUrlActivity(url, "微信支付");
                                        //                                    startActivity(intent);
                                    }
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

