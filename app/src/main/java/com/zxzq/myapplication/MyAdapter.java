package com.zxzq.myapplication;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2017/03/06.
 */
public class MyAdapter extends RecyclerView.Adapter {
    List<ShowBean.ResultBean.StationdesBean> list;
    private View mView;
    private MyItemClickListener mItemClickListener;

    public MyAdapter(List<ShowBean.ResultBean.StationdesBean> list) {
        this.list = list;
    }

    public void setOnItemClickListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mView = View.inflate(parent.getContext(), R.layout.item, null);
        return new ViewHolder(mView, mItemClickListener);
    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final ViewHolder myholder = (ViewHolder) holder;
        myholder.mTv_show.setText(list.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mTv_show;
        private MyItemClickListener mListener;

        public ViewHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            mTv_show = (TextView) itemView.findViewById(R.id.tv_show);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(view, getPosition());
            }
        }
    }

    public interface MyItemClickListener {
        public void onItemClick(View view, int postion);
    }
}
