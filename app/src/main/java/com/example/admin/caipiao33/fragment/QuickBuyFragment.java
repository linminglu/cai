package com.example.admin.caipiao33.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.fragment.adapter.MyBaseBuyAdapter;
import com.example.admin.caipiao33.fragment.adapter.TypeBeforeAdapter;

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
    private static final String TYPE = "type";
    public static final int TYPE_QUICK = 0;
    public static final int TYPE_SELF_SELECT = 1;

    @BindView(R.id.listView)
    ExpandableListView listView;
    Unbinder unbinder;
    private LayoutInflater mInflater;
    private View parentView;
    private BuyRoomBean mBuyRoomBean;
    private MyBaseBuyAdapter adapter;
    private int mType;



    //若Fragement定义有带参构造函数，则一定要定义public的默认的构造函数
    public QuickBuyFragment()
    {
    }

    /**
     */
    public static QuickBuyFragment newInstance(BuyRoomBean bean, int type)
    {
        QuickBuyFragment fragment = new QuickBuyFragment();
        Bundle args = new Bundle();
        args.putSerializable(PLAY_DETAIL_LIST_BEAN, bean);
        args.putInt(TYPE, type);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null)
        {
            mBuyRoomBean = (BuyRoomBean) arguments.getSerializable(PLAY_DETAIL_LIST_BEAN);
            mType = arguments.getInt(TYPE);
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
        adapter = new TypeBeforeAdapter(mInflater, mBuyRoomBean, mType);
        listView.setAdapter(adapter);
        //遍历所有group,将所有项设置成默认展开
        int groupCount = listView.getCount();
        for (int i=0; i < groupCount; i++)
        {
            listView.expandGroup(i);
        }
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

    /**
     * 清空已选中的内容
     */
    public void clearChecked() {
        adapter.clearChecked();
    }
}

