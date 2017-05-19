package com.wind.music;

import com.wind.music.bean.Song;
import com.wind.music.util.LoadLocal;
import com.wind.music.util.Network;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Application extends android.app.Application {

    private static Application app;
    private List<Song> localSongs = new ArrayList<>();

    @Override
    public void onCreate() {
        app = this;
        Network.init(this);
        LoadLocal.init(this);
    }

    @Override
    public void onTerminate() {
        app = null;
        Network.release();
        LoadLocal.release();
    }

    public static Application getApp() {
        return app;
    }

    public List<Song> getLocalSongs() {
        return localSongs;
    }
}
