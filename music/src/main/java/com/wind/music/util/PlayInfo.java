package com.wind.music.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wind.music.bean.BillBoardBean;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/20.
 */

public class PlayInfo {
    private final String NAME = "play_info";
    private final String KEY_MODE = "play_mode";
    private final String KEY_INDEX = "play_index";
    private final String KEY_SONGS = "play_songs";

    private SharedPreferences mPreference;
    private Gson gson;

    private int mode;
    private int index;
    private List<BillBoardBean.Song> songs;

    public PlayInfo(Context context) {
        mPreference = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        gson = new Gson();

        mode = mPreference.getInt(KEY_MODE, MusicPlayer.MODE_CYCLE);
        index = mPreference.getInt(KEY_INDEX, 0);
        String s = mPreference.getString(KEY_SONGS, "");
        songs = gson.fromJson(s, new TypeToken<List<BillBoardBean.Song>>(){}.getType());
        if (songs == null) {
            songs = new LinkedList<>();
        }
    }

    public void setMode(int mode) {
        if (this.mode != mode) {
            this.mode = mode;
            mPreference.edit().putInt(KEY_MODE, mode).apply();
        }
    }

    public int getMode() {
        return mode;
    }

    public void setIndex(int index) {
        if (this.index != index) {
            this.index = index;
            mPreference.edit().putInt(KEY_INDEX, index).apply();
        }
    }

    public int getIndex() {
        return index;
    }

    public void setSongs(List<BillBoardBean.Song> songs) {
        if (songs == null) {
            songs = new ArrayList<>();
        }
        this.songs = songs;
        mPreference.edit().putString(KEY_SONGS, gson.toJson(songs)).apply();
    }

    public List<BillBoardBean.Song> getSongs() {
        return songs;
    }
}
