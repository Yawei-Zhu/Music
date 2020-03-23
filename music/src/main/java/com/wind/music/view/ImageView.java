package com.wind.music.view;

import android.graphics.Bitmap;

import com.wind.music.presenter.ImagePresenter;

public interface ImageView extends View {

    void onLoaded(String path, Bitmap bitmap);
}
