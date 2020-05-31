package com.wind.music.fragment;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;

import com.wind.music.R;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.bean.Song;
import com.wind.music.presenter.SongPresenter;
import com.wind.music.presenter.PresenterFactory;
import com.wind.music.view.SongView;

import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */

public class SongFragment extends BaseSongFragment implements SongView {

    public static final int TYPE_LOCAL = 0;
    public static final int TYPE_NEW = 1;
    public static final int TYPE_HOT = 2;
    private int mType;
    private SongPresenter mPresenter;

    public static SongFragment of(int type) {
        SongFragment instance = new SongFragment();
        instance.setType(type);
        return instance;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = PresenterFactory.createLocalPresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        mPresenter.setView(null);
        mPresenter = null;
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override @StringRes
    public int getTitle() {
        int resId = 0;
        switch (getType()) {
            case TYPE_LOCAL:
                resId = R.string.title_local;
                break;
            case TYPE_NEW:
                resId = R.string.title_new;
                break;
            case TYPE_HOT:
                resId = R.string.title_hot;
                break;
        }

        return resId;
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
        mPresenter.loadData(getType());
    }

    @Override
    public void onLoaded(int type, List<Song> result) {
        getRefreshLayout().setRefreshing(false);
        if (result != null) {
            mSongAdapter.getData().addAll(result);
            mSongAdapter.notifyDataSetChanged();
        }
    }
}
