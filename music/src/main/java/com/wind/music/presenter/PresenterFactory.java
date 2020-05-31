package com.wind.music.presenter;

import com.wind.music.presenter.impl.ImagePresenterImpl;
import com.wind.music.presenter.impl.PlayerPresenterImpl;
import com.wind.music.presenter.impl.SearchPresenterImpl;
import com.wind.music.presenter.impl.SongPresenterImpl;
import com.wind.music.service.PlayerService;
import com.wind.music.view.ImageView;
import com.wind.music.view.PlayerView;
import com.wind.music.view.SearchView;
import com.wind.music.view.SongView;

public final class PresenterFactory {
    public static SongPresenter createLocalPresenter(SongView view) {
        SongPresenter presenter = new SongPresenterImpl();
        presenter.setView(view);
        return presenter;
    }

    public static ImagePresenter createImagePresenter(ImageView view) {
        ImagePresenter presenter = new ImagePresenterImpl();
        presenter.setView(view);
        return presenter;
    }

    public static PlayerPresenter createPlayerPresenter(PlayerView view) {
        PlayerPresenter presenter = new PlayerPresenterImpl();
        presenter.setView(view);
        return presenter;
    }
    public static SearchPresenter createSearchPresenter(SearchView view) {
        SearchPresenter presenter = new SearchPresenterImpl();
        presenter.setView(view);
        return presenter;
    }
}
