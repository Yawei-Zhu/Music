package com.wind.music;

import android.content.Intent;

import com.wind.music.bean.Song;
import com.wind.music.event.NoticeEvent;
import com.wind.music.service.PlayerService;
import com.wind.music.util.LoadLocal;
import com.wind.music.util.LoadLocalListener;
import com.wind.music.util.Network;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Application extends android.app.Application {

    private static Application app;
    private Intent playerService;
    private List<Song> localSongs = new ArrayList<>();

    @Override
    public void onCreate() {
        app = this;
        Network.init(this);
        LoadLocal.init(this);
        loadLocalData();
        playerService = new Intent(this, PlayerService.class);
        startService(playerService);
    }

    @Override
    public void onTerminate() {
        stopService(playerService);
        Network.release();
        LoadLocal.release();
        app = null;
    }

    public static Application getApp() {
        return app;
    }

    public List<Song> getLocalSongs() {
        return localSongs;
    }

    private void loadLocalData() {
        LoadLocal.loadSongs(new LoadLocalListener<List<Song>>() {
            @Override
            public void onRespond(List<Song> data) {
                localSongs.clear();
                localSongs.addAll(data);
                EventBus.getDefault().post(new NoticeEvent(NoticeEvent.WHAT_DATA_LOADED));
            }
        });
    }
}
