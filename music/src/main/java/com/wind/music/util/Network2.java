package com.wind.music.util;

import com.wind.music.bean.BillBoardBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/8/5.
 */

public interface Network2 {
    @GET("ting")
    Call<BillBoardBean> listSong(
            @Query(Urls.FORMAT) String format,
            @Query(Urls.METHOD) String method,
            @Query(Urls.TYPE) int type,
            @Query(Urls.SIZE) int size,
            @Query(Urls.OFFSET) int offset);
}
