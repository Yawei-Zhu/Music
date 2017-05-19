package com.wind.music.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.wind.music.Application;
import com.wind.music.R;
import com.wind.music.adapter.SongAdapter;
import com.wind.music.bean.Song;
import com.wind.music.decoration.DefaultDecoration;
import com.wind.music.event.PlayActionEvent;
import com.wind.music.event.PlayInfoEvent;
import com.wind.music.service.PlayerService;
import com.wind.music.util.LoadLocal;
import com.wind.music.util.LoadLocalListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

public class LocalActivity extends BaseActivity {
    private static final String TAG = LocalActivity.class.getSimpleName();


    private RecyclerView recyclerView;
    private TextView tvPlay;
    private TextView tvMode;
    private TextView tvTitle;

    private List<Song> songs;
    private SongAdapter adapter;
    private Intent service;
    private boolean isPlaying = false;

    /*
     * activity lifecycle start
     **********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);

        EventBus.getDefault().register(this);
        initLayout();
        loadData();
        service = new Intent(this, PlayerService.class);
        startService(service);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    /*
     * activity lifecycle end
     **********************************************************************************************/


    /*
     * eventbus callback start
     **********************************************************************************************/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PlayInfoEvent event) {
        if (event == null) {
            return;
        }

        switch (event.what) {
            case PlayInfoEvent.WHAT_SONG_INDEX:
                if (event.extra != null && event.extra instanceof Integer) {
                    updateSongInfo((Integer) event.extra);
                }
                break;
            case PlayInfoEvent.WHAT_SONG_PLAYING:
                if (event.extra != null && event.extra instanceof Boolean) {
                    updatePlaying((Boolean) event.extra);
                }
                break;
            case PlayInfoEvent.WHAT_SONG_MODE:
                if (event.extra != null && event.extra instanceof Integer) {
                    updatePlayMode((Integer) event.extra);
                }
                break;
            case PlayInfoEvent.WHAT_SONG_POSITION:
            case PlayInfoEvent.WHAT_SONG_NONE:
            default:
                // do nothing
                break;
        }
    }

    private void updatePlayMode(Integer mode) {
        String s = "";
        switch (mode) {
            case PlayerService.MODE_CYCLE:
                s = "循环";
                break;
            case PlayerService.MODE_ORDER:
                s = "顺序";
                break;
            case PlayerService.MODE_RANDOM:
                s = "随机";
                break;
            case PlayerService.MODE_SINGLE:
                s = "单曲";
                break;
        }

        if (tvMode != null) {
            tvMode.setText(s);
        }
    }

    private void updateSongInfo(int index) {
        if (songs.size() > index && index >= 0) {
            Song song = songs.get(index);
            if (tvTitle != null) {
                tvTitle.setText(song.title);
            }
        }
    }

    private void updatePlaying(boolean playing) {
        isPlaying = playing;
        if (tvPlay != null) {
            tvPlay.setText(playing ? "暂停" : "播放");
        }
    }


    /*
     * eventbus callback start
     **********************************************************************************************/

    private void initLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        tvMode = (TextView) findViewById(R.id.tv_mode);
        if (tvMode != null) {
            tvMode.setOnClickListener(switchPlayModeListener);
        }

        tvTitle = (TextView) findViewById(R.id.tv_title);

        tvPlay = (TextView) findViewById(R.id.tv_play);
        if (tvPlay != null) {
            tvPlay.setOnClickListener(pauseListener);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        songs = Application.getApp().getLocalSongs();
        adapter = new SongAdapter(LocalActivity.this, songs);
        adapter.setOnItemClickListener(selectSongListener);

        if (recyclerView != null) {
            LinearLayoutManager lm = new LinearLayoutManager(LocalActivity.this);
            lm.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(lm);
            recyclerView.addItemDecoration(new DefaultDecoration());
            recyclerView.setAdapter(adapter);
        }
    }

    private void loadData() {
        LoadLocal.loadSongs(new LoadLocalListener<List<Song>>() {
            @Override
            public void onRespond(List<Song> data) {
                songs.clear();
                songs.addAll(data);
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private final View.OnClickListener switchPlayModeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            EventBus.getDefault().post(new PlayActionEvent(PlayActionEvent.WHAT_MODE));
        }
    };

    private final View.OnClickListener pauseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            PlayActionEvent event = new PlayActionEvent();
            if (isPlaying) {
                event.what = PlayActionEvent.WHAT_PAUSE;
            } else {
                event.what = PlayActionEvent.WHAT_PLAY;
            }
            EventBus.getDefault().post(event);
        }
    };

    private final SongAdapter.OnItemClickListener selectSongListener = new SongAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Song item, int position) {
            EventBus.getDefault().post(new PlayActionEvent(PlayActionEvent.WHAT_PLAY, position));
        }
    };
}
