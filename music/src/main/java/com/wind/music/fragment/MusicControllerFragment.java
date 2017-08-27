package com.wind.music.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wind.music.R;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.service.PlayerService;
import com.wind.music.util.MusicPlayer;

import java.util.Locale;

/**
 * Created by Wind on 2017/8/27.
 */

public class MusicControllerFragment extends BaseFragment {

    private TextView tvCurrent;
    private TextView tvDuration;
    private SeekBar sbProgress;
    private Button btPlay;
    private Button btMode;
    private TextView tvTitle;

    private MusicPlayer mPlayer;
    private boolean isProgressTrackingTouch = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_music_controller, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sbProgress = (SeekBar) view.findViewById(R.id.sb_progress);
        if (sbProgress != null) {
            sbProgress.setOnSeekBarChangeListener(onSeekBarChangeListener);
        }

        tvCurrent = (TextView) view.findViewById(R.id.tv_current);
        tvDuration = (TextView) view.findViewById(R.id.tv_duration);

        btMode = (Button) view.findViewById(R.id.bt_mode);
        if (btMode != null) {
            btMode.setOnClickListener(switchPlayModeListener);
        }

        tvTitle = (TextView) view.findViewById(R.id.tv_title);

        btPlay = (Button) view.findViewById(R.id.bt_play);
        if (btPlay != null) {
            btPlay.setOnClickListener(pauseListener);
        }
    }

    private final View.OnClickListener switchPlayModeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mPlayer != null) {
                int mode = mPlayer.changePlayMode();
                updateMode(mode);
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
            updateDuration(mPlayer == null ? 0 : mPlayer.getDuration());
            updatePosition(position);
        }
    };

    private final SeekBar.OnSeekBarChangeListener onSeekBarChangeListener = new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (tvCurrent != null) {
                tvCurrent.setText(time2String(progress));
            }
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {
            isProgressTrackingTouch = true;
        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            isProgressTrackingTouch = false;
            if (mPlayer != null) {
                mPlayer.seekTo(seekBar.getProgress());
            }
        }
    };

    public void updatePlaying(boolean isPlaying) {
        if (btPlay != null) {
            if (isPlaying) {
                btPlay.setText(R.string.musicplayer_pause);
            } else {
                btPlay.setText(R.string.musicplayer_play);
            }
        }
    }

    public void updateMode(int mode) {
        int s = 0;
        switch (mode) {
            case PlayerService.MODE_CYCLE:
                s = R.string.musicplayer_mode_cycle;
                break;
            case PlayerService.MODE_ORDER:
                s = R.string.musicplayer_mode_order;
                break;
            case PlayerService.MODE_RANDOM:
                s = R.string.musicplayer_mode_random;
                break;
            case PlayerService.MODE_SINGLE:
                s = R.string.musicplayer_mode_single;
                break;
        }

        if (btMode != null && s != 0) {
            btMode.setText(s);
        }
    }

    private void updateSong(BillBoardBean.Song song) {
        if (tvTitle != null) {
            tvTitle.setText(song.getTitle());
        }
    }

    private String time2String(int time) {
        String s;
        int totalSecond = time / 1000;
        int second = totalSecond % 60;
        int minute = totalSecond / 60 % 60;
        int hour = totalSecond / 3600;
        if (hour == 0) {
            s = String.format(Locale.CHINA, "%02d:%02d", minute, second);
        } else {
            s = String.format(Locale.CHINA, "%d:%02d:%02d", hour, minute, second);
        }
        return s;
    }

    private void updateDuration(int duration) {
        if (sbProgress != null) {
            sbProgress.setMax(duration);
        }
        if (tvDuration != null) {
            tvDuration.setText(time2String(duration));
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
                updateMode(player.getPlayMode());
            }
        }
    }
}
