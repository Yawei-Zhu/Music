package com.wind.music.view;

import com.wind.music.bean.BillBoardBean;

import java.util.List;

/**
 * Created by Administrator on 2017/5/19.
 */

public interface SongView extends View {

    void onLoaded(int type, BillBoardBean songs);

}
