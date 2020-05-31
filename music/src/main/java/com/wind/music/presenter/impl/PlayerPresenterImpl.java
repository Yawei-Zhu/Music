package com.wind.music.presenter.impl;

import com.wind.music.Application;
import com.wind.music.bean.SongInfoBean;
import com.wind.music.model.ModelFactory;
import com.wind.music.model.SongModel;
import com.wind.music.presenter.PlayerPresenter;
import com.wind.music.util.AsyncExecutor;
import com.wind.music.util.Utils;
import com.wind.music.view.PlayerView;
import com.wind.music.view.View;

import java.util.HashMap;
import java.util.concurrent.Future;

public class PlayerPresenterImpl implements PlayerPresenter {
    private PlayerView mView;
    private SongModel mModel;
    private HashMap<String, Future<?>> mLoadMap = new HashMap<>();
    private HashMap<String, Future<?>> mCacheMap = new HashMap<>();

    public PlayerPresenterImpl() {
        mModel = ModelFactory.createSongModel(Application.getApp());
    }

    @Override
    public void loadSongInfo(String songId) {
        if (!mLoadMap.containsKey(songId)) {
            Future<?> future = AsyncExecutor.of().submit(songId, new AsyncExecutor.Executable<String, SongInfoBean>() {
                @Override
                public SongInfoBean onLoading(String param) {
                    return mModel.loadSongInfo(param);
                }

                @Override
                public void onLoaded(String param, SongInfoBean result) {
                    if (mLoadMap.remove(param) != null) {
                        if (mView != null) {
                            mView.onSongInfoLoaded(param, result);
                        }
                    }
                }
            });
            mLoadMap.put(songId, future);
        }
    }

    @Override
    public void cancelSongInfo(String songId) {
        Future<?> value = mLoadMap.remove(songId);
        if (value != null) {
            value.cancel(false);
        }
    }

    @Override
    public void cacheSong(String path) {
        if (mCacheMap.containsKey(path)) {
            return;
        }
        Future<?> future = AsyncExecutor.of().submit(path, new AsyncExecutor.Executable<String, String>() {
            @Override
            public String onLoading(String param) {
                if (Utils.isNetworkPath(param)) {
                    return mModel.cacheSong(param);
                } else {
                    return param;
                }
            }

            @Override
            public void onLoaded(String param, String result) {
                if (mCacheMap.remove(param) != null && mView != null) {
                    mView.onSongCached(param, result);
                }
            }
        });
        mCacheMap.put(path, future);
    }

    @Override
    public void cancelSong(String path) {
        Future<?> value = mCacheMap.remove(path);
        if (value != null) {
            value.cancel(false);
        }
    }

    @Override
    public void setView(View view) {
        if (mView != view) {
            for (Future<?> value : mLoadMap.values()) {
                value.cancel(false);
            }
            mLoadMap.clear();

            for (Future<?> value : mCacheMap.values()) {
                value.cancel(false);
            }
            mCacheMap.clear();

            mView = (PlayerView) view;
        }
    }
}
