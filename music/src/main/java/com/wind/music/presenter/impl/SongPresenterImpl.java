package com.wind.music.presenter.impl;

import com.wind.music.Application;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.fragment.SongFragment;
import com.wind.music.model.SongModel;
import com.wind.music.model.ModelFactory;
import com.wind.music.presenter.SongPresenter;
import com.wind.music.util.AsyncExecutor;
import com.wind.music.util.Setting;
import com.wind.music.view.SongView;
import com.wind.music.view.View;

import java.util.concurrent.Future;

public class SongPresenterImpl implements SongPresenter {
    private SongModel mModel;
    private SongView mView;
    private Future<?> mFuture;

    public SongPresenterImpl() {
        mModel = ModelFactory.createSongModel(Application.getApp());
    }

    @Override
    public void setView(View view) {
        SongView v = (SongView) view;
        if (mView != v) {
            cancel();
            mView = v;
        }
    }

    @Override
    public void loadData(int type) {
        if (mFuture == null) {
            mFuture = AsyncExecutor.of().submit(type,
                    new AsyncExecutor.Executable<Integer, BillBoardBean>() {
                        @Override
                        public BillBoardBean onLoading(Integer param) {
                            Setting setting = Application.getApp().getSetting();
                            if (SongFragment.TYPE_LOCAL == param) {
                                return mModel.loadLocalSongs(setting.getMinLocalLength() * 1000);
                            } else {
                                return mModel.loadNetworkSongs(param, setting.getMaxNetworkSongsNumber());
                            }
                        }

                        @Override
                        public void onLoaded(Integer param, BillBoardBean result) {
                            if (mFuture != null) {
                                mFuture = null;
                                mView.onLoaded(param, result);
                            }
                        }
                    });
        }
    }

    @Override
    public void cancel() {
        if (mFuture != null) {
            mFuture.cancel(false);
            mFuture = null;
        }
    }
}
