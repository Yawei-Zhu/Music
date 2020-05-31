package com.wind.music.presenter.impl;

import com.wind.music.Application;
import com.wind.music.bean.SearchBean;
import com.wind.music.bean.Song;
import com.wind.music.bean.SongInfoBean;
import com.wind.music.model.ModelFactory;
import com.wind.music.model.SongModel;
import com.wind.music.presenter.SearchPresenter;
import com.wind.music.util.AsyncExecutor;
import com.wind.music.view.SearchView;
import com.wind.music.view.View;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Future;

public class SearchPresenterImpl implements SearchPresenter {
    private final String TAG = getClass().getSimpleName();
    private SearchView mView;
    private SongModel mModel;
    private HashMap<String, Future<?>> mLoadMap = new HashMap<>();

    public SearchPresenterImpl() {
        mModel = ModelFactory.createSongModel(Application.getApp());
    }

    @Override
    public void query(String key) {
        Future<?> future = AsyncExecutor.of().submit(key, new AsyncExecutor.Executable<String, List<Song>>() {
            @Override
            public List<Song> onLoading(String param) {
                SearchBean bean = mModel.query(param);
                List<Song> songs = new ArrayList<>();
                for (SearchBean.Song song_ : bean.getSong()) {
                    SongInfoBean songInfoBean = mModel.loadSongInfo(song_.getSongid());
                    Song song = new Song();
                    songs.add(song);
                    song.setSongInfoBean(songInfoBean);
                }
                return songs;
            }

            @Override
            public void onLoaded(String param, List<Song> result) {
                if (mLoadMap.remove(param) != null) {
                    if (mView != null) {
                        mView.onLoaded(param, result);
                    }
                }
            }
        });
        mLoadMap.put(key, future);
        return;
    }

    @Override
    public void cancel(String key) {
        Future<?> future = mLoadMap.remove(key);
        if (future != null) {
            future.cancel(false);
        }
    }

    @Override
    public void setView(View view) {
        if (mView != view) {
            for (Future<?> value : mLoadMap.values()) {
                value.cancel(false);
            }
            mLoadMap.clear();
            mView = (SearchView) view;
        }
    }
}
