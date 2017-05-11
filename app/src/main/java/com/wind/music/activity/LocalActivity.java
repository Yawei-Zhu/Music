package com.wind.music.activity;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
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
import com.wind.music.decoration.DefaultDecoration;

import java.util.ArrayList;
import java.util.Random;

public class LocalActivity extends AppCompatActivity {
    private static final String TAG = LocalActivity.class.getSimpleName();

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

        tvTitle = (TextView) findViewById(R.id.tv_title);

        tvPlay = (TextView) findViewById(R.id.tv_play);
        tvPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (player != null) {
                    if (player.isPlaying()) {
                        pausePosition = player.getCurrentPosition();
                        player.stop();
                        tvPlay.setText("播放");
                    } else {
                        play(index);
                        tvPlay.setText("暂停");
                    }
                }
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        LinearLayoutManager lm = new LinearLayoutManager(LocalActivity.this);
        lm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(lm);

        recyclerView.addItemDecoration(new DefaultDecoration());

        songs = new ArrayList<>();
        adapter = new SongAdapter(LocalActivity.this, songs);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new SongAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Song item, int position) {
                pausePosition = 0;
                play(index = position);
            }
        });

        player = new MediaPlayer();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.seekTo(pausePosition);
                pausePosition = 0;
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
                        play(index);
                        break;
                    case 1:
                        index++;
                        if (index < songs.size()) {
                            play(index);
                        } else {
                        }
                        break;
                    case 2:
                        Random r = new Random();
                        index = r.nextInt(songs.size());
                        play(index);
                        break;
                    case 3:
                        mp.seekTo(0);
                        mp.start();
                        break;
                }
            }
        });

    }

    private void play(int index) {
        try {
            Song song = songs.get(index);
            player.reset();
            player.setDataSource(song.data);
            player.prepareAsync();
            tvTitle.setText(song.title);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        cursor.moveToFirst();
        int cc = cursor.getColumnCount();
        for (int i = 0; i < cc; i++) {
            Log.i(TAG, "loadData: index:" + i + ", " + cursor.getColumnName(i) + "=" + cursor.getString(i));
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            Song song = new Song();
            data.add(song);
            song._id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            song.title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            song.data = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            song.duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));

            song.artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            song.artist_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID));

            song.album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
            song.album_id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));

            song.album_art = query(resolver, song.album_id, sortOrder);
        }

        cursor.close();

        songs.clear();
        songs.addAll(data);
        adapter.notifyDataSetChanged();
        index = 0;
        tvTitle.setText(songs.get(index).title);
        Uri contentUri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        long id = songs.get(0)._id;
        Uri uri1 = ContentUris.withAppendedId(contentUri, id);
    }

    private String query(ContentResolver resolver, long album_id, String sortOrder) {
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String selection = MediaStore.Audio.Albums._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(album_id)};
        Cursor cursor = resolver.query(uri, null, selection, selectionArgs, null);

        if (cursor != null && cursor.moveToFirst()) {
            String album_art = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART));
            cursor.close();
            return album_art;
        } else if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.stop();
        player.release();
        player = null;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("WARNING")
                .setMessage("Are you sure you want to close the music player?")
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
}
