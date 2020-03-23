package com.wind.music.presenter.impl;

import android.graphics.Bitmap;

import com.wind.music.model.ImageModel;
import com.wind.music.model.ModelFactory;
import com.wind.music.presenter.ImagePresenter;
import com.wind.music.util.AsyncExecutor;
import com.wind.music.view.ImageView;
import com.wind.music.view.View;

import java.util.HashMap;
import java.util.concurrent.Future;

public class ImagePresenterImpl implements ImagePresenter {
    private ImageView mView;
    private ImageModel mModel;
    private HashMap<String, Future<?>> mLoadMap = new HashMap<>();

    public ImagePresenterImpl() {
        mModel = model();
    }

    @Override
    public ImageModel model() {
        return ModelFactory.createImageModel();
    }

    @Override
    public void setView(View view) {
        ImageView imageView = (ImageView) view;
        if (mView != imageView) {
            for (Future<?> value : mLoadMap.values()) {
                value.cancel(false);
            }
            mLoadMap.clear();
            mView = imageView;
        }
    }

    @Override
    public int load(String path) {
        Future<?> future = AsyncExecutor.of().submit(path, new AsyncExecutor.Executable<String, Bitmap>() {
            @Override
            public Bitmap onLoading(String param) {
                return mModel.loadImage(param);
            }

            @Override
            public void onLoaded(String param, Bitmap result) {
                if (mLoadMap.remove(param) != null) {
                    if (mView != null) {
                        mView.onLoaded(param, result);
                    }
                }
            }
        });
        mLoadMap.put(path, future);
        return 0;
    }

    @Override
    public boolean cancel(String path) {
        Future<?> future = mLoadMap.remove(path);
        if (future != null) {
            future.cancel(false);
            return true;
        }

        return false;
    }
}
