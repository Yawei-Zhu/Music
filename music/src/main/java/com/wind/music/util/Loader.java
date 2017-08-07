package com.wind.music.util;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Loader {
    public static void loadBillBoard(int type, int offset, NetworkListener listener) {
        String url = Urls.getBillUrl(type, offset);
        Network.load(url, listener);
    }
}
