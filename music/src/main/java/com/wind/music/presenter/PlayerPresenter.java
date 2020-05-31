package com.wind.music.presenter;

public interface PlayerPresenter extends Presenter {

    void loadSongInfo(String songId);
    void cancelSongInfo(String songId);

    void cacheSong(String path);
    void cancelSong(String path);

}
