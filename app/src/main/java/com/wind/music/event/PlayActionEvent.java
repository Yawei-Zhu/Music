package com.wind.music.event;

/**
 * Created by Administrator on 2017/5/18.
 */

public class PlayActionEvent extends Event {

    public static final int WHAT_NONE = 0;
    public static final int WHAT_PLAY = 1;
    public static final int WHAT_PAUSE = 2;
    public static final int WHAT_PREVIOUS = 3;
    public static final int WHAT_NEXT = 4;
    public static final int WHAT_SEEK = 5;
    public static final int WHAT_MODE = 6;


    public int what;
    public Object extra;

    public PlayActionEvent() {
        this(WHAT_NONE);
    }

    public PlayActionEvent(int what) {
        this(what, null);
    }

    public PlayActionEvent(int what, Object extra) {
        this.what = what;
        this.extra = extra;
    }
}
