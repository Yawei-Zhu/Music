package com.wind.music.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wind.music.R;
import com.wind.music.util.MusicPlayer;

public class BaseDialog<T> extends AppCompatDialogFragment {

    private OnAttachListener mOnAttachListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnAttachListener)
        {
            mOnAttachListener = (OnAttachListener) context;
        }
    }

    public interface OnAttachListener<T> {
        void onAttach(T t);
    }
}
