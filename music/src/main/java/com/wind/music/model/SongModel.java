package com.wind.music.model;

import com.wind.music.bean.BillBoardBean;
import com.wind.music.bean.SearchBean;
import com.wind.music.bean.Song;
import com.wind.music.bean.SongInfoBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public interface SongModel extends Model {

    List<Song> loadLocalSongs(int minLength);

    BillBoardBean loadNetworkSongs(int type, int size);

    SongInfoBean loadSongInfo(String songId);

    String cacheSong(String path);

    SearchBean query(String key);

}
