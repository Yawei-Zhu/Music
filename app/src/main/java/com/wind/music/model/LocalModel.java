package com.wind.music.model;

/**
 * Created by Administrator on 2017/6/20.
 */

public interface LocalModel extends Model {
    public void loadData();

    public interface OnLoadedListener {
        public void onLoaded();
    }
}
