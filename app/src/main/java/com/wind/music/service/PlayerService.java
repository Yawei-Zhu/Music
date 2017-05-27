package com.wind.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.wind.music.bean.Song;
import com.wind.music.util.MusicPlayer;
import com.wind.music.util.PlayInfoSaver;

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
    private PlayInfoSaver saver;
    private MediaPlayer player;
    private int index;
    private int pausePosition;
    private int mode;

    @Override
    public void onCreate() {

        saver = new PlayInfoSaver(this);
        mode = saver.readMode();
        index = saver.readIndex();
        pausePosition = saver.readPosition();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {

        if (player != null) {
            if (player.isPlaying()) {
                pausePosition = player.getCurrentPosition();
                player.stop();
            }
            player.release();
            player = null;
        }

        if (saver != null) {
            saver.saveMode(mode);
            saver.saveIndex(index);
            saver.savePosition(pausePosition);
            saver.release();
            saver = null;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        IBinder binder = new PlayerBinder();
        return binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private class PlayerBinder extends Binder implements MusicPlayer {

        @Override
        public void setData(List<Song> data) {
            songs = data;
        }

        @Override
        public void play() {
            PlayerService.this.play();
        }

        @Override
        public void play(int index) {
            PlayerService.this.play(index);
        }

        @Override
        public void pause() {
            PlayerService.this.pause();
        }

        @Override
        public boolean isPlaying() {
            return player != null && player.isPlaying();
        }

        @Override
        public int whatIsPlaying() {
            return index;
        }

        @Override
        public int currentPosition() {
            return  player == null ? 0 : player.getCurrentPosition();
        }

        @Override
        public void seekTo(int position) {
            PlayerService.this.seekTo(position);
        }

        @Override
        public void previous() {
            PlayerService.this.previous();
        }

        @Override
        public void next() {
            PlayerService.this.next();
        }

        @Override
        public int changePlayMode() {
            return PlayerService.this.changeMode();
        }

        @Override
        public int getPlayMode() {
            return mode;
        }

        @Override
        public void registerPlayInfoListener(PlayInfoListener listener) {

        }

        @Override
        public void unregisterPlayInfoListener(PlayInfoListener listener) {

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
        }
    }

    private void play(int index) {
        this.index = index;
        play();
    }

    private void pause() {
        if (player != null) {
            pausePosition = player.getCurrentPosition() - 500;
            if (pausePosition < 0)  {
                pausePosition = 0;
            }
            player.stop();
        }
    }

    private void previous() {
        index--;
        if (index < 0) {
            index = songs.size() - 1;
        }
        play(index);
    }


    private void next() {
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

    private void seekTo(int position) {
        if (player != null) {
            player.seekTo(position);
        }
    }

    private int changeMode() {
        mode++;
        if (mode > 3) {
            mode = 0;
        }
        return mode;
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
        }
    };

    private final MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            next();
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
