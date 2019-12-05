package com.wind.music.fragment;

import android.support.v4.app.Fragment;

/**
 * Created by Administrator on 2017/5/8.
 */

public class BaseFragment extends Fragment {

    public static final CharSequence TITLE_DEFAULT = "";

    private CharSequence mTitle = TITLE_DEFAULT;

    public CharSequence getTitle() {
        return mTitle;
    }

    public void setTitle(CharSequence title) {
        this.mTitle = title;
    }

}
