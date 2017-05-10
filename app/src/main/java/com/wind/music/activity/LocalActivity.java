package com.wind.music.activity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.wind.music.R;
import com.wind.music.adapter.SongAdapter;
import com.wind.music.bean.Song;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class LocalActivity extends AppCompatActivity {
    private static final String TAG = LocalActivity.class.getSimpleName();

    private RecyclerView recyclerView;
    private TextView tvPlay;

    private ArrayList<Song> songs;
    private SongAdapter adapter;
    private MediaPlayer player;
    private int index = 0;

    private int mode = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView tv = (TextView) findViewById(R.id.tv_mode);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView tv = (TextView) v;
                mode++;
                if (mode == 4) {
                    mode = 0;
                }
                switch (mode) {
                    case 0:
                        tv.setText("循环");
                        break;
                    case 1:
                        tv.setText("顺序");
                        break;
                    case 2:
                        tv.setText("随机");
                        break;
                    case 3:
                        tv.setText("单曲");
                        break;
                }
            }
        });

        tvPlay = (TextView) findViewById(R.id.tv_play);
        tvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null) {
                    if (player.isPlaying()) {
                        player.pause();
                        tvPlay.setText("播放");
                    } else {
                        player.start();
                        tvPlay.setText("播放");
                    }
                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager lm = new LinearLayoutManager(LocalActivity.this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);

        songs = new ArrayList<>();
        adapter = new SongAdapter(LocalActivity.this, songs);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Song item) {
                try {
                    index = songs.indexOf(item);
                    player.reset();
                    player.setDataSource(item.path);
                    player.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

        player = new MediaPlayer();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.seekTo(0);
                mp.start();
                tvPlay.setText("暂停");
            }
        });
        player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                switch (mode) {
                    case 0:
                        index++;
                        if (index == songs.size()) {
                            index = 0;
                        }
                        try {
                            player.reset();
                            player.setDataSource(songs.get(index).path);
                            player.prepareAsync();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 1:
                        index++;
                        if (index < songs.size()) {
                            try {
                                player.reset();
                                player.setDataSource(songs.get(index).path);
                                player.prepareAsync();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                    case 2:
                        try {
                            Random r = new Random();
                            index = r.nextInt(songs.size());
                            player.reset();
                            player.setDataSource(songs.get(index).path);
                            player.prepareAsync();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        break;
                    case 3:
                        mp.seekTo(0);
                        mp.start();
                        break;
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        loadData();
    }

    private void loadData() {
        ContentResolver resolver = getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
        ArrayList<Song> data = new ArrayList<>();
        int count = cursor.getCount();
        Log.i(TAG, "loadData: count=" + count);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Song song = new Song();
            data.add(song);

            song.title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            song.artist_name = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            song.path = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            song.file_duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
        }

        cursor.close();

        songs.clear();
        songs.addAll(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
        player = null;
    }
}
