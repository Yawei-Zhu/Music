package com.wind.music.presenter;

public interface SearchPresenter extends Presenter {

    void query(String key);
    void cancel(String key);

}
