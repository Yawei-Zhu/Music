package com.wind.music.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.wind.music.bean.Song;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/12.
 */

public class LoadLocal {
    private static final String TAG = LoadLocal.class.getSimpleName();
    private static Context mContext;

    public static void init(Context context) {
        mContext = context;
    }

    public static void release() {
        mContext = null;
    }

    public static void loadSongs(final LoadLocalListener<List<Song>> listener) {
        new AsyncTask<Void, Void, List<Song>>() {
            @Override
            protected List<Song> doInBackground(Void... params) {
                List<Song> result = loadData();
                return result;
            }

            @Override
            protected void onPostExecute(List<Song> songs) {
                if (listener != null)
                    listener.onRespond(songs);
            }
        }.execute();
    }

    private static List<Song> loadData() {
        ArrayList<Song> data = new ArrayList<>();

        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String[] projection = null;
        String selection = null;
        String[] selectionArgs = null;
        String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, sortOrder);
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

            song.album_art = query(resolver, song.album_id);
        }
        cursor.close();
        return data;
    }

    private static String query(ContentResolver resolver, long album_id) {
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

}
