package com.wind.music.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.wind.music.adapter.SongRecyclerAdapter;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.decoration.DefaultDecoration;
import com.wind.music.util.LoadLocal;
import com.wind.music.util.LoadLocalListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wind on 2017/8/19.
 */

public class LocalSongFragment extends SongFragment {
    private boolean mPermission;
    private Handler handler = new Handler();
    private final int INTERVAL = 500;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refresh();
    }

    @Override
    public void refresh() {
        if (hasPermission()) {
            LoadLocal.loadSongs(new LoadLocalListener<List<BillBoardBean.Song>>() {
                @Override
                public void onRespond(List<BillBoardBean.Song> data) {
                    if (songs != null) {
                        songs.clear();
                        songs.addAll(data);
                        if (adapter != null) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                    if (getRefreshLayout() != null) {
                        getRefreshLayout().setRefreshing(false);
                    }
                }
            });
        } else {
            handler.postDelayed(loadDataRunnable, INTERVAL);
        }
    }

    private final Runnable loadDataRunnable = new Runnable() {
        @Override
        public void run() {
            refresh();
        }
    };

    public boolean hasPermission() {
        return mPermission;
    }

    public void setPermission(boolean permission) {
        this.mPermission = permission;
    }

    public List<BillBoardBean.Song> getSongs() {
        return songs;
    }
}
