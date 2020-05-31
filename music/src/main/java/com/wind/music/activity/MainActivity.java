package com.wind.music.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.PermissionChecker;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.wind.music.R;
import com.wind.music.dialog.MusicListDialog;
import com.wind.music.fragment.MusicControllerFragment;
import com.wind.music.fragment.SongFragment;
import com.wind.music.service.PlayerService;
import com.wind.music.util.MusicPlayer;
import com.wind.music.util.Toasts;

public class MainActivity extends BaseActivity implements MusicControllerFragment.OnMusicListShowListener {
    private final String TAG = MainActivity.class.getSimpleName();
    MusicPlayer mMusicPlayer;
    MusicControllerFragment ctrlFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        unbindPlayer();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        finishLayout();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        /* go to android home */
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_HOME);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_setting:
                //startActivity(new Intent(this, SettingActivity.class));
                break;
            case R.id.action_search:
                SearchActivity.start(this);
                break;
            default:
                return false;
        }
        return true;
    }

    private void bindPlayer() {
        Intent service = new Intent(this, PlayerService.class);
        bindService(service, playerConnection, BIND_AUTO_CREATE);
    }

    private void unbindPlayer() {
        unbindService(playerConnection);
    }

    private ServiceConnection playerConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            if (service instanceof MusicPlayer) {
                mMusicPlayer = (MusicPlayer) service;
                if (null != ctrlFragment) {
                    ctrlFragment.setPlayer(mMusicPlayer);
                }
                MusicListDialog dialog = (MusicListDialog) getSupportFragmentManager().findFragmentByTag(MusicListDialog.TAG);
                if (dialog != null) {
                    dialog.setPlayer(mMusicPlayer);
                }
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mMusicPlayer = null;
            if (null != ctrlFragment) {
                ctrlFragment.setPlayer(null);
            }
            MusicListDialog dialog = (MusicListDialog) getSupportFragmentManager().findFragmentByTag(MusicListDialog.TAG);
            if (dialog != null) {
                dialog.setPlayer(null);
            }
        }
    };

    private void initLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        AppCompatButton button;
        button = (AppCompatButton) findViewById(R.id.button_local);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SongActivity.start(MainActivity.this, SongFragment.TYPE_LOCAL);
            }
        });

        button = (AppCompatButton) findViewById(R.id.button_new);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SongActivity.start(MainActivity.this, SongFragment.TYPE_NEW);
            }
        });

        button = (AppCompatButton) findViewById(R.id.button_hot);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SongActivity.start(MainActivity.this, SongFragment.TYPE_HOT);
            }
        });

        FragmentManager fm = getSupportFragmentManager();
        ctrlFragment = new MusicControllerFragment();
        fm.beginTransaction().replace(R.id.fragment_controller, ctrlFragment).commit();
    }

    private void finishLayout() {
        setSupportActionBar(null);

        AppCompatButton button;
        button = (AppCompatButton) findViewById(R.id.button_local);
        button.setOnClickListener(null);

        button = (AppCompatButton) findViewById(R.id.button_new);
        button.setOnClickListener(null);

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction().remove(ctrlFragment).commit();
        ctrlFragment = null;
    }

    private void checkAndRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
            if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{permission}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int result : grantResults) {
                if (result != PermissionChecker.PERMISSION_GRANTED) {
                    finish();
                    return;
                }
            }
        }
    }

    @Override
    public void onMusicListShow(MusicControllerFragment musicController) {
        if (mMusicPlayer != null && mMusicPlayer.getData() != null) {
            MusicListDialog listDialog = new MusicListDialog();
            listDialog.show(getSupportFragmentManager(), MusicListDialog.TAG);
            listDialog.setPlayer(mMusicPlayer);
        } else {
            Toasts.show(this, "No data!");
        }
    }
}
