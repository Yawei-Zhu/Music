package com.wind.music.model.impl;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;

import com.wind.music.Application;
import com.wind.music.model.ImageModel;
import com.wind.music.util.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageModelImpl implements ImageModel {
    private LruCache<String, Bitmap> mCache = new LruCache<>(100);

    @Override
    public Bitmap loadImage(String path) {
        boolean bNetwork = Utils.isNetworkPath(path);
        if (bNetwork) {
            return loadNetworkImage(path);
        } else {
            return loadLocalImage(path);
        }
    }

    private Bitmap loadNetworkImage(String path) {
        Bitmap bitmap;
        String cachePath;

        bitmap = mCache.get(path);
        if (bitmap != null) {
            return bitmap;
        }

        cachePath = getCachePath(path);
        bitmap = BitmapFactory.decodeFile(cachePath);
        if (bitmap != null) {
            mCache.put(path, bitmap);
            return bitmap;
        }

        try {
            URL url = new URL(path);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        if (bitmap != null) {
            mCache.put(path, bitmap);
            cacheImageFile(cachePath, bitmap);
        }

        return bitmap;
    }

    private Bitmap loadLocalImage(String path) {
        Bitmap bitmap;

        bitmap = mCache.get(path);
        if (bitmap != null) {
            return bitmap;
        }

        bitmap = BitmapFactory.decodeFile(path);
        if (bitmap != null) {
            mCache.put(path, bitmap);
        }

        return bitmap;
    }

    private void cacheImageFile(String cachePath, Bitmap bitmap) {
        try {
            File file = new File(cachePath);
            file.getParentFile().mkdirs();
            FileOutputStream out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getCachePath(String url) {
        int start = url.lastIndexOf('/');
        String name = url.substring(start + 1);
        File cacheDir = Application.getApp().getExternalCacheDir();
        if (cacheDir == null) {
            cacheDir = Application.getApp().getCacheDir();
        }
        File cacheFile = new File(cacheDir, "image/" + name + ".jpg");
        return cacheFile.getAbsolutePath();
    }

}
