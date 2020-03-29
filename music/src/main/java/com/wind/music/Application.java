package com.wind.music;

import android.content.Intent;

import com.wind.music.service.PlayerService;
import com.wind.music.util.Setting;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Application extends android.app.Application {

    private static Application app;
    private Setting mSetting;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        mSetting = new Setting(this);
        Intent playerService = new Intent(this, PlayerService.class);
        startService(playerService);
    }

    @Override
    public void onTerminate() {
        Intent playerService = new Intent(this, PlayerService.class);
        stopService(playerService);
        app = null;
        super.onTerminate();
    }

    public static Application getApp() {
        return app;
    }

    /**
     * Music setting
     * @return setting
     */
    public Setting getSetting() {
        return mSetting;
    }
}
