package com.wind.music.fragment;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wind.music.adapter.SongAdapter;
import com.wind.music.bean.BillBoard;
import com.wind.music.bean.NetworkSong;
import com.wind.music.decoration.DefaultDecoration;
import com.wind.music.util.JSONParser;
import com.wind.music.util.Loader;
import com.wind.music.util.Network;
import com.wind.music.util.Network2;
import com.wind.music.util.NetworkListener;
import com.wind.music.util.Toasts;
import com.wind.music.util.Urls;

import org.json.JSONException;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * Created by Administrator on 2017/5/8.
 */

public abstract class BaseSongFragment extends ListFragment {

    public static final int TYPE_NEW = 1;
    public static final int TYPE_HOT = 2;

    private BillBoard billBoard;
    private SongAdapter adapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        getRecyclerView().setLayoutManager(lm);

        billBoard = new BillBoard();
//        adapter = new SongAdapter(getContext(), billBoard.songs);
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
        Network2 net = new Retrofit.Builder().baseUrl(Urls.URL).build().create(Network2.class);
        net.listSong(Urls.FORMAT_JSON, Urls.METHOD_BILL, getType(), 10, 0, new Callback<List<NetworkSong>>() {
            @Override
            public void onResponse(Call<List<NetworkSong>> call, Response<List<NetworkSong>> response) {

            }

            @Override
            public void onFailure(Call<List<NetworkSong>> call, Throwable t) {

            }
        });
    }
}
