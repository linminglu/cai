package com.example.admin.caipiao33.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.admin.caipiao33.BaseFragment;
import com.example.admin.caipiao33.R;
import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.fragment.adapter.MyBaseBuyAdapter;
import com.example.admin.caipiao33.fragment.adapter.NormalExpandableAdapter;
import com.example.admin.caipiao33.fragment.adapter.TypeAfterAdapter;
import com.example.admin.caipiao33.fragment.adapter.TypeBeforeAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    ListView listView;
    Unbinder unbinder;
    private LayoutInflater mInflater;
    private View parentView;
    private BuyRoomBean mBuyRoomBean;
    private MyBaseBuyAdapter adapter;
    private int mType;

    /*
    分类：
        1. 六合
        2. 前四个四个折叠
        3. 第一个不折叠，后四个四个折叠
     */
    /**
     * 18 - 香港⑥合彩
     */
    private final String six = "18";

    /**
     * 52 - 三分PK10
     * 9 - 北京赛车(PK10)
     * 34 - 幸运飞艇
     */
    private final List<String> beforeList = Arrays.asList("52", "9", "34");

    /**
     * 27 - 广东快乐十分
     * 28 - 重庆幸运农场
     */
    private final List<String> afterList = Arrays.asList("27", "28");

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
        String num = mBuyRoomBean.getNum();

        if (beforeList.contains(num)) {
            adapter = new TypeBeforeAdapter(mInflater, mBuyRoomBean, mType);
        } else if (afterList.contains(num)) {
            adapter = new TypeAfterAdapter(mInflater, mBuyRoomBean, mType);
        } else {
            adapter = new NormalExpandableAdapter(mInflater, mBuyRoomBean, mType);
        }
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

    /**
     * 清空已选中的内容
     */
    public void clearChecked() {
        adapter.clearChecked();
    }
}

