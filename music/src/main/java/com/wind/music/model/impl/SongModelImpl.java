package com.wind.music.model.impl;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.github.stuxuhai.jpinyin.PinyinException;
import com.github.stuxuhai.jpinyin.PinyinFormat;
import com.github.stuxuhai.jpinyin.PinyinHelper;
import com.google.gson.Gson;
import com.wind.music.Application;
import com.wind.music.bean.BillBoardBean;
import com.wind.music.bean.SearchBean;
import com.wind.music.bean.Song;
import com.wind.music.bean.SongInfoBean;
import com.wind.music.model.SongModel;
import com.wind.music.util.Urls;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
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
    public List<Song> loadLocalSongs(int minLength) {

        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        String sortOrder = MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
        Cursor cursor = resolver.query(uri, null, null, null, sortOrder);
        if (cursor == null) {
            return null;
        }
        ArrayList<Song> songs = new ArrayList<>();
        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
            if (cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)) < minLength) {
                continue;
            }

            Song song = new Song();
            songs.add(song);
            song.setLocal(true);

            song.setSong_id(String.valueOf(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media._ID))));
            song.setTitle(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE)));
            song.setFile_link(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA)));
            song.setFile_duration(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION)));

            song.setAuthor(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST)));
            song.setArtist_id(String.valueOf(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST_ID))));

            song.setAlbum_title(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM)));
            song.setAlbum_id(String.valueOf(cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))));

            song.setPic_small(getAlbumPicture(resolver, song.getAlbum_id()));
        }
        cursor.close();

        Collections.sort(songs, new Comparator<Song>() {
            @Override
            public int compare(Song l, Song r) {
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

        return songs;
    }

    private String getAlbumPicture(ContentResolver resolver, String album_id) {
        Uri uri = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
        String[] projection = new String[]{MediaStore.Audio.Albums.ALBUM_ART};
        String selection = MediaStore.Audio.Albums._ID + "=?";
        String[] selectionArgs = new String[]{album_id};
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

    @Override
    public SongInfoBean loadSongInfo(String songId) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try {
            Urls urls = new Urls();
            URL url = urls.getSongInfoUrl(songId);
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
        return gson.fromJson(stringBuilder.toString(), SongInfoBean.class);
    }

    @Override
    public String cacheSong(String path) {
        String cachePath;
        String cachingPath;
        byte buffer[] = new byte[8192];
        int length;

        cachePath = getCachePath(path);
        File cacheFile = new File(cachePath);
        if (cacheFile.isFile()) {
            return cachePath;
        }
        cacheFile.getParentFile().mkdirs();

        cachingPath = getCachingPath(path);
        File cachingFile = new File(cachingPath);
        cachingFile.getParentFile().mkdirs();

        BufferedInputStream in = null;
        BufferedOutputStream out = null;
        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(conn.getInputStream());
            out = new BufferedOutputStream(new FileOutputStream(cachingFile));
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
            cachingFile.renameTo(cacheFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return cachePath;
    }

    private String getCachePath(String path) {
        int start = path.lastIndexOf('/');
        String name = path.substring(start + 1);
        File cacheDir = Application.getApp().getExternalCacheDir();
        if (cacheDir == null) {
            cacheDir = Application.getApp().getCacheDir();
        }
        File cacheFile = new File(cacheDir, "music/" + name + ".mp3");
        return cacheFile.getAbsolutePath();
    }

    private String getCachingPath(String path) {
        int start = path.lastIndexOf('/');
        String name = path.substring(start + 1);
        File cacheDir = Application.getApp().getExternalCacheDir();
        if (cacheDir == null) {
            cacheDir = Application.getApp().getCacheDir();
        }
        File cachingFile = new File(cacheDir, "music/" + name + ".mp3.cache");
        return cachingFile.getAbsolutePath();
    }

    @Override
    public SearchBean query(String key) {
        StringBuilder stringBuilder = new StringBuilder();
        String line;

        try {
            Urls urls = new Urls();
            URL url = urls.getQueryUrl(key);
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
        return gson.fromJson(stringBuilder.toString(), SearchBean.class);
    }

}
