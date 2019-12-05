package com.wind.music.model.impl;

import com.wind.music.bean.BillBoardBean;
import com.wind.music.model.LocalModel;
import com.wind.music.util.AsyncExecutor;
import com.wind.music.util.LoadLocal;

import java.util.List;

public class LocalModelImpl implements LocalModel {
    @Override
    public  void loadData(OnLoadedListener<List<BillBoardBean.Song>> l) {
        final OnLoadedListener<List<BillBoardBean.Song>> listener = l;
        AsyncExecutor.of().execute(null, new AsyncExecutor.Executable<Void, List<BillBoardBean.Song>>() {
            @Override
            public List<BillBoardBean.Song> onLoading(Void param) {
                return LoadLocal.loadData();
            }

            @Override
            public void onLoaded(Void param, List<BillBoardBean.Song> result) {
                listener.onLoaded(result);
            }
        });
    }
}
