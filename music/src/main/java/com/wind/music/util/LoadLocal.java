package com.wind.music.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.wind.music.bean.BillBoardBean;

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

    public static void loadSongs(final LoadLocalListener<List<BillBoardBean.Song>> listener) {
        new AsyncTask<Void, Void, List<BillBoardBean.Song>>() {
            @Override
            protected List<BillBoardBean.Song> doInBackground(Void... params) {
                return loadData();
            }

            @Override
            protected void onPostExecute(List<BillBoardBean.Song> songs) {
                if (listener != null)
                    listener.onRespond(songs);
            }
        }.execute();
    }

    private static List<BillBoardBean.Song> loadData() {
        ArrayList<BillBoardBean.Song> data = new ArrayList<>();

        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
        Cursor cursor = resolver.query(uri, null, null, null, sortOrder);
        if (cursor == null) {
            return data;
        }
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            BillBoardBean.Song song = new BillBoardBean.Song();
            data.add(song);
            song.setSong_id(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            song.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            song.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            song.setFile_duration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));

            song.setArtist_name(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            song.setArtist_id(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)));

            song.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
            song.setAlbum_no(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));

            song.setPic_small(query(resolver, song.getAlbum_no()));
        }
        cursor.close();
        Collections.sort(data, new Comparator<BillBoardBean.Song>() {
            @Override
            public int compare(BillBoardBean.Song l, BillBoardBean.Song r) {
                String lp;
                try {
                    lp = PinyinHelper.convertToPinyinString(l.getTitle(), ",", PinyinFormat.WITHOUT_TONE);
                } catch (PinyinException e) {
                    e.printStackTrace();
                    lp = l.getTitle();
                }
                String rp;
                try {
                    rp = PinyinHelper.convertToPinyinString(r.getTitle(), ",", PinyinFormat.WITHOUT_TONE);
                } catch (PinyinException e) {
                    e.printStackTrace();
                    rp = r.getTitle();
                }
                return lp.toLowerCase().compareTo(rp.toLowerCase());
            }
        });

        return data;
    }

    private static String query(ContentResolver resolver, long album_id) {
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{MediaStore.Audio.Albums.ALBUM_ART};
        String selection = MediaStore.Audio.Albums._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(album_id)};
        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, null);

        if (cursor != null && cursor.moveToFirst()) {
            String album_art = cursor.getString(0);
            cursor.close();
            return album_art;
        } else if (cursor != null) {
            cursor.close();
        }
        return null;
    }

}
