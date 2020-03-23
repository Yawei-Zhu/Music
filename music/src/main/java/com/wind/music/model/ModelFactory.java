package com.wind.music.model;

import android.content.Context;

import com.wind.music.model.impl.ImageModelImpl;
import com.wind.music.model.impl.PlayerModelImpl;
import com.wind.music.model.impl.SongModelImpl;

public final class ModelFactory {
    public static SongModel createSongModel(Context context) {
        return new SongModelImpl(context);
    }

    public static ImageModel createImageModel() {
        return new ImageModelImpl();
    }

    public static PlayerModel createPlayerModel() {
        return new PlayerModelImpl();
    }
}
