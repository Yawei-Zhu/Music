package com.wind.music.model;

import com.wind.music.bean.SongInfoBean;

public interface PlayerModel extends Model {
    SongInfoBean loadSongInfo(int songId);
    String cacheSong(String path);
}
