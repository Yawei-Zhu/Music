package com.wind.music.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wind.music.adapter.SongAdapter;
import com.wind.music.adapter.SongRecyclerAdapter;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.bean.Song;
import com.wind.music.decoration.DefaultDecoration;
import com.wind.music.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wind on 2017/8/26.
 */

public class BaseSongFragment extends ListFragment {

    private final List<Song> mSongs = new ArrayList<>();
    protected SongAdapter mSongAdapter;
    private OnItemClickListener mOnItemClickListener;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        getRecyclerView().setLayoutManager(lm);

        mSongAdapter = new SongAdapter(getSongs());
        getRecyclerView().setAdapter(mSongAdapter);

        RecyclerView.ItemDecoration decor = new DefaultDecoration();
        getRecyclerView().addItemDecoration(decor);

        mOnItemClickListener = new OnItemClickListener(getRecyclerView()) {
            @Override
            public void onItemClick(RecyclerView rv, RecyclerView.ViewHolder vh) {
                if (mOnSongClickListener != null) {
                    mOnSongClickListener.onSongClick(getSongs(), vh.getAdapterPosition());
                }
            }
        };
        getRecyclerView().addOnItemTouchListener(mOnItemClickListener);

        getRefreshLayout().setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        refresh();
    }

    @Override
    public void onDestroyView() {
        getRecyclerView().setAdapter(null);
        mSongAdapter = null;
        getRecyclerView().removeOnItemTouchListener(mOnItemClickListener);
        mOnItemClickListener = null;
        super.onDestroyView();
    }

    @NonNull
    public List<Song> getSongs() {
        return mSongs;
    }

    public void refresh() {
    }

    public OnSongClickListener getOnSongClickListener() {
        return mOnSongClickListener;
    }

    public void setOnSongClickListener(OnSongClickListener l) {
        this.mOnSongClickListener = l;
    }

    private OnSongClickListener mOnSongClickListener;
    public interface OnSongClickListener {
        void onSongClick(List<Song> songs, int position);
    }
}
