package com.wind.music.model.impl;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.gson.Gson;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.fragment.SongFragment;
import com.wind.music.model.SongModel;
import com.wind.music.util.Setting;
import com.wind.music.util.Urls;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SongModelImpl implements SongModel {
    private final String TAG = getClass().getSimpleName();
    private Context mContext;

    public SongModelImpl(Context context) {
        mContext = context;
    }

    @Override
    public BillBoardBean loadLocalSongs(int minLength) {

        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
        Cursor cursor = resolver.query(uri, null, null, null, sortOrder);
        if (cursor == null) {
            return null;
        }
        BillBoardBean billBoardBean = new BillBoardBean();
        ArrayList<BillBoardBean.Song> songs = new ArrayList<>();
        billBoardBean.setSong_list(songs);
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            BillBoardBean.Song song = new BillBoardBean.Song();
            song.setIs_local(true);

            song.setSong_id(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID)));
            song.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            song.setPath(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            song.setFile_duration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));

            song.setArtist_name(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            song.setArtist_id(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID)));

            song.setAlbum(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
            song.setAlbum_no(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)));

            song.setPic_small(getAlbumPicture(resolver, song.getAlbum_no()));

            if (song.getFile_duration() >= minLength) {
                songs.add(song);
            }
        }
        cursor.close();

        Collections.sort(songs, new Comparator<BillBoardBean.Song>() {
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

        return billBoardBean;
    }

    private String getAlbumPicture(ContentResolver resolver, long album_id) {
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{MediaStore.Audio.Albums.ALBUM_ART};
        String selection = MediaStore.Audio.Albums._ID + "=?";
        String[] selectionArgs = new String[]{String.valueOf(album_id)};
        Cursor cursor = resolver.query(uri, projection, selection, selectionArgs, null);

        String album_art = null;
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                album_art = cursor.getString(0);
            }
            cursor.close();
        }
        return album_art;
    }

    @Override
    public BillBoardBean loadNetworkSongs(int type, int size) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try {
            Urls urls = new Urls();
            URL url = urls.getBillUrl(type, size);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream in = conn.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Gson gson = new Gson();
        return gson.fromJson(stringBuilder.toString(), BillBoardBean.class);
    }

}
