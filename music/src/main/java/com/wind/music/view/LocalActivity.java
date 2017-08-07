package com.wind.music.view;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.support.v4.content.PermissionChecker;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wind.music.R;
import com.wind.music.adapter.SongRecyclerAdapter;
import com.wind.music.bean.Song;
import com.wind.music.decoration.DefaultDecoration;
import com.wind.music.service.PlayerService;
import com.wind.music.util.LoadLocal;
import com.wind.music.util.LoadLocalListener;
import com.wind.music.util.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

public class LocalActivity extends BaseActivity {
    private final String TAG = getClass().getSimpleName();

    private SwipeRefreshLayout srLayout;
    private TextView tvCurrent;
    private TextView tvDuration;
    private SeekBar sbProgress;
    private Button btPlay;
    private Button btMode;
    private TextView tvTitle;

    private List<Song> songs;
    private SongRecyclerAdapter adapter;
    private MusicPlayer player;
    private boolean isProgressTrackingTouch = false;

    /*
     * activity lifecycle start
     **********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);

        initLayout();
        loadData();
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
                player.registerOnPlayInfoListener(onPlayInfoListener);
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

        sbProgress = (SeekBar) findViewById(R.id.sb_progress);
        if (sbProgress != null) {
            sbProgress.setOnSeekBarChangeListener(onSeekBarChangeListener);
        }

        tvCurrent = (TextView) findViewById(R.id.tv_current);
        tvDuration = (TextView) findViewById(R.id.tv_duration);
        Drawable d;

        btMode = (Button) findViewById(R.id.bt_mode);
        if (btMode != null) {
            btMode.setOnClickListener(switchPlayModeListener);
        }

        tvTitle = (TextView) findViewById(R.id.tv_title);

        btPlay = (Button) findViewById(R.id.bt_play);
        if (btPlay != null) {
            btPlay.setOnClickListener(pauseListener);
        }

        srLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        if (srLayout != null) {
            srLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    loadData();
                }
            });
        }

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        songs = new ArrayList<>();
        adapter = new SongRecyclerAdapter(LocalActivity.this, songs);
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            if (checkSelfPermission(permission) == PermissionChecker.PERMISSION_GRANTED) {
                _LoadData();
            } else {
                requestPermissions(new String[]{permission}, 1);
            }
        } else {
            _LoadData();
        }
    }

    private void _LoadData() {
        LoadLocal.loadSongs(new LoadLocalListener<List<Song>>() {
            @Override
            public void onRespond(List<Song> data) {
                if (songs != null) {
                    songs.clear();
                    songs.addAll(data);
                    if (adapter != null) {
                        adapter.notifyDataSetChanged();
                    }
                }
                if (srLayout != null) {
                    srLayout.setRefreshing(false);
                }
            }
        });
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

    private final SongRecyclerAdapter.OnItemClickListener selectSongListener =
            new SongRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(Song item, int position) {
                    if (player != null) {
                        if (player.isPlaying()) {
                            if (player.whatIsPlaying() == position) {
                                player.pause();
                                updatePlaying(false);
                            } else {
                                player.play(position);
                            }
                        } else {
                            if (player.whatIsPlaying() == position) {
                                player.play();
                            } else {
                                player.play(position);
                            }
                            updatePlaying(true);
                        }
                    }
                }
            };

    private final MusicPlayer.OnPlayInfoListener onPlayInfoListener = new MusicPlayer.OnPlayInfoListener() {
        int oldIndex = -1;

        @Override
        public void onPlayInfo(int index, int position) {
            if (oldIndex != index) {
                updateIndex(oldIndex = index);
            }
            updateDuration(player == null ? 0 : player.getDuration());
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
            if (player != null) {
                player.seekTo(seekBar.getProgress());
            }
        }
    };

    private void updatePlaying(boolean isPlaying) {
        if (btPlay != null) {
            if (isPlaying) {
                btPlay.setText(R.string.musicplayer_pause);
            } else {
                btPlay.setText(R.string.musicplayer_play);
            }
        }
    }

    private void updateMode(int mode) {
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

    private void updateIndex(int index) {
        if (index >= 0 && songs != null && songs.size() > index) {
            if (tvTitle != null) {
                tvTitle.setText(songs.get(index).title);
            }
        }
    }

    private String time2String(int time) {
        String s;
        int totalSecond = time / 1000;
        int second = totalSecond % 60;
        int minute = totalSecond / 60 % 60;
        int hour = totalSecond / 3600;
        if (hour == 0) {
            s = String.format("%02d:%02d", minute, second);
        } else {
            s = String.format("%d:%02d:%02d", hour, minute, second);
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
}
