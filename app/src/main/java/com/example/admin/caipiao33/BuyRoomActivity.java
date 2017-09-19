package com.example.admin.caipiao33;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.caipiao33.bean.BuyRoomBean;
import com.example.admin.caipiao33.httputils.HttpUtil;
import com.example.admin.caipiao33.utils.Constants;
import com.example.admin.caipiao33.utils.MyImageLoader;
import com.example.admin.caipiao33.views.DividerItemDecoration;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 需要调用者传入
 * title -- Constants.EXTRA_TITLE
 * bean -- Constants.EXTRA_BUY_ROOM_BEAN
 * <p>
 * 购彩房间列表页
 */
public class BuyRoomActivity extends BaseActivity
{

    private String mTitleStr;
    private BuyRoomBean mBuyRoomBean;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.toolbar_title)
    TextView mToolbarTitle;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_room);
        ButterKnife.bind(this);
        // 获取外部数据
        Intent intent = getIntent();
        mTitleStr = intent.getStringExtra(Constants.EXTRA_TITLE);
        mBuyRoomBean = (BuyRoomBean) intent.getSerializableExtra(Constants.EXTRA_BUY_ROOM_BEAN);

        // 修改toolbar内容
        mToolbar.setTitle("");
        mToolbarTitle.setText(mTitleStr);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // 加载
        recyclerView.setAdapter(new MyAdapter());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        if (item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        @BindView(R.id.iv)
        ImageView iv;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_sub_title)
        TextView tvSubTitle;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @OnClick(R.id.parent)
        public void onViewClicked(View view)
        {
            switch (view.getId())
            {
                case R.id.parent:
                    int position = getAdapterPosition();
                    BuyRoomBean.RoomListBean roomListBean = mBuyRoomBean.getRoomList()
                            .get(position);
                    Intent intent = new Intent(BuyRoomActivity.this, BuyActivity.class);
                    intent.putExtra(Constants.EXTRA_TITLE, mTitleStr);
                    intent.putExtra(Constants.EXTRA_NUMBER, mBuyRoomBean.getNum());
                    intent.putExtra(Constants.EXTRA_ROOM_ID, roomListBean.getId());
                    startActivity(intent);
                    break;
            }
        }
    }

    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder>
    {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = getLayoutInflater().inflate(R.layout.item_room, null);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position)
        {
            BuyRoomBean.RoomListBean roomListBean = mBuyRoomBean.getRoomList().get(position);
            MyImageLoader.displayImage(HttpUtil.mNewUrl + roomListBean.getImage(), holder.iv, BuyRoomActivity.this);
            holder.tvTitle.setText(roomListBean.getName());
            holder.tvSubTitle.setText(roomListBean.getRemark());
        }

        @Override
        public int getItemCount()
        {
            return mBuyRoomBean.getRoomList().size();
        }
    }
}
