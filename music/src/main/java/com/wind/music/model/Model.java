package com.wind.music.model;

/**
 * Created by Administrator on 2017/6/19.
 */

public interface Model {

    public interface OnLoadedListener<R> {
        public void onLoaded(R result);
    }
}
