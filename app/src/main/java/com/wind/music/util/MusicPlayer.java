package com.wind.music.util;

import com.wind.music.bean.Song;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface MusicPlayer {
    void setData(List<Song> data);
    void play();
    void play(int index);
    void pause();
    boolean isPlaying();
    int whatIsPlaying();
    int currentPosition();
    void seekTo(int position);
    void previous();
    void next();
    int changePlayMode();
    int getPlayMode();
    void registerPlayInfoListener(PlayInfoListener listener);
    void unregisterPlayInfoListener(PlayInfoListener listener);

    public interface PlayInfoListener {
        void playInfo(int index, int position);
    }

}
