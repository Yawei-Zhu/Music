package com.wind.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.wind.music.Application;
import com.wind.music.bean.Song;
import com.wind.music.event.PlayActionEvent;
import com.wind.music.event.PlayInfoEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;
import java.util.Random;

/**
 * Created by Administrator on 2017/5/18.
 */

public class PlayerService extends Service {
    public static final int MODE_CYCLE = 0;
    public static final int MODE_ORDER = 1;
    public static final int MODE_RANDOM = 2;
    public static final int MODE_SINGLE = 3;

    private List<Song> songs;
    private MediaPlayer player;
    private int index = 0;
    private int pausePosition = 0;
    private int mode = 0;

    @Override
    public void onCreate() {
        EventBus.getDefault().register(this);
        songs = Application.getApp().getLocalSongs();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        EventBus.getDefault().post(new PlayInfoEvent(PlayInfoEvent.WHAT_SONG_MODE, mode));
        EventBus.getDefault().post(new PlayInfoEvent(PlayInfoEvent.WHAT_SONG_INDEX, index));
        EventBus.getDefault().post(new PlayInfoEvent(
                PlayInfoEvent.WHAT_SONG_PLAYING,
                player != null && player.isPlaying()));
        EventBus.getDefault().post(new PlayInfoEvent(
                PlayInfoEvent.WHAT_SONG_POSITION,
                player == null ? 0 : player.getCurrentPosition()));
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // do nothing
        return null;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PlayActionEvent event) {
        if (event == null) {
            return;
        }

        switch (event.what) {
            case PlayActionEvent.WHAT_PLAY:
                if (event.extra != null && event.extra instanceof Integer) {
                    pausePosition = 0;
                    play((Integer) event.extra);
                } else {
                    play();
                }
                break;
            case PlayActionEvent.WHAT_PAUSE:
                pause();
                break;
            case PlayActionEvent.WHAT_PREVIOUS:
                previous();
                break;
            case PlayActionEvent.WHAT_NEXT:
                next();
                break;
            case PlayActionEvent.WHAT_SEEK:
                if (event.extra != null && event.extra instanceof Integer) {
                    seekTo((Integer) event.extra);
                } else {
                    seekTo(0);
                }
                break;
            case PlayActionEvent.WHAT_MODE:
                changeMode();
                break;
            case PlayActionEvent.WHAT_NONE:
            default:
                // do nothing
                break;
        }
    }

    private void initPlayer() {
        player = new MediaPlayer();
        player.setOnPreparedListener(onPreparedListener);
        player.setOnSeekCompleteListener(onSeekCompleteListener);
        player.setOnCompletionListener(onCompletionListener);
        player.setOnErrorListener(onErrorListener);
    }

    private void play() {
        play(index);
    }

    private void play(int index) {
        if (songs != null && songs.size() > index && index >= 0) {
            Song song = songs.get(index);

            if (player == null) {
                initPlayer();
            }
            player.reset();
            try {
                player.setDataSource(song.data);
            } catch (Exception e) {
                e.printStackTrace();
            }
            player.prepareAsync();
            EventBus.getDefault().post(new PlayInfoEvent(PlayInfoEvent.WHAT_SONG_INDEX, index));
        }
    }

    private void pause() {
        if (player != null) {
            pausePosition = player.getCurrentPosition();
            player.stop();
            EventBus.getDefault().post(new PlayInfoEvent(PlayInfoEvent.WHAT_SONG_PLAYING, false));
        }
    }

    private void previous() {
        switch (mode) {
            case MODE_CYCLE:
                index++;
                if (index == songs.size()) {
                    index = 0;
                }
                play(index);
                break;
            case MODE_ORDER:
                index++;
                if (index < songs.size()) {
                    play(index);
                }
                break;
            case MODE_RANDOM:
                Random r = new Random();
                index = r.nextInt(songs.size());
                play(index);
                break;
            case MODE_SINGLE:
                play(index);
                break;
        }
    }


    private void next() {
        index--;
        if (index < 0) {
            index = songs.size() - 1;
        }
        play(index);
    }

    private void seekTo(int position) {
        if (player != null) {
            player.seekTo(position);
        }
    }

    private void changeMode() {
        mode++;
        if (mode > 3) {
            mode = 0;
        }
        EventBus.getDefault().post(new PlayInfoEvent(PlayInfoEvent.WHAT_SONG_MODE, mode));
    }

    private final MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.seekTo(pausePosition);
            pausePosition = 0;
        }
    };

    private final MediaPlayer.OnSeekCompleteListener onSeekCompleteListener = new MediaPlayer.OnSeekCompleteListener() {
        @Override
        public void onSeekComplete(MediaPlayer mp) {
            mp.start();
            EventBus.getDefault().post(new PlayInfoEvent(PlayInfoEvent.WHAT_SONG_PLAYING, true));
        }
    };

    private final MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            previous();
        }
    };

    private final MediaPlayer.OnErrorListener onErrorListener = new MediaPlayer.OnErrorListener() {
        @Override
        public boolean onError(MediaPlayer mp, int what, int extra) {
            switch (what) {
                case MediaPlayer.MEDIA_ERROR_IO:
                    break;
                case MediaPlayer.MEDIA_ERROR_MALFORMED:
                    break;
                case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                    break;
                case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                    player.release();
                    player = null;
                    play();
                    break;
                case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                    play();
                    break;
                case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                    break;
                case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                    break;
            }
            return true;
        }
    };

}
