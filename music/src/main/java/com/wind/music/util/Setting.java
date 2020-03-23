package com.wind.music.util;

import android.content.Context;
import android.content.SharedPreferences;

public class Setting {
    private final String NAME = "setting";

    private final String KEY_MIN_LOCAL_LENGTH = "setting.min.local.length";
    private final int DEFAULT_MIN_LOCAL_LENGTH = 60;

    private final String KEY_MAX_NETWORK_SONGS_NUMBER = "setting.max.network.songs.number";
    private final int DEFAULT_MAX_NETWORK_SONGS_NUMBER = 40;

    private SharedPreferences mPreference;
    private int minLocalLength;
    private int maxNetworkSongsNumber;

    public Setting(Context context) {
        mPreference = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        minLocalLength = mPreference.getInt(KEY_MIN_LOCAL_LENGTH, DEFAULT_MIN_LOCAL_LENGTH);
        maxNetworkSongsNumber = mPreference.getInt(KEY_MAX_NETWORK_SONGS_NUMBER, DEFAULT_MAX_NETWORK_SONGS_NUMBER);
    }

    public int getMinLocalLength() {
        return minLocalLength;
    }

    public void setMinLocalLength(int minLocalLength) {
        if (this.minLocalLength != minLocalLength) {
            this.minLocalLength = minLocalLength;
            mPreference.edit().putInt(KEY_MIN_LOCAL_LENGTH, minLocalLength).apply();
        }
    }

    public int getMaxNetworkSongsNumber() {
        return maxNetworkSongsNumber;
    }

    public void setMaxNetworkSongsNumber(int maxNetworkSongsNumber) {
        if (this.maxNetworkSongsNumber != maxNetworkSongsNumber) {
            this.maxNetworkSongsNumber = maxNetworkSongsNumber;
            mPreference.edit().putInt(KEY_MAX_NETWORK_SONGS_NUMBER, maxNetworkSongsNumber).apply();
        }
    }
}
