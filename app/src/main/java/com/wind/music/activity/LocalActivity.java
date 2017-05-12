package com.wind.music.activity;

import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.wind.music.R;
import com.wind.music.adapter.SongAdapter;
import com.wind.music.bean.Song;
import com.wind.music.decoration.DefaultDecoration;
import com.wind.music.util.LoadLocal;
import com.wind.music.util.LoadLocalListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LocalActivity extends AppCompatActivity {
    private static final String TAG = LocalActivity.class.getSimpleName();
    private static final int MODE_CYCLE = 0;
    private static final int MODE_ORDER = 1;
    private static final int MODE_RANDOM = 2;
    private static final int MODE_SINGLE = 3;


    private RecyclerView recyclerView;
    private TextView tvPlay;
    private TextView tvTitle;

    private ArrayList<Song> songs;
    private SongAdapter adapter;
    private MediaPlayer player;
    private int index = 0;
    private int pausePosition = 0;

    private int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);

        initLayout();
        loadData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (player != null) {
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WARNING")
                .setMessage("Are you want to close Music?")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LocalActivity.super.onBackPressed();
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    private void initLayout() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }

        TextView tv = (TextView) findViewById(R.id.tv_mode);
        if (tv != null) {
            tv.setOnClickListener(switchPlayModeListener);
        }

        tvTitle = (TextView) findViewById(R.id.tv_title);

        tvPlay = (TextView) findViewById(R.id.tv_play);
        if (tvPlay != null) {
            tvPlay.setOnClickListener(pauseListener);
        }

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        songs = new ArrayList<>();
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
                if (songs != null) {
                    songs.addAll(data);
                }
                if (adapter != null) {
                    adapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void initPlayer() {
        player = new MediaPlayer();
        player.setOnPreparedListener(onPreparedListener);
        player.setOnCompletionListener(onCompletionListener);
        player.setOnErrorListener(onErrorListener);
    }

    private void play(int index) {
        if (songs != null && songs.size() > index) {
            Song song = songs.get(index);
            try {
                if (player == null) {
                    initPlayer();
                }
                player.reset();
                player.setDataSource(song.data);
                player.prepareAsync();
                if (tvTitle != null) {
                    tvTitle.setText(song.title);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private final View.OnClickListener switchPlayModeListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mode++;
            if (mode == 4) {
                mode = 0;
            }

            if (v instanceof TextView) {
                TextView tv = (TextView) v;
                switch (mode) {
                    case MODE_CYCLE:
                        tv.setText("循环");
                        break;
                    case MODE_ORDER:
                        tv.setText("顺序");
                        break;
                    case MODE_RANDOM:
                        tv.setText("随机");
                        break;
                    case MODE_SINGLE:
                        tv.setText("单曲");
                        break;
                }
            }
        }
    };

    private final View.OnClickListener pauseListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (player != null) {
                if (player.isPlaying()) {
                    pausePosition = player.getCurrentPosition();
                    player.stop();
                    if (v instanceof TextView) {
                        ((TextView) v).setText("播放");
                    }
                } else {
                    play(index);
                    if (v instanceof TextView) {
                        ((TextView) v).setText("暂停");
                    }
                }
            }
        }
    };

    private final SongAdapter.OnItemClickListener selectSongListener = new SongAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(Song item, int position) {
            pausePosition = 0;
            play(index = position);
        }
    };

    private final MediaPlayer.OnPreparedListener onPreparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            mp.seekTo(pausePosition);
            pausePosition = 0;
            mp.start();
            if (tvPlay != null) {
                tvPlay.setText("暂停");
            }
        }
    };

    private final MediaPlayer.OnCompletionListener onCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
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
                    break;
                case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                    break;
                case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                    break;
                case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                    break;
            }
            return true;
        }
    };
}
