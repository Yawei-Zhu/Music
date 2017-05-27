package com.wind.music.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.wind.music.Application;
import com.wind.music.R;
import com.wind.music.adapter.SongAdapter;
import com.wind.music.bean.Song;
import com.wind.music.decoration.DefaultDecoration;
import com.wind.music.event.NoticeEvent;
import com.wind.music.event.PlayActionEvent;
import com.wind.music.event.PlayInfoEvent;
import com.wind.music.service.PlayerService;
import com.wind.music.util.MusicPlayer;

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
    private boolean isPlaying = false;
    private MusicPlayer player;

    /*
     * activity lifecycle start
     **********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);

        EventBus.getDefault().register(this);
        initLayout();
    }

    @Override
    protected void onStart() {
        super.onStart();
        bindPlayer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindPlayer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_local, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_close:
                finish();
                Process.killProcess(Process.myPid());
                break;
        }
        return true;
    }

    /*
     * activity lifecycle end
     **********************************************************************************************/


    /*
     * eventbus callback start
     **********************************************************************************************/

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NoticeEvent event) {
        if (event == null) {
            return;
        }

        switch (event.what) {
            case NoticeEvent.WHAT_DATA_LOADED:
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
                break;
        }
    }

    /*
     * eventbus callback end
     **********************************************************************************************/

    /*
     * player start
     **********************************************************************************************/

    private PlayerConnection playerConnection;

    private void bindPlayer() {
        Intent service = new Intent(this, PlayerService.class);
        playerConnection = new PlayerConnection();
        int flags = BIND_AUTO_CREATE;
        bindService(service, playerConnection, flags);
    }

    private void unbindPlayer() {
        if (playerConnection != null) {
            unbindService(playerConnection);
        }
        player = null;
        playerConnection = null;
    }

    private class PlayerConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof MusicPlayer) {
                player = (MusicPlayer) service;
                player.setData(songs);
                updatePlaying(player.isPlaying());
                updateMode(player.getPlayMode());
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }
    /*
     * player end
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

    private final View.OnClickListener switchPlayModeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (player != null) {
                int mode = player.changePlayMode();
                updateMode(mode);
            }
        }
    };

    private final View.OnClickListener pauseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (player != null) {
                if (player.isPlaying()) {
                    player.pause();
                    updatePlaying(false);
                } else {
                    player.play();
                    updatePlaying(true);
                }
            }
        }
    };

    private final SongAdapter.OnItemClickListener selectSongListener = new SongAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Song item, int position) {
            if (player != null) {
                player.play(position);
                updatePlaying(true);
            }
        }
    };

    private void updatePlaying(boolean isPlaying) {
        if (tvPlay != null) {
            if (isPlaying) {
                tvPlay.setText("暂停");
            } else {
                tvPlay.setText("播放");
            }
        }
    }

    private void updateMode(int mode) {
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
}
