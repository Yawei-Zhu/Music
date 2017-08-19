package com.wind.music.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.wind.music.adapter.SongAdapter;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.decoration.DefaultDecoration;
import com.wind.music.util.Loader;
import com.wind.music.util.NetworkListener;

/**
 * Created by Administrator on 2017/5/8.
 */

public abstract class BaseSongFragment extends ListFragment {

    private static final String TAG = "SongFragment";
    public static final int TYPE_NEW = 1;
    public static final int TYPE_HOT = 2;

    private SongAdapter adapter;
    private BillBoardBean song;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        getRecyclerView().setLayoutManager(lm);

        adapter = new SongAdapter(getContext(), song);
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

    @Override
    public CharSequence getTitle() {
        CharSequence title = "";
        switch (getType()) {
            case TYPE_NEW:
                title = "最新";
                break;
            case TYPE_HOT:
                title = "最热";
                break;
        }
        return title;
    }

    public abstract int getType();

    @Override
    public void refresh() {
        getRefreshLayout().setRefreshing(true);
        Loader.loadBillBoard(getType(), 0, new NetworkListener() {
            @Override
            public void onRespond(int code, String msg, String response) {
                getRefreshLayout().setRefreshing(false);
                Log.i(TAG, "onRespond: " + response);
                Gson gson = new Gson();
                song = gson.fromJson(response, BillBoardBean.class);
                adapter.setData(song);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
