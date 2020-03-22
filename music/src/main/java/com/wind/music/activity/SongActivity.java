package com.wind.music.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.wind.music.R;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.dialog.MusicListDialog;
import com.wind.music.fragment.MusicControllerFragment;
import com.wind.music.fragment.BaseSongFragment;
import com.wind.music.fragment.SongFragment;
import com.wind.music.service.PlayerService;
import com.wind.music.util.MusicPlayer;
import com.wind.music.util.Toasts;

import java.util.List;

public class SongActivity extends BaseActivity implements MusicControllerFragment.OnMusicListShowListener {
    private static final String EXTRA_TYPE = "Extra.Type";
    private final String TAG = getClass().getSimpleName();

    private MusicPlayer mMusicPlayer;
    private MusicControllerFragment ctrlFragment;
    private SongFragment songFragment;
    private int mType = SongFragment.TYPE_LOCAL;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, SongActivity.class);
        intent.putExtra(EXTRA_TYPE, type);
        context.startActivity(intent);
    }

    /*
     * activity lifecycle start
     **********************************************************************************************/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);

        Intent intent = getIntent();
        mType = intent.getIntExtra(SongActivity.EXTRA_TYPE, SongFragment.TYPE_LOCAL);

        initLayout();
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        finishLayout();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_song, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            default:
                return false;
        }
        return true;
    }

    /*
     * activity lifecycle end
     **********************************************************************************************/

    /*
     * mMusicPlayer start
     **********************************************************************************************/

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
                dialog.setPlayer(mMusicPlayer);
            }
        }
    };

    private void bindPlayer() {
        Intent service = new Intent(this, PlayerService.class);
        bindService(service, playerConnection, BIND_AUTO_CREATE);
    }

    private void unbindPlayer() {
        unbindService(playerConnection);
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

    /*
     * mMusicPlayer end
     **********************************************************************************************/

    private void initLayout() {

        songFragment = SongFragment.of(mType);
        songFragment.setOnSongClickListener(selectSongListener);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(songFragment.getTitle());
            setSupportActionBar(toolbar);
        }

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        ctrlFragment = new MusicControllerFragment();

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_song, songFragment)
                .replace(R.id.fragment_controller, ctrlFragment)
                .commit();
    }

    private void finishLayout() {

        songFragment.setOnSongClickListener(null);
        songFragment = null;
        ctrlFragment = null;
    }

    private final BaseSongFragment.OnSongClickListener selectSongListener =
            new BaseSongFragment.OnSongClickListener() {

                @Override
                public void onSongClick(List<BillBoardBean.Song> songs, int position) {
                    if (mMusicPlayer != null) {
                        mMusicPlayer.setData(songs);
                        mMusicPlayer.play(position);
                    }
                }
            };

}
