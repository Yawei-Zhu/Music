package com.wind.music.model;

import com.wind.music.bean.BillBoardBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public interface SongModel extends Model {

    BillBoardBean loadLocalSongs(int minLength);
    BillBoardBean loadNetworkSongs(int type, int size);

}
