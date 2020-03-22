package com.wind.music.model;

import android.graphics.Bitmap;

public interface ImageModel extends Model {
    Bitmap loadImage(String path);
}
