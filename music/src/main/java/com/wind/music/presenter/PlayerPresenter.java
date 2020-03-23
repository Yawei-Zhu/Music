package com.wind.music.presenter;

public interface PlayerPresenter extends Presenter {

    void loadSongInfo(int songId);
    void cancelSongInfo(int songId);

    void cacheSong(String path);
    void cancelSong(String path);

}
