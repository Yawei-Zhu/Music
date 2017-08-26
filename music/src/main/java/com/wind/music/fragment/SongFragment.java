package com.wind.music.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wind.music.adapter.SongAdapter;
import com.wind.music.adapter.SongRecyclerAdapter;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.decoration.DefaultDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wind on 2017/8/26.
 */

public class SongFragment extends ListFragment {

    protected final List<BillBoardBean.Song> songs = new ArrayList<>();
    protected SongRecyclerAdapter adapter;
    protected SongRecyclerAdapter.OnItemClickListener mOnItemClickListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        getRecyclerView().setLayoutManager(lm);

        adapter = new SongRecyclerAdapter(getContext(), songs);
        adapter.setOnItemClickListener(mOnItemClickListener);
        getRecyclerView().setAdapter(adapter);

        RecyclerView.ItemDecoration decor = new DefaultDecoration();
        getRecyclerView().addItemDecoration(decor);

        getRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

    }

    public List<BillBoardBean.Song> getSongs() {
        return songs;
    }

    public SongRecyclerAdapter.OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(SongRecyclerAdapter.OnItemClickListener l) {
        if (mOnItemClickListener != l) {
            this.mOnItemClickListener = l;
            if (adapter != null) {
                adapter.setOnItemClickListener(mOnItemClickListener);
            }
        }
    }
}
