package com.example.admin.caipiao33.views;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.admin.caipiao33.R;

import java.util.List;

public class PopOPtions
{

    private PopupWindow mPopMenu;
    private Context mContext;
    private List<String> list;
    private OnItemSelectedListener listener;
    private RecyclerView mRecyclerView;

    public PopOPtions() {

    }

    public void init(Context context, List<String> list, OnItemSelectedListener listener) {
        if (mPopMenu != null) {
            dismissPopWindow();
            return;
        }
        this.listener = listener;
        this.mContext = context;
        this.list = list;
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_pop_options, null);
        mPopMenu = new PopupWindow(inflate, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
        inflate.setFocusableInTouchMode(true);
        mRecyclerView = (RecyclerView) inflate.findViewById(R.id.recyclerView);

        if (list.size() >= 8) {
            // 使用网格布局
            mRecyclerView.setLayoutManager(new GridLayoutManager(context, 4));
            mRecyclerView.addItemDecoration(new DividerGridItemDecoration(context));
        } else {
            // 使用线性布局
            mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
            mRecyclerView.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.VERTICAL_LIST));
        }
        mRecyclerView.setAdapter(new MyAdapter());

        inflate.setOnTouchListener(new OnTouchListener()
        {

            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                dismissPopWindow();
                return false;
            }
        });
        inflate.setOnKeyListener(new View.OnKeyListener()
        {

            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event)
            {
                dismissPopWindow();
                return false;
            }
        });
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv);
            tv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv:
                    int position = getAdapterPosition();
                    String s = list.get(position);
                    dismissPopWindow();
                    if (null != listener) {
                        listener.onItemSelected(s, position);
                    }
                    break;
            }

        }
    }

    class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_option, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(list.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    public void showPop(View parent)
    {
        if (null == mPopMenu) {
            return;
        }
        if (mPopMenu.isShowing())
        {
            mPopMenu.dismiss();
            return;
        }
        mPopMenu.showAsDropDown(parent);
    }

    public void dismissPopWindow()
    {
        if (mPopMenu != null && mPopMenu.isShowing())
        {
            mPopMenu.dismiss();
        }
    }

    public interface OnItemSelectedListener {
        void onItemSelected(String s, int position);
    }
}
