package com.wind.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;

import com.wind.music.bean.BillBoardBean;
import com.wind.music.util.MusicPlayer;
import com.wind.music.util.PlayInfoSaver;

import java.util.HashSet;
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

            if (player == null) {
                initPlayer();
            }
            player.reset();
            try {
                player.setDataSource(song.getPath());
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
            synchronized (listenerSet) {
                updatePlayInfoThread.run = false;
                listenerSet.notifyAll();
                updatePlayInfoThread = null;
            }
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
        if (saver != null) {
            saver.saveMode(mode);
        }
        return mode;
    }

    private final MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.seekTo(pausePosition);
            pausePosition = 0;

            if (updatePlayInfoThread == null) {
                updatePlayInfoThread = new UpdatePlayInfoThread();
                updatePlayInfoThread.start();
            }
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


    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            synchronized (listenerSet) {
                for (MusicPlayer.OnPlayInfoListener l : listenerSet) {
                    l.onPlayInfo(msg.arg1, msg.arg2);
                }
            }
        }
    };
    private UpdatePlayInfoThread updatePlayInfoThread;

    private class UpdatePlayInfoThread extends Thread {
        public volatile boolean run = true;

        @Override
        public void run() {
            while (run) {
                synchronized (listenerSet) {
                    if (listenerSet.isEmpty()) {
                        try {
                            listenerSet.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        if (player != null) {
                            handler.obtainMessage(1, index, player.getCurrentPosition()).sendToTarget();
                        } else {
                            run = false;
                        }
                    }
                }

                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
