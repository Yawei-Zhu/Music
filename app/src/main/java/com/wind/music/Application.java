package com.wind.music;

import com.wind.music.util.Network;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Application extends android.app.Application {

    @Override
    public void onCreate() {
        Network.init(this);
    }

    @Override
    public void onTerminate() {
        Network.release();
    }
}
