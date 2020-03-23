package com.wind.music.presenter;

/**
 * Created by Administrator on 2017/6/20.
 */

public interface SongPresenter extends Presenter {

    void loadData(int type);
    void cancel();

}
