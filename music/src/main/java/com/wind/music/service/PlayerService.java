package com.wind.music.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;

import com.google.gson.Gson;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.bean.SongInfoBean;
import com.wind.music.presenter.PlayerPresenter;
import com.wind.music.presenter.PresenterFactory;
import com.wind.music.util.MusicPlayer;
import com.wind.music.util.Network;
import com.wind.music.util.NetworkListener;
import com.wind.music.util.PlayInfo;
import com.wind.music.util.Urls;
import com.wind.music.util.Utils;
import com.wind.music.view.PlayerView;

import java.net.MalformedURLException;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static android.media.CamcorderProfile.get;

/**
 * Created by Administrator on 2017/5/18.
 */

public class PlayerService extends Service implements PlayerView, MusicPlayer {
    private final String TAG = getClass().getSimpleName();

    private PlayInfo mPlayInfo;
    private MediaPlayer mMediaPlayer;
    private int mPausePosition;
    private final HashSet<MusicPlayer.OnPlayInfoListener> mListenerSet = new HashSet<>();
    private Timer mTimer;
    private PlayerPresenter mPresenter;
    private BroadcastReceiver mHeadSetReceiver;


    @Override
    public void onCreate() {
        mPlayInfo = new PlayInfo(this);
        mTimer = new Timer();
        mTimer.schedule(task, 200, 200);
        mPresenter = PresenterFactory.createPlayerPresenter(this);
        initReceiver();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        exitReceiver();
        mPresenter.setView(null);
        mPresenter = null;

        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }

        mPlayInfo = null;

        mTimer.cancel();
        mTimer = null;

