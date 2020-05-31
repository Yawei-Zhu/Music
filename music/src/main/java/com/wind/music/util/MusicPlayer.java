package com.wind.music.util;

import com.wind.music.bean.Song;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface MusicPlayer {
    int MODE_CYCLE = 0;
    int MODE_SINGLE = 1;
    int MODE_RANDOM = 2;
    int MODE_MAX = 3;

    void setData(List<Song> data);
    List<Song> getData();
    int insert(int index, Song song);
    Song remove(int index);
    void play();
    void play(int index);
    void pause();
    boolean isPlaying();
    int whatIsPlaying();
    int getCurrentPosition();
    int getDuration();
    void seekTo(int position);
    void previous();
    void next();
    int changePlayMode();
    int getPlayMode();
    void addOnPlayInfoListener(OnPlayInfoListener listener);
    void removeOnPlayInfoListener(OnPlayInfoListener listener);

    public interface OnPlayInfoListener {
        void onPlayInfo(Song song, int position);
    }

}
