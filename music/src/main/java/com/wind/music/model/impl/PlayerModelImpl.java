package com.wind.music.model.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.gson.Gson;
import com.wind.music.Application;
import com.wind.music.bean.SongInfoBean;
import com.wind.music.model.PlayerModel;
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

public class PlayerModelImpl implements PlayerModel {

    @Override
    public SongInfoBean loadSongInfo(int songId) {
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
}
