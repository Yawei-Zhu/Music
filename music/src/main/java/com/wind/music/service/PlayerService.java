package com.wind.music.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.bean.SongInfoBean;
import com.wind.music.util.MusicPlayer;
import com.wind.music.util.Network;
import com.wind.music.util.NetworkListener;
import com.wind.music.util.PlayInfoSaver;
import com.wind.music.util.Urls;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/5/18.
 */

public class PlayerService extends Service {
    public static final int MODE_CYCLE = 0;
    public static final int MODE_SINGLE = 1;
    public static final int MODE_RANDOM = 2;
    public static final int MODE_MAX = 3;

    private List<BillBoardBean.Song> songs;
    private PlayInfoSaver saver;
    private MediaPlayer player;
    private int index = -1;
    private int pausePosition;
    private int mode;
    private final HashSet<MusicPlayer.OnPlayInfoListener> listenerSet = new HashSet<>();

    @Override
    public void onCreate() {

        saver = new PlayInfoSaver(this);
        mode = saver.readMode();
        index = saver.readIndex();
        pausePosition = saver.readPosition();
        Timer mTimer = new Timer();
        mTimer.schedule(task, 1000, 1000);
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
        return new PlayerBinder();
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    private class PlayerBinder extends Binder implements MusicPlayer {

        @Override
        public void setData(List<BillBoardBean.Song> data) {
            songs = data;
        }

        @Override
        public List<BillBoardBean.Song> getData() {
            return songs;
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
        public int getCurrentPosition() {
            int position = player == null ? 0 : player.getCurrentPosition();
            return position < 0 ? 0 : position;
        }

        @Override
        public int getDuration() {
            int duration = player == null ? 0 : player.getDuration();
            return duration < 0 ? 0 : duration;
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
        public void registerOnPlayInfoListener(OnPlayInfoListener listener) {
            if (listener != null) {
                synchronized (listenerSet) {
                    listenerSet.add(listener);
                    listenerSet.notifyAll();
                }
            }
        }

        @Override
        public void unregisterOnPlayInfoListener(OnPlayInfoListener listener) {
            if (listener != null) {
                synchronized (listenerSet) {
                    listenerSet.remove(listener);
                }
            }
        }
    }

    private void initPlayer() {
        player = new MediaPlayer();
        player.setOnPreparedListener(onPreparedListener);
        player.setOnSeekCompleteListener(onSeekCompleteListener);
        player.setOnCompletionListener(onCompletionListener);
        player.setOnErrorListener(onErrorListener);
        player.setOnInfoListener(onInfoListener);
    }

    private void play() {
        if (songs != null && songs.size() > index && index >= 0) {
            BillBoardBean.Song song = songs.get(index);

            String path = song.getPath();
            if (TextUtils.isEmpty(path)) {
                if (song.getBitrate() == null) {
                    loadSongInfo(song);
                    return;
                } else {
                    path = song.getBitrate().getFile_link();
                    if (TextUtils.isEmpty(path)) {
                        next();
                        return;
                    }
                }
            }

            if (player == null) {
                initPlayer();
            }
            player.reset();
            try {
                player.setDataSource(path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            player.prepareAsync();
        }
    }

    private void play(int index) {
        this.index = index;
        if (saver != null) {
            saver.saveIndex(index);
        }
        pausePosition = 0;
        play();
    }

    private void pause() {
        if (player != null) {
            pausePosition = player.getCurrentPosition() - 500;
            if (pausePosition < 0) {
                pausePosition = 0;
            }
            player.stop();
            if (saver != null) {
                saver.savePosition(pausePosition);
            }
        }
    }

    private void previous() {
        int index = this.index;
        index--;
        if (index < 0) {
            index = songs.size() - 1;
        }
        play(index);
    }


    private void next() {
        int index = this.index;
        switch (mode) {
            case MODE_CYCLE:
                index++;
                if (index == songs.size()) {
                    index = 0;
                }
                play(index);
                break;
            case MODE_SINGLE:
                play(index);
                break;
            case MODE_RANDOM:
                Random r = new Random();
                index = r.nextInt(songs.size());
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
        if (mode >= MODE_MAX) {
            mode = MODE_CYCLE;
        }
        if (saver != null) {
            saver.saveMode(mode);
        }
        return mode;
    }

    private void loadSongInfo(final BillBoardBean.Song song) {
        Network.load(Urls.getSongInfoUrl(song.getSong_id()), new NetworkListener() {
            @Override
            public void onRespond(int code, String msg, String response) {
                if (code == Network.CODE_SUCCESS) {
                    Gson gson = new Gson();
                    SongInfoBean bean = gson.fromJson(response, SongInfoBean.class);
                    song.setSonginfo(bean.getSonginfo());
                    song.setBitrate(bean.getBitrate());
                    play();
                }
            }
        });
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

    private final MediaPlayer.OnInfoListener onInfoListener = new MediaPlayer.OnInfoListener() {
        @Override
        public boolean onInfo(MediaPlayer mp, int what, int extra) {
            return true;
        }
    };


    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            synchronized (listenerSet) {
                BillBoardBean.Song song = songs.get(msg.arg1);
                for (MusicPlayer.OnPlayInfoListener l : listenerSet) {
                    l.onPlayInfo(song, msg.arg2);
                }
            }
        }
    };

    TimerTask task = new TimerTask() {
        @Override
        public void run() {
            synchronized (listenerSet) {
                if (!listenerSet.isEmpty()) {
                    if (player != null) {
                        handler.obtainMessage(1, index, player.getCurrentPosition()).sendToTarget();
                    }
                }
            }
        }
    };
}
