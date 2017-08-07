package com.wind.music.util;

import android.content.Context;
import android.support.annotation.StringRes;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/5/8.
 */

public class Toasts {

    private static final int LENGTH_DEFAULT = Toast.LENGTH_SHORT;
    private static Toast toast;

    public static Toast show(Context context, @StringRes int id) {
        return show(context, id, LENGTH_DEFAULT);
    }

    public static Toast show(Context context, CharSequence text) {
        return show(context, text, LENGTH_DEFAULT);
    }

    public static Toast show(Context context, @StringRes int id, int duration) {
        return show(context, context.getString(id), duration);
    }

    public static Toast show(Context context, CharSequence text, int duration) {
        if (toast != null) {
            toast.cancel();
        }

        toast = Toast.makeText(context, text, duration);
        toast.show();
        return toast;
    }

}
