package com.wind.music.view;

import com.wind.music.bean.SongInfoBean;
import com.wind.music.presenter.PlayerPresenter;

public interface PlayerView extends View {

    void onSongInfoLoaded(int songId, SongInfoBean songInfo);

    void onSongCaching(String srcPath, String cachePath, int percent);

    void onSongCached(String srcPath, String cachePath);
}
