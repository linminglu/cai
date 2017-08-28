package com.example.admin.caipiao33.fragment;

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
import com.example.admin.caipiao33.bean.WeiXinPayBean;
import com.example.admin.caipiao33.fragment.adapter.WeiXinPayAdapter;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.httputils.MyResponseListener;
import com.example.admin.caipiao33.utils.ToastUtil;
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
    private View parentView;
    private ArrayList<WeiXinPayBean> weiXinPayBeens;
    private TopupActivity topupActivity;
    private WeiXinPayAdapter payAdapter;

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
        parentView = inflater.inflate(R.layout.fragment_weixin_pay, container, false);
        mInflater = inflater;
        topupActivity = (TopupActivity) getActivity();
        unbinder = ButterKnife.bind(this, parentView);
        initView();
        initData();
        return parentView;
    }

    private void initData()
    {
        showLoadingDialog(false);
        HttpUtil.requestSecond("user", "rwechatList", null, new TypeToken<ArrayList<WeiXinPayBean>>()
        {
        }.getType(), topupActivity, new MyResponseListener<ArrayList<WeiXinPayBean>>()
        {
            @Override
            public void onSuccess(ArrayList<WeiXinPayBean> result)
            {
                weiXinPayBeens = result;
                weiXinPayBeens.get(0).setSelete(true);
                payAdapter = new WeiXinPayAdapter(weiXinPayBeens, mInflater, weixinPayLv, topupActivity);
                weixinPayLv.setAdapter(payAdapter);
                payAdapter.notifyDataSetChanged();
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

    private void initView()
    {
        weixinPayLv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                for (int i = 0; i < weiXinPayBeens.size(); i++)
                {
                    weiXinPayBeens.get(i).setSelete(false);
                }
                weiXinPayBeens.get(position).setSelete(true);
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
        switch (v.getId())
        {
            case R.id.weixin_pay_btn: //下一步
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v)
    {

    }
}

