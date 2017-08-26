package com.wind.music.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.util.Loader;
import com.wind.music.util.NetworkListener;

/**
 * Created by Administrator on 2017/5/8.
 */

public class NetSongFragment extends SongFragment {

    private static final String TAG = "SongFragment";
    public static final int TYPE_NEW = 1;
    public static final int TYPE_HOT = 2;
    private int mType;

    public static NetSongFragment of(int type) {
        NetSongFragment instance = new NetSongFragment();
        instance.setType(type);
        return instance;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refresh();
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

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    @Override
    public void refresh() {
        getRefreshLayout().setRefreshing(true);
        Loader.loadBillBoard(getType(), 0, new NetworkListener() {
            @Override
            public void onRespond(int code, String msg, String response) {
                getRefreshLayout().setRefreshing(false);
                Log.i(TAG, "onRespond: " + response);
                Gson gson = new Gson();
                BillBoardBean song = gson.fromJson(response, BillBoardBean.class);
                adapter.getDatas().addAll(song.getSong_list());
                adapter.notifyDataSetChanged();
            }
        });
    }
}
