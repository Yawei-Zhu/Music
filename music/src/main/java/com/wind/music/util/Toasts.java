package com.wind.music.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

import java.lang.ref.PhantomReference;
import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Toasts {
    private static final int LENGTH_SHORT = Toast.LENGTH_SHORT;
    private static final int LENGTH_LONG = Toast.LENGTH_LONG;

    /*
     * use weak reference
     */
    private static WeakReference<Toast> mToastRef = null;

    public static Toast show(Context context, @StringRes int id) {
        return show(context, id, LENGTH_SHORT);
    }

    public static Toast show(Context context, CharSequence text) {
        return show(context, text, LENGTH_SHORT);
    }

    public static Toast show(Context context, @StringRes int id, int duration) {
        return show(context, context.getString(id), duration);
    }

    public static Toast show(Context context, CharSequence text, int duration) {
        Toast toast;

        if (mToastRef != null) {
            toast = mToastRef.get();
            if (toast != null) {
                toast.cancel();
            }
        }

        toast = Toast.makeText(context, text, duration);
        toast.show();

        mToastRef = new WeakReference<>(toast);
        return toast;
    }

}
