package com.wind.music.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/24.
 */

public abstract class BaseAdapter<E> extends RecyclerView.Adapter<BaseAdapter.ViewHolder<E>> {

    private Context mContext;
    private List<E> mData;

    public BaseAdapter(Context context, List<E> data) {
        setContext(context);
        setData(data);
    }

    public void setContext(Context context) {
        if (context == null) {
            throw new IllegalAccessError("context == null");
        }
        this.mContext = context;
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
        public void onItemClick(BaseAdapter parent, ViewHolder holder, E item, int position);
    }
}
