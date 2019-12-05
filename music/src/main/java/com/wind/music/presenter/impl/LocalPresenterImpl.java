package com.wind.music.presenter.impl;

import android.os.Handler;

import com.wind.music.bean.BillBoardBean;
import com.wind.music.model.LocalModel;
import com.wind.music.model.Model;
import com.wind.music.model.ModelFactory;
import com.wind.music.presenter.LocalPresenter;
import com.wind.music.view.LocalView;

import java.util.List;

public class LocalPresenterImpl implements LocalPresenter {
    private LocalModel mModel;
    private LocalView mView;
    private boolean mPermission = false;
    private boolean mCancel = false;
    private boolean mLoading = false;
    private Handler handler = new Handler();
    private final int INTERVAL = 500;

    public LocalPresenterImpl(LocalView view) {
        mModel = model();
        mView = view;
    }

    @Override
    public boolean hasPermission() {
        return mPermission;
    }

    @Override
    public void setPermission(boolean permission) {
        this.mPermission = permission;
    }

    @Override
    public LocalModel model() {
        return ModelFactory.createLocalModel();
    }

    @Override
    public void loadData() {
        if (!mLoading) {
            mCancel = false;
            mView.onStartLoad();
            mLoading = true;
            innerLoadData();
        }
    }

    @Override
    public void cancelLoad() {
        if (mLoading) {
            mCancel = true;
        }
    }

    private void onCanceled() {
        mLoading = false;
        mView.onCanceled();
        mCancel = false;
    }

    private void innerLoadData() {
        if (hasPermission()) {
            mModel.loadData(new Model.OnLoadedListener<List<BillBoardBean.Song>>() {
                @Override
                public void onLoaded(List<BillBoardBean.Song> songs) {
                    if (mCancel) {
                        onCanceled();
                        return;
                    }
                    mLoading = false;
                    mView.onLoaded(songs);
                }
            });
        } else {
            handler.postDelayed(loadDataRunnable, INTERVAL);
        }
    }

    private final Runnable loadDataRunnable = new Runnable() {
        @Override
        public void run() {
            if (mCancel) {
                onCanceled();
                return;
            }
            innerLoadData();
        }
    };
}
