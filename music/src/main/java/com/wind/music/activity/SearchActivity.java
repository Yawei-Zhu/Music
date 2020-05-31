package com.wind.music.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.wind.music.R;
import com.wind.music.bean.Song;
import com.wind.music.dialog.MusicListDialog;
import com.wind.music.fragment.BaseSongFragment;
import com.wind.music.fragment.MusicControllerFragment;
import com.wind.music.fragment.SearchSongFragment;
import com.wind.music.service.PlayerService;
import com.wind.music.util.MusicPlayer;
import com.wind.music.util.Toasts;

import java.util.List;

public class SearchActivity extends BaseActivity implements MusicControllerFragment.OnMusicListShowListener {
    private final String TAG = getClass().getSimpleName();

    private MusicPlayer mMusicPlayer;
    private MusicControllerFragment ctrlFragment;
    private SearchSongFragment songFragment;

    public static void start(Context context) {
        context.startActivity(new Intent(context, SearchActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
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
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();
        searchView.setOnQueryTextListener(mOnQueryTextListener);
        searchView.setOnCloseListener(mOnCloseListener);

        return super.onCreateOptionsMenu(menu);
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
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        songFragment = new SearchSongFragment();
        songFragment.setOnSongClickListener(mSelectSongListener);

        ctrlFragment = new MusicControllerFragment();

        FragmentManager fm = getSupportFragmentManager();
        fm.beginTransaction()
                .replace(R.id.fragment_song, songFragment)
                .replace(R.id.fragment_controller, ctrlFragment)
                .commit();
    }

    private final SearchView.OnQueryTextListener mOnQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            songFragment.query(query);
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    private final SearchView.OnCloseListener mOnCloseListener = new SearchView.OnCloseListener() {
        @Override
        public boolean onClose() {
            songFragment.cancel();
            return true;
        }
    };

    private final BaseSongFragment.OnSongClickListener mSelectSongListener =
            new BaseSongFragment.OnSongClickListener() {

                @Override
                public void onSongClick(List<Song> songs, int position) {
                    if (mMusicPlayer != null) {
                        int index = mMusicPlayer.insert(0, songs.get(position));
                        if (index >= 0) {
                            mMusicPlayer.play(index);
                        } else {
                            Log.e(TAG, "Failed to insert song in media player.");
                        }
                    }
                }
            };

}
