package com.wind.music;

import android.content.Intent;

import com.wind.music.service.PlayerService;
import com.wind.music.util.LoadLocal;
import com.wind.music.util.Network;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Application extends android.app.Application {

    private static Application app;
    private int currSongType = -1;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Network.init(this);
        LoadLocal.init(this);
        Intent playerService = new Intent(this, PlayerService.class);
        startService(playerService);
    }

    @Override
    public void onTerminate() {
        Intent playerService = new Intent(this, PlayerService.class);
        stopService(playerService);
        Network.release();
        LoadLocal.release();
        app = null;
        super.onTerminate();
    }

    public static Application getApp() {
        return app;
    }

    /**
     * get the type of the playing song
     * @return 0 for local; other for net
     */
    public int getCurrSongType() {
        return currSongType;
    }

    /**
     * set the type of the playing song
     * @param type {@link Application#getCurrSongType Application.getCurrSongType}
     */
    public void setCurrSongType(int type) {
        this.currSongType = type;
    }
}
