package com.wind.music.event;

/**
 * Created by Administrator on 2017/5/21.
 */

public class NoticeEvent extends Event {
    public static final int WHAT_NONE = 0;
    public static final int WHAT_DATA_LOADED = 1;

    public int what;
    public Object extra;

    public NoticeEvent() {
        this(WHAT_NONE);
    }

    public NoticeEvent(int what) {
        this(what, null);
    }

    public NoticeEvent(int what, Object extra) {
        this.what = what;
        this.extra = extra;
    }
}
