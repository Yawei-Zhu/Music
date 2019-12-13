package com.wind.music.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.wind.music.R;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.dialog.MusicListDialog;
import com.wind.music.service.PlayerService;
import com.wind.music.util.MusicPlayer;

/**
 * Created by Wind on 2017/8/27.
 */

public class MusicControllerFragment extends BaseFragment {
    private final String TAG = getClass().getSimpleName();

    private ProgressBar sbProgress;
    private ImageButton btPlay;
    private TextView tvTitle;
    private ImageView ivPic;

    private MusicPlayer mPlayer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_controller, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sbProgress = (ProgressBar) view.findViewById(R.id.sb_progress);

        ImageButton btList = (ImageButton) view.findViewById(R.id.bt_list);
        if (btList != null) {
            btList.setOnClickListener(mOnListClickListener);
        }
        ivPic = (ImageView) view.findViewById(R.id.iv_pic);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);

        btPlay = (ImageButton) view.findViewById(R.id.bt_play);
        if (btPlay != null) {
            btPlay.setOnClickListener(pauseListener);
            btPlay.setSelected(true);
        }
    }

    private final View.OnClickListener mOnListClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPlayer != null) {
                MusicListDialog listDialog = new MusicListDialog();
                listDialog.setMusicPlayer(mPlayer);
                listDialog.show(getFragmentManager(), TAG);
            }
        }
    };

    private final View.OnClickListener pauseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPlayer != null && mPlayer.getData() != null) {
                if (mPlayer.isPlaying()) {
                    mPlayer.pause();
                    updatePlaying(false);
                } else {
                    mPlayer.play();
                    updatePlaying(true);
                }
            }
        }
    };
    private final MusicPlayer.OnPlayInfoListener onPlayInfoListener = new MusicPlayer.OnPlayInfoListener() {
        BillBoardBean.Song oldSong = null;

        @Override
        public void onPlayInfo(BillBoardBean.Song song, int position) {
            if (oldSong != song) {
                updateSong(oldSong = song);
            }
            updateDuration(song == null ? 100 : song.getFile_duration());
            updatePosition(position);
        }
    };

    public void updatePlaying(boolean isPlaying) {
        if (btPlay != null) {
            btPlay.setActivated(isPlaying);
        }
    }

    private void updateSong(BillBoardBean.Song song) {
        if (tvTitle != null) {
            tvTitle.setText(song.getTitle());
        }
        Glide.with(this).load(song.getPic_small()).into(ivPic);
    }

    private void updateDuration(int duration) {
        if (sbProgress != null) {
            sbProgress.setMax(duration);
        }
    }

    private void updatePosition(int position) {
        if (sbProgress != null) {
            sbProgress.setProgress(position);
        }
    }

    public MusicPlayer getPlayer() {
        return mPlayer;
    }

    public void setPlayer(MusicPlayer player) {
        if (mPlayer != player) {
            if (mPlayer != null) {
                mPlayer.unregisterOnPlayInfoListener(onPlayInfoListener);
            }
            mPlayer = player;
            if (mPlayer != null) {
                mPlayer.registerOnPlayInfoListener(onPlayInfoListener);

                updatePlaying(player.isPlaying());
            } else {
                updatePlaying(false);
            }
        }
    }
}
