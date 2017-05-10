package com.wind.music.widget;

import android.content.Context;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.wind.music.R;

/**
 * Created by Administrator on 2017/5/9.
 */

public class MusicBar extends FrameLayout {

    private ImageView mMusicBarPlay;
    private TextView mMusicBarTitle;
    private TextView mMusicBarName;
    private ImageView mMusicBarPic;
    private MediaPlayer mPlayer;

    public MusicBar(Context context) {
        this(context, null);
    }

    public MusicBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MusicBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initView();
    }

    private void initView() {
        View layout = View.inflate(getContext(), R.layout.layout_musicbar, null);
        addView(layout);

        mMusicBarPlay = (ImageView) findViewById(R.id.musicbar_play);
        mMusicBarTitle = (TextView) findViewById(R.id.musicbar_title);
        mMusicBarName = (TextView) findViewById(R.id.musicbar_name);
        mMusicBarPic = (ImageView) findViewById(R.id.musicbar_pic);

        mMusicBarPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPlayer != null) {
                    if (mPlayer.isPlaying()) {

                    } else {

                    }
                }
            }
        });
    }
}
