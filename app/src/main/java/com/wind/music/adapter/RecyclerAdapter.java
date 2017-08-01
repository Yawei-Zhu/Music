package com.wind.music.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/24.
 */

public abstract class RecyclerAdapter<E> extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder<E>> {

    private final Class<? extends ViewHolder> vhc;
    private final Context mContext;
    private List<E> mData;

    public RecyclerAdapter(Context context, List<E> data, Class<? extends RecyclerAdapter.ViewHolder> vhc) {
        this.mContext = context;
        this.vhc = vhc;
        setData(data);
    }

    public Context getContext() {
        return mContext;
    }

    public void setData(List<E> data) {
        if (data == null) {
            data = new ArrayList<>();
        }
        this.mData = data;
    }

    public List<E> getData() {
        return mData;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static abstract class ViewHolder<E> extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void bindData(E e);
    }

    private OnItemClickListener<E> mOnItemClickListener;

    public OnItemClickListener<E> getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener<E> l) {
        this.mOnItemClickListener = l;
    }

    public interface OnItemClickListener<E> {
        public void onItemClick(RecyclerAdapter parent, ViewHolder holder, E item, int position);
    }
}
