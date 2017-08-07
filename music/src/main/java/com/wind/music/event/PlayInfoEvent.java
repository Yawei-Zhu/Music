package com.wind.music.event;

/**
 * Created by Administrator on 2017/5/18.
 */

public class PlayInfoEvent extends Event {
    public static final int WHAT_SONG_NONE = 0;
    public static final int WHAT_SONG_PLAYING = 1;
    public static final int WHAT_SONG_INDEX = 2;
    public static final int WHAT_SONG_POSITION = 3;
    public static final int WHAT_SONG_MODE = 4;

    public int what;
    public Object extra;

    public PlayInfoEvent() {
        this(WHAT_SONG_NONE);
    }

    public PlayInfoEvent(int what) {
        this(what, null);
    }

    public PlayInfoEvent(int what, Object extra) {
        this.what = what;
        this.extra = extra;
    }
}
