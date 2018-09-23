package com.wind.music.view;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Process;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.PermissionChecker;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.wind.music.Application;
import com.wind.music.R;
import com.wind.music.adapter.SongRecyclerAdapter;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.fragment.LocalSongFragment;
import com.wind.music.fragment.MusicControllerFragment;
import com.wind.music.service.PlayerService;
import com.wind.music.util.MusicPlayer;

import java.util.Locale;

public class LocalActivity extends BaseActivity {
    private final String TAG = getClass().getSimpleName();

    private SwipeRefreshLayout srLayout;

    private MusicPlayer player;
    private MusicControllerFragment ctrlFragment;
    private LocalSongFragment songFragment;

    /*
     * activity lifecycle start
     **********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);

        initLayout();
        checkAndRequestPermission();
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
            case R.id.action_into_billboard:
                startActivity(new Intent(this, BillBoardActivity.class));
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
                if (ctrlFragment != null) {
                    ctrlFragment.setPlayer(player);
                }
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

        FragmentManager fm = getSupportFragmentManager();
        songFragment = (LocalSongFragment) fm.findFragmentById(R.id.song_fragment);
        if (songFragment != null) {
            songFragment.setOnItemClickListener(selectSongListener);
        }

        ctrlFragment = (MusicControllerFragment) fm.findFragmentById(R.id.ctrl_fragment);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    private void checkAndRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            if (checkSelfPermission(permission) == PermissionChecker.PERMISSION_GRANTED) {
                songFragment.setPermission(true);
            } else {
                requestPermissions(new String[]{permission}, 1);
            }
        } else {
            songFragment.setPermission(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int result : grantResults) {
                if (result != PermissionChecker.PERMISSION_GRANTED) {
                    return;
                }
            }
            songFragment.setPermission(true);
        }
    }

    private final SongRecyclerAdapter.OnItemClickListener selectSongListener =
            new SongRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BillBoardBean.Song item, int position) {
                    if (player != null) {
                        if (0 == Application.getApp().getCurrSongType()) {
                            if (player.isPlaying()) {
                                if (player.whatIsPlaying() == position) {
                                    player.pause();
                                    if (ctrlFragment != null) {
                                        ctrlFragment.updatePlaying(false);
                                    }
                                } else {
                                    player.play(position);
                                }
                            } else {
                                if (player.whatIsPlaying() == position) {
                                    player.play();
                                } else {
                                    player.play(position);
                                }
                                if (ctrlFragment != null) {
                                    ctrlFragment.updatePlaying(true);
                                }
                            }
                        } else {
                            player.setData(songFragment.getSongs());
                            player.play(position);
                            Application.getApp().setCurrSongType(0);
                            if (ctrlFragment != null) {
                                ctrlFragment.updatePlaying(true);
                            }
                        }
                    }
                }
            };

}
