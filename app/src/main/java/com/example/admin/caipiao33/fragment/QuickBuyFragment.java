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
import com.example.admin.caipiao33.fragment.adapter.TypeQuick3Adapter;
import com.example.admin.caipiao33.fragment.adapter.TypeSix26Adapter;
import com.example.admin.caipiao33.fragment.adapter.TypeSix27Adapter;
import com.example.admin.caipiao33.fragment.adapter.TypeSix28Adapter;
import com.example.admin.caipiao33.fragment.adapter.TypeSix6Adapter;
import com.example.admin.caipiao33.fragment.adapter.TypeSixAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 自选下注、快捷下注
 */
public class QuickBuyFragment extends BaseFragment
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
    private String mPlayId;


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

    public void updateBuyRoomBean(BuyRoomBean bean)
    {
        mBuyRoomBean = bean;
        List<BuyRoomBean.PlayDetailListBean> playDetailList = bean.getPlayDetailList();
        if (null == playDetailList)
        {
            return;
        }
        // TODO 这里原来使用num判断是那种玩法类型，改用type需要注意
        String num = mBuyRoomBean.getNum();
        if (num.equals(MyBaseBuyAdapter.TYPE_SIX))
        {
            String playName = mBuyRoomBean.getPlayName();
            if (mType == TYPE_SELF_SELECT && playName.equals("自选不中"))
            {
                adapter = new TypeSix26Adapter(mInflater, mBuyRoomBean, mType);
            }
            else if (mType == TYPE_SELF_SELECT && playName.equals("连码"))
            {
                adapter = new TypeSix28Adapter(mInflater, mBuyRoomBean, mType);
            }
            else if (mType == TYPE_SELF_SELECT && playName.equals("合肖"))
            {
                adapter = new TypeSix6Adapter(mInflater, mBuyRoomBean, mType);
            }
            else if (playName.equals("连肖连尾")) {
                adapter = new TypeSix27Adapter(mInflater, mBuyRoomBean, mType);
            }
            else
            {
                adapter = new TypeSixAdapter(mInflater, mBuyRoomBean, mType);
            }

        }
        else if (TypeQuick3Adapter.QUICK3List.contains(num)) {
            adapter = new TypeQuick3Adapter(mInflater, mBuyRoomBean, mType);
        }
        else
        {
            adapter = new TypeBeforeAdapter(mInflater, mBuyRoomBean, mType);
        }
        listView.setAdapter(adapter);
        //遍历所有group,将所有项设置成默认展开
        int groupCount = adapter.getGroupCount();
        for (int i = 0; i < groupCount; i++)
        {
            listView.expandGroup(i);
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
        updateBuyRoomBean(mBuyRoomBean);
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
    public void clearChecked()
    {
        adapter.clearChecked();
    }

    public List<BuyRoomBean.PlayDetailListBean.ListBean> getChecked()
    {
        return adapter.getCheckedList();
    }
}

