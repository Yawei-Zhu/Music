package com.wind.music.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.util.Log;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.wind.music.bean.Artist;
import com.wind.music.bean.Song;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
        Collections.sort(data, new Comparator<Song>() {
            @Override
            public int compare(Song l, Song r) {
                String lp = null;
                try {
                    lp = PinyinHelper.convertToPinyinString(l.title, ",", PinyinFormat.WITHOUT_TONE);
                } catch (PinyinException e) {
                    e.printStackTrace();
                    lp = l.title;
                }
                String rp = null;
                try {
                    rp = PinyinHelper.convertToPinyinString(r.title, ",", PinyinFormat.WITHOUT_TONE);
                } catch (PinyinException e) {
                    e.printStackTrace();
                    rp = r.title;
                }
                return lp.toLowerCase().compareTo(rp.toLowerCase());
            }
        });
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

    private static List<Artist> queryArtist() {
        List<Artist> artists = new ArrayList<>();
        Uri uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI;
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int column = cursor.getColumnCount();
            Log.i(TAG, "queryArtist: start");
            for (int i = 0; i < column; i++) {
                Log.i(TAG, i + " " + cursor.getColumnName(i) + "=" + cursor.getString(i));
            }
            Log.i(TAG, "queryArtist: end");
            cursor.close();
        }
        return artists;
    }

    private static List<Artist> queryAlbum() {
        List<Artist> artists = new ArrayList<>();
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int column = cursor.getColumnCount();
            Log.i(TAG, "queryAlbum: start");
            for (int i = 0; i < column; i++) {
                Log.i(TAG, i + " " + cursor.getColumnName(i) + "=" + cursor.getString(i));
            }
            Log.i(TAG, "queryAlbum: end");
            cursor.close();
        }
        return artists;
    }

    private static List<Artist> queryGenre() {
        List<Artist> artists = new ArrayList<>();
        Uri uri = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int column = cursor.getColumnCount();
            Log.i(TAG, "queryGenre: start");
            for (int i = 0; i < column; i++) {
                Log.i(TAG, i + " " + cursor.getColumnName(i) + "=" + cursor.getString(i));
            }
            Log.i(TAG, "queryGenre: end");
            cursor.close();
        }
        return artists;
    }

    private static List<Artist> queryPlaylists() {
        List<Artist> artists = new ArrayList<>();
        Uri uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI;
        Cursor cursor = mContext.getContentResolver().query(uri, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            int column = cursor.getColumnCount();
            Log.i(TAG, "Playlists: start");
            for (int i = 0; i < column; i++) {
                Log.i(TAG, i + " " + cursor.getColumnName(i) + "=" + cursor.getString(i));
            }
            Log.i(TAG, "Playlists: end");
            cursor.close();
        }
        return artists;
    }

}
