package com.wind.music.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wind.music.bean.Song;
import com.wind.music.presenter.PresenterFactory;
import com.wind.music.presenter.SearchPresenter;
import com.wind.music.view.SearchView;

import java.util.List;

public class SearchSongFragment extends BaseSongFragment implements SearchView {

    private SearchPresenter mPresenter;
    private String mCurrQuery;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        getRefreshLayout().setEnabled(false);
        mPresenter = PresenterFactory.createSearchPresenter(this);
        return view;
    }

    @Override
    public void onDestroyView() {
        getRefreshLayout().setEnabled(true);
        mPresenter.setView(null);
        mPresenter = null;
        super.onDestroyView();
    }

    @Override
    public void onLoaded(String key, List<Song> result) {
        if (TextUtils.equals(mCurrQuery, key)) {
            mCurrQuery = null;
            getSongs().clear();
            getSongs().addAll(result);
            mSongAdapter.notifyDataSetChanged();
            getRefreshLayout().setRefreshing(false);
        }
    }

    public void query(String query) {
        if (query != null) {
            query = query.trim();
        }
        if (!TextUtils.equals(mCurrQuery, query)) {
            if (!TextUtils.isEmpty(mCurrQuery)) {
                mPresenter.cancel(mCurrQuery);
                getRefreshLayout().setRefreshing(false);
            }
            mCurrQuery = query;
            if (!TextUtils.isEmpty(query)) {
                mPresenter.query(query);
                getRefreshLayout().setRefreshing(true);
            }
        }
    }

    public void cancel() {
        if (!TextUtils.isEmpty(mCurrQuery)) {
            mPresenter.cancel(mCurrQuery);
            mCurrQuery = null;
            getRefreshLayout().setRefreshing(false);
        }
    }
}