        synchronized (mListenerSet) {
            for (MusicPlayer.OnPlayInfoListener l : mListenerSet) {
                l.onPlayInfo(null, 0);
            }
            mListenerSet.clear();
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

    @Override
    public void onSongInfoLoaded(int songId, SongInfoBean songInfo) {
        if (songInfo == null) {
            next();
            return;
        }

        int index = mPlayInfo.getIndex();
        List<BillBoardBean.Song> songs = mPlayInfo.getSongs();
        BillBoardBean.Song song = songs.get(index);
        song.setSonginfo(songInfo.getSonginfo());
        song.setBitrate(songInfo.getBitrate());
        innerPlay(songInfo.getBitrate().getFile_link());
    }

    @Override
    public void onSongCaching(String srcPath, String cachePath, int percent) {

    }

    @Override
    public void onSongCached(String srcPath, String cachePath) {
        String path = cachePath;
        if (cachePath == null) {
            path = srcPath;
        }
        innerPlay(path);
    }

    private void initPlayer() {
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnPreparedListener(onPreparedListener);
        mMediaPlayer.setOnSeekCompleteListener(onSeekCompleteListener);
        mMediaPlayer.setOnCompletionListener(onCompletionListener);
        mMediaPlayer.setOnErrorListener(onErrorListener);
        mMediaPlayer.setOnInfoListener(onInfoListener);
    }


    private void innerPlay(String path) {
        if (Utils.isNetworkPath(path)) {
            mPresenter.cacheSong(path);
            return;
        }

        if (mMediaPlayer == null) {
            initPlayer();
        }
        mMediaPlayer.reset();
        try {
            mMediaPlayer.setDataSource(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        mMediaPlayer.prepareAsync();
    }

    private String getPath(BillBoardBean.Song song) {
        String path = song.getPath();
        if (!song.isIs_local()) {
            if (song.getBitrate() == null) {
                mPresenter.loadSongInfo(song.getSong_id());
            } else {
                path = song.getBitrate().getFile_link();
            }
        }
        return path;
    }

    @Override
    public void play() {
        int index = mPlayInfo.getIndex();
        List<BillBoardBean.Song> songs = mPlayInfo.getSongs();
        if (songs.size() > index && index >= 0) {
            BillBoardBean.Song song = songs.get(index);

            String path = getPath(song);
            if (TextUtils.isEmpty(path)) {
                return;
            }

            innerPlay(path);
        }
    }

    @Override
    public void play(int index) {
        int oldIndex = mPlayInfo.getIndex();

        List<BillBoardBean.Song> songs = mPlayInfo.getSongs();
        if (songs.size() > oldIndex && oldIndex >= 0) {
            BillBoardBean.Song song = songs.get(oldIndex);
            if (!song.isIs_local()) {
                mPresenter.cancelSongInfo(song.getSong_id());
                String path = getPath(song);
                if (!TextUtils.isEmpty(path)) {
                    mPresenter.cancelSong(path);
                }
            }
        }

        pause();
        mPlayInfo.setIndex(index);
        mPausePosition = 0;
        play();
    }

    @Override
    public void pause() {
        if (mMediaPlayer != null) {
            mPausePosition = mMediaPlayer.getCurrentPosition() - 500;
            if (mPausePosition < 0) {
                mPausePosition = 0;
            }
            mMediaPlayer.stop();
        }
    }

    @Override
    public void previous() {
        int index = mPlayInfo.getIndex();
        index--;
        if (index < 0) {
            index = mPlayInfo.getSongs().size() - 1;
        }
        play(index);
    }

    @Override
    public void next() {
        int index = mPlayInfo.getIndex();
        switch (mPlayInfo.getMode()) {
            case MusicPlayer.MODE_CYCLE:
                index++;
                if (index == mPlayInfo.getSongs().size()) {
                    index = 0;
                }
                play(index);
                break;
            case MusicPlayer.MODE_SINGLE:
                play(index);
                break;
            case MusicPlayer.MODE_RANDOM:
                Random r = new Random();
                index = r.nextInt(mPlayInfo.getSongs().size());
                play(index);
                break;
        }
    }

    @Override
    public void seekTo(int position) {
        if (mMediaPlayer != null) {
            mMediaPlayer.seekTo(position);
        }
    }

    @Override
    public void setData(List<BillBoardBean.Song> data) {
        List<BillBoardBean.Song> songs = mPlayInfo.getSongs();
        songs.clear();
        if (data != null) {
            songs.addAll(data);
        }
        mPlayInfo.setSongs(songs);
    }

    @Override
    public List<BillBoardBean.Song> getData() {
        return mPlayInfo.getSongs();
    }

    @Override
    public int insert(int index, BillBoardBean.Song song) {
        List<BillBoardBean.Song> songs = mPlayInfo.getSongs();
        if (index < songs.size()) {
            songs.add(index, song);
            int tmpIndex = mPlayInfo.getIndex();
            if (tmpIndex >= index) {
                mPlayInfo.setIndex(tmpIndex + 1);
            }
        } else {
            songs.add(song);
        }
        mPlayInfo.setSongs(songs);
        return 0;
    }

    @Override
    public BillBoardBean.Song remove(int index) {
        List<BillBoardBean.Song> songs = mPlayInfo.getSongs();
        int tmpIndex = mPlayInfo.getIndex();
        if (index < songs.size()) {
            if (tmpIndex > index) {
                tmpIndex--;
            } else if (tmpIndex == index) {
                pause();
                tmpIndex = 0;
                mPausePosition = 0;
            }
            mPlayInfo.setIndex(tmpIndex);
            BillBoardBean.Song song = songs.remove(index);
            if (song != null) {
                mPlayInfo.setSongs(songs);
            }
            return song;
        }
        return null;
    }

    @Override
    public boolean isPlaying() {
        return mMediaPlayer != null && mMediaPlayer.isPlaying();
    }

    @Override
    public int whatIsPlaying() {
        return mPlayInfo.getIndex();
    }

    @Override
    public int getCurrentPosition() {
        return mMediaPlayer == null ? 0 : mMediaPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mMediaPlayer == null ? 0 : mMediaPlayer.getDuration();
    }

    @Override
    public int changePlayMode() {
        int mode = mPlayInfo.getMode();
        mode++;
        if (mode >= MusicPlayer.MODE_MAX) {
            mode = MusicPlayer.MODE_CYCLE;
        }
        mPlayInfo.setMode(mode);
        return mode;
    }

    @Override
    public int getPlayMode() {
        return mPlayInfo.getMode();
    }

    @Override
    public void addOnPlayInfoListener(OnPlayInfoListener listener) {
        if (listener != null) {
            synchronized (mListenerSet) {
                mListenerSet.add(listener);
                mListenerSet.notifyAll();
            }
        }
    }

    @Override
    public void removeOnPlayInfoListener(OnPlayInfoListener listener) {
        if (listener != null) {
            synchronized (mListenerSet) {
                mListenerSet.remove(listener);
            }
        }
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_BUTTON);
        filter.addAction(Intent.ACTION_HEADSET_PLUG);
        filter.addAction(BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
        mHeadSetReceiver = new HeadSetReceiver();
        registerReceiver(mHeadSetReceiver, filter);
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.registerMediaButtonEventReceiver();
    }

    private void exitReceiver() {
        unregisterReceiver(mHeadSetReceiver);
        mHeadSetReceiver = null;
    }

    private class HeadSetReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction())) {
                KeyEvent event = intent.getParcelableExtra(Intent.EXTRA_KEY_EVENT);
                int keyCode = event.getKeyCode();
                Log.d(TAG, "onReceive: media button keyCode=" + keyCode);
                if (keyCode == KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE) {
                    if (isPlaying()) {
                        pause();
                    } else {
                        play();
                    }
                }
            } else if (Intent.ACTION_HEADSET_PLUG.equals(intent.getAction())) {
                int state = intent.getIntExtra("state", -1);
                Log.d(TAG, "onReceive: headset plug state=" + state);
                if (state == 0) {
                    pause();
                }
            } else if (BluetoothHeadset.ACTION_CONNECTION_STATE_CHANGED.equals(intent.getAction())) {
                int state = intent.getIntExtra(BluetoothHeadset.EXTRA_STATE, -1);
                Log.d(TAG, "onReceive: bluetooth headset state changed state=" + state);
                if (BluetoothHeadset.STATE_DISCONNECTED == state || BluetoothHeadset.STATE_DISCONNECTING == state) {
                    pause();
                }
            } else if (BluetoothAdapter.ACTION_STATE_CHANGED.equals(intent.getAction())) {
                int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, -1);
                Log.d(TAG, "onReceive: bluetooth state changed state=" + state);
                if (BluetoothAdapter.STATE_OFF == state || BluetoothAdapter.STATE_TURNING_OFF == state) {
                    pause();
                }
            }
        }
    }

    private final MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.seekTo(mPausePosition);
            mPausePosition = 0;
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
                    mMediaPlayer.release();
                    mMediaPlayer = null;
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
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            int mIndex = mPlayInfo.getIndex();
            List<BillBoardBean.Song> mSongs = mPlayInfo.getSongs();
            BillBoardBean.Song song = mIndex < mSongs.size() ? mSongs.get(mIndex) : null;
            int position = mMediaPlayer != null ? mMediaPlayer.getCurrentPosition() : 0;
            synchronized (mListenerSet) {
                for (MusicPlayer.OnPlayInfoListener l : mListenerSet) {
                    l.onPlayInfo(song, position);
                }
            }
        }
    };

    private final TimerTask task = new TimerTask() {
        @Override
        public void run() {
            synchronized (mListenerSet) {
                if (!mListenerSet.isEmpty()) {
                    handler.obtainMessage(1).sendToTarget();
                }
            }
        }
    };


    private class PlayerBinder extends Binder implements MusicPlayer {

        @Override
        public void setData(List<BillBoardBean.Song> data) {
            PlayerService.this.setData(data);
        }

        @Override
        public List<BillBoardBean.Song> getData() {
            return PlayerService.this.getData();
        }

        @Override
        public int insert(int index, BillBoardBean.Song song) {
            return PlayerService.this.insert(index, song);
        }

        @Override
        public BillBoardBean.Song remove(int index) {
            return PlayerService.this.remove(index);
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
            return PlayerService.this.isPlaying();
        }

        @Override
        public int whatIsPlaying() {
            return PlayerService.this.whatIsPlaying();
        }

        @Override
        public int getCurrentPosition() {
            return PlayerService.this.getCurrentPosition();
        }

        @Override
        public int getDuration() {
            return PlayerService.this.getDuration();
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
            return PlayerService.this.changePlayMode();
        }

        @Override
        public int getPlayMode() {
            return PlayerService.this.getPlayMode();
        }

        @Override
        public void addOnPlayInfoListener(OnPlayInfoListener listener) {
            PlayerService.this.addOnPlayInfoListener(listener);
        }

        @Override
        public void removeOnPlayInfoListener(OnPlayInfoListener listener) {
            PlayerService.this.removeOnPlayInfoListener(listener);
        }
    }
}
