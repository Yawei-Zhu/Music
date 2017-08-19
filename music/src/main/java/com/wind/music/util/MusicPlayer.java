package com.wind.music.util;

import com.wind.music.bean.BillBoardBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/21.
 */

public interface MusicPlayer {
    void setData(List<BillBoardBean.Song> data);
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
    void registerOnPlayInfoListener(OnPlayInfoListener listener);
    void unregisterOnPlayInfoListener(OnPlayInfoListener listener);

    public interface OnPlayInfoListener {
        void onPlayInfo(int index, int position);
    }

}
