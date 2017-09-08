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
import com.example.admin.caipiao33.bean.BankPayBean;
import com.example.admin.caipiao33.fragment.adapter.BankPayAdapter;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.topupactivity.Ali3SaoMaActivity;
import com.example.admin.caipiao33.topupactivity.BankPayActivity;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.ToastUtil;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 银行转账列表页面
 */
public class BankPayFragment extends BaseFragment implements View.OnClickListener
{
    @BindView(R.id.bank_pay_lv)
    ListView bankPayLv;
    @BindView(R.id.bank_pay_btn)
    Button bankPayBtn;
    Unbinder unbinder;
    private LayoutInflater mInflater;
    private TopupActivity topupActivity;
    private BankPayAdapter payAdapter;

    public BankPayFragment()
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
        View parentView = inflater.inflate(R.layout.fragment_bank_pay, container, false);
        mInflater = inflater;
        topupActivity = (TopupActivity) getActivity();
        unbinder = ButterKnife.bind(this, parentView);
        initView();
        initData();
        return parentView;
    }

    private void initData()
    {
        HttpUtil.requestSecond("user", "rbankList", null, new TypeToken<ArrayList<BankPayBean>>()
        {
        }.getType(), topupActivity, new MyResponseListener<ArrayList<BankPayBean>>()
        {
            @Override
            public void onSuccess(ArrayList<BankPayBean> result)
            {
                result.get(0).setSelete(true);
                payAdapter = new BankPayAdapter(result, mInflater, bankPayLv, topupActivity);
                bankPayLv.setAdapter(payAdapter);
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
        bankPayLv.setOnItemClickListener(new AdapterView.OnItemClickListener()
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

    @OnClick(R.id.bank_pay_btn)
    public void onViewClicked(View v)
    {
        Intent intent;
        switch (v.getId())
        {
            case R.id.bank_pay_btn: //下一步
                for (int i = 0; i < payAdapter.getBeanContents().size(); i++)
                {
                    if (payAdapter.getBeanContents().get(i).isSelete())
                    {
                        if (isCanNext(topupActivity.getTopupAmount(), payAdapter.getBeanContents()
                                .get(i)
                                .getPayMin(), payAdapter.getBeanContents().get(i).getPayMax()))
                        {
                            intent = new Intent(topupActivity, BankPayActivity.class);
                            intent.putExtra(Constants.EXTRA_TOPUP_PAYID, payAdapter.getBeanContents()
                                    .get(i)
                                    .getId());
                            intent.putExtra(Constants.EXTRA_TOPUP_TOPUPAMOUNT, topupActivity.getTopupAmount() + "");
                            startActivity(intent);
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

