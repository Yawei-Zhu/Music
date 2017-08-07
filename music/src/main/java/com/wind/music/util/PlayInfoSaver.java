package com.wind.music.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.wind.music.service.PlayerService;

/**
 * Created by Administrator on 2017/5/20.
 */

public class PlayInfoSaver {
    private final String KEY_MODE = "play_mode";
    private final String KEY_INDEX = "play_index";
    private final String KEY_POSITION = "play_position";

    private SharedPreferences mPreference;

    public PlayInfoSaver(Context context) {
        mPreference = context.getSharedPreferences("play_info", Context.MODE_PRIVATE);
    }

    public void saveMode(int mode) {
        if (mPreference != null) {
            mPreference.edit().putInt(KEY_MODE, mode).commit();
        }
    }

    public int readMode() {
        if (mPreference != null) {
            return mPreference.getInt(KEY_MODE, PlayerService.MODE_CYCLE);
        } else {
            return PlayerService.MODE_CYCLE;
        }
    }

    public void saveIndex(int index) {
        if (mPreference != null) {
            mPreference.edit().putInt(KEY_INDEX, index).commit();
        }
    }

    public int readIndex() {
        if (mPreference != null) {
            return mPreference.getInt(KEY_INDEX, 0);
        } else {
            return 0;
        }
    }

    public void savePosition(int position) {
        if (mPreference != null) {
            mPreference.edit().putInt(KEY_POSITION, position).commit();
        }
    }

    public int readPosition() {
        if (mPreference != null) {
            return mPreference.getInt(KEY_POSITION, 0);
        } else {
            return 0;
        }
    }

    public void release() {
        if (mPreference != null) {
            mPreference.edit().commit();
            mPreference = null;
        }
    }
}
