package com.wind.music.presenter;

/**
 * Created by Administrator on 2017/6/20.
 */

public interface LocalPresenter extends Presenter {
    public void loadData();
    public void bindPlayer();
    public void unbindPlayer();
    public void onPermissionResult(boolean result);
}
