package com.wind.music.presenter;

import com.wind.music.model.ImageModel;

public interface ImagePresenter extends Presenter {

    ImageModel model();

    int load(String path);

    boolean cancel(String path);

}
