package com.wind.music.view;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.text.TextUtilsCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;

import com.google.gson.Gson;
import com.wind.music.adapter.FragmentAdapter;
import com.wind.music.adapter.SongRecyclerAdapter;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.bean.SongInfoBean;
import com.wind.music.fragment.BaseFragment;
import com.wind.music.R;
import com.wind.music.fragment.NetSongFragment;
import com.wind.music.service.PlayerService;
import com.wind.music.util.MusicPlayer;
import com.wind.music.util.Network;
import com.wind.music.util.NetworkListener;
import com.wind.music.util.Urls;

import java.util.ArrayList;
import java.util.List;

public class BillBoardActivity extends BaseActivity {
    private ViewPager mViewPager;

    private MediaPlayer mediaPlayer;

    private MusicPlayer player;
    private int type = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab());
        tabLayout.addTab(tabLayout.newTab());

        final List<NetSongFragment> fragments = new ArrayList<>();
        fragments.add(NetSongFragment.of(NetSongFragment.TYPE_HOT));
        fragments.add(NetSongFragment.of(NetSongFragment.TYPE_NEW));
        FragmentAdapter pagerAdapter = new FragmentAdapter(getSupportFragmentManager(), fragments);

        for (NetSongFragment f : fragments) {
            final NetSongFragment frag = f;
            frag.setOnItemClickListener(new SongRecyclerAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(final BillBoardBean.Song item, final int position) {
                    if (player != null) {
                        if (type != frag.getType()) {
                            player.setData(frag.getSongs());
                            player.play(position);
                            type = frag.getType();
                        } else {
                            if (player.isPlaying()) {
                                if (player.whatIsPlaying() == position) {
                                    player.pause();
                                } else {
                                    player.play(position);
                                }
                            } else {
                                if (player.whatIsPlaying() == position) {
                                    player.play();
                                } else {
                                    player.play(position);
                                }
                            }
                        }
                    }
                }
            });
        }

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    /*
     * player start
     **********************************************************************************************/

    private PlayerConnection playerConnection;

    private void bindPlayer() {
        Intent service = new Intent(this, PlayerService.class);
        playerConnection = new PlayerConnection();
        bindService(service, playerConnection, BIND_AUTO_CREATE);
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
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
        }
    }
    /*
     * player end
     **********************************************************************************************/

}
