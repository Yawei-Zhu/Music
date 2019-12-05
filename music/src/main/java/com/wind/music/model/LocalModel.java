package com.wind.music.model;

import com.wind.music.bean.BillBoardBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public interface LocalModel extends Model {
    public void loadData(OnLoadedListener<List<BillBoardBean.Song>> l);
}
