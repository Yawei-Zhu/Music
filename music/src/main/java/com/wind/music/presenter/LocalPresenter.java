package com.wind.music.presenter;

import com.wind.music.model.LocalModel;
import com.wind.music.view.View;

/**
 * Created by Administrator on 2017/6/20.
 */

public interface LocalPresenter extends Presenter {

    @Override
    LocalModel model();

    void loadData();
    void cancelLoad();

    boolean hasPermission();
    void setPermission(boolean permission);
}
