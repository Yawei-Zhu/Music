package com.wind.music.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wind.music.bean.BillBoardBean;
import com.wind.music.presenter.LocalPresenter;
import com.wind.music.presenter.PresenterFactory;
import com.wind.music.view.LocalView;

import java.util.List;

/**
 * Created by Wind on 2017/8/19.
 */

public class LocalSongFragment extends SongFragment implements LocalView {
    private LocalPresenter mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = presenter();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.loadData();
    }

    @Override
    public void onDestroyView() {
        mPresenter.cancelLoad();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        mPresenter = null;
        super.onDestroy();
    }

    @Override
    public void refresh() {
        mPresenter.loadData();
    }

    public void setPermission(boolean permission) {
        mPresenter.setPermission(permission);
    }

    @Override
    public LocalPresenter presenter() {
        return PresenterFactory.createLocalPresenter(this);
    }

    @Override
    public void onStartLoad() {
        getRefreshLayout().setRefreshing(true);
    }

    @Override
    public void onLoaded(List<BillBoardBean.Song> result) {
        getRefreshLayout().setRefreshing(false);
        List<BillBoardBean.Song> songs = getSongs();
        songs.clear();
        songs.addAll(result);
        if (mSongAdapter != null) {
            mSongAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onCanceled() {
        getRefreshLayout().setRefreshing(false);
    }
}
