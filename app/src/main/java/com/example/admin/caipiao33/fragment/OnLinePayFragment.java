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
import com.example.admin.caipiao33.bean.OnLinePayBean;
import com.example.admin.caipiao33.fragment.adapter.OnLinePayAdapter;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.example.admin.caipiao33.utils.UserConfig;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 网银支付列表
 */
public class OnLinePayFragment extends BaseFragment implements View.OnClickListener
{
    @BindView(R.id.online_pay_lv)
    ListView onlinePayLv;
    @BindView(R.id.online_pay_btn)
    Button onlinePayBtn;
    Unbinder unbinder;
    private LayoutInflater mInflater;
    private TopupActivity topupActivity;
    private OnLinePayAdapter payAdapter;
    private OnLinePayBean onLinePayBean;
    private View mNotifyNullLayout;

    public OnLinePayFragment()
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
        View parentView = inflater.inflate(R.layout.fragment_online_pay, container, false);
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
        HttpUtil.requestSecond("user", "ronlineList", null, OnLinePayBean.class, topupActivity, new MyResponseListener<OnLinePayBean>()
        {
            @Override
            public void onSuccess(OnLinePayBean result)
            {
                if (null == result || result.getExpand().getBankList().size() == 0)
                {
                    onlinePayLv.setVisibility(View.GONE);
                    onlinePayBtn.setVisibility(View.GONE);
                    mNotifyNullLayout.setVisibility(View.VISIBLE);
                }
                else
                {
                    onlinePayLv.setVisibility(View.VISIBLE);
                    onlinePayBtn.setVisibility(View.VISIBLE);
                    mNotifyNullLayout.setVisibility(View.GONE);
                }
                onLinePayBean = result;
                result.getExpand().getBankList().get(0).setSelete(true);
                payAdapter = new OnLinePayAdapter(result.getExpand()
                        .getBankList(), mInflater, onlinePayLv, topupActivity);
                onlinePayLv.setAdapter(payAdapter);
                payAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(int code, String msg)
            {
                onlinePayLv.setVisibility(View.GONE);
                onlinePayBtn.setVisibility(View.GONE);
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
        onlinePayLv.setOnItemClickListener(new AdapterView.OnItemClickListener()
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

    @OnClick(R.id.online_pay_btn)
    public void onViewClicked(View v)
    {
        switch (v.getId())
        {
            case R.id.online_pay_btn: //下一步
                for (int i = 0; i < payAdapter.getBeanContents().size(); i++)
                {
                    if (payAdapter.getBeanContents().get(i).isSelete())
                    {
                        if (isCanNext(topupActivity.getTopupAmount(), onLinePayBean.getMinAmount(), onLinePayBean
                                .getMaxAmount()))
                        {
                            String url = onLinePayBean.getPayUrl() + "/common/recharge/third?isH5=1&memberId=" + UserConfig
                                    .getInstance()
                                    .getToken(topupActivity)
                                    .getMemberId() + "&type=4&payId=" + onLinePayBean.getId() + "&amount=" + topupActivity
                                    .getTopupAmount() + "&bankName=" + payAdapter.getBeanContents()
                                    .get(i)
                                    .getId() + "&baseUrl=" + HttpUtil.mNewUrl;
                            toWebUrlActivity(url, "网银支付");
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

