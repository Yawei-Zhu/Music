package com.wind.music.presenter;

import com.wind.music.presenter.impl.LocalPresenterImpl;
import com.wind.music.view.LocalView;
import com.wind.music.view.View;

public final class PresenterFactory {
    public static LocalPresenter createLocalPresenter(LocalView view) {
        return new LocalPresenterImpl(view);
    }
}
