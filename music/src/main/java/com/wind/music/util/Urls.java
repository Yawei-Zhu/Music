package com.wind.music.util;

import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Administrator on 2017/5/8.
 */

public final class Urls {
    public final String TAG = getClass().getSimpleName();

    public final String BASE_URL = "http://tingapi.ting.baidu.com/v1/restserver/ting";
    public final String FORMAT = "format";
    public final String FORMAT_JSON = "json";
    public final String FORMAT_XML = "xml";

    public final String METHOD = "method";
    public final String METHOD_BILL = "baidu.ting.billboard.billList";
    public final String METHOD_PLAY = "baidu.ting.song.play";

    public final String TYPE = "type";

    public final String SIZE = "size";
    public final int SIZE_DEFAULT = 20;

    public final String OFFSET = "offset";
    public final int SIZE_OFFSET = 0;

    public final String SONG_ID = "songid";

    public URL getBillUrl(int type, int size) throws MalformedURLException {
        // http://tingapi.ting.baidu.com/v1/restserver/ting?format=json&method=baidu.ting.billboard.billList&type=1&size=20&offset=0

        StringBuilder url = new StringBuilder();
        url.append(BASE_URL)
                .append("?").append(FORMAT).append("=").append(FORMAT_JSON)
                .append("&").append(METHOD).append("=").append(METHOD_BILL)
                .append("&").append(TYPE).append("=").append(type)
                .append("&").append(SIZE).append("=").append(size)
                .append("&").append(OFFSET).append("=").append(SIZE_OFFSET);

        Log.d(TAG, "getBillUrl: url=" + url.toString());

        return new URL(url.toString());
    }

    public URL getSongInfoUrl(int id) throws MalformedURLException {
        StringBuilder url = new StringBuilder();
        url.append(BASE_URL)
                .append("?").append(FORMAT).append("=").append(FORMAT_JSON)
                .append("&").append(METHOD).append("=").append(METHOD_PLAY)
                .append("&").append(SONG_ID).append("=").append(id);

        Log.d(TAG, "getSongInfoUrl: url=" + url.toString());

        return new URL(url.toString());
    }


}
