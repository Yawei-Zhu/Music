package com.wind.music.view;

import com.wind.music.bean.BillBoardBean;
import com.wind.music.presenter.LocalPresenter;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public interface LocalView extends View {

    @Override
    LocalPresenter presenter();

    void onStartLoad();

    void onLoaded(List<BillBoardBean.Song> result);

    void onCanceled();
}
