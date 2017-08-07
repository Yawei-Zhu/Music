package com.wind.music.view;

import com.wind.music.bean.Song;
import com.wind.music.util.MusicPlayer;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public interface LocalView {
    public void bindData(List<Song> data);
    public void bindPlayer(MusicPlayer player);
}
