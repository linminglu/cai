package com.example.admin.caipiao33.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.fragment.adapter.NormalExpandableAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Description : 走势页面
 * Author : cxy
 * Date   : 17/7/31
 */
public class QuickBuyFragment extends BaseFragment implements View.OnClickListener
{

    private static final String PLAY_DETAIL_LIST_BEAN = "playDetailListBean";
    @BindView(R.id.listView)
    ListView listView;
    Unbinder unbinder;
    private LayoutInflater mInflater;
    private View parentView;
    private BuyRoomBean mBuyRoomBean;
    private NormalExpandableAdapter adapter;


    //若Fragement定义有带参构造函数，则一定要定义public的默认的构造函数
    public QuickBuyFragment()
    {
    }

    /**
     */
    public static QuickBuyFragment newInstance(BuyRoomBean bean)
    {
        QuickBuyFragment fragment = new QuickBuyFragment();
        Bundle args = new Bundle();
        args.putSerializable(PLAY_DETAIL_LIST_BEAN, bean);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            mBuyRoomBean = (BuyRoomBean) getArguments().getSerializable(PLAY_DETAIL_LIST_BEAN);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        parentView = inflater.inflate(R.layout.fragment_quick_buy, container, false);
        mInflater = inflater;
        initView();
        return parentView;
    }

    private void initView()
    {
        unbinder = ButterKnife.bind(this, parentView);
        adapter = new NormalExpandableAdapter(mInflater, mBuyRoomBean);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(final View v)
    {
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
        unbinder.unbind();
    }
}

