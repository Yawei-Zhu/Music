package com.wind.music.model;

import com.wind.music.model.impl.LocalModelImpl;

public final class ModelFactory {
    public static LocalModel createLocalModel() {
        return new LocalModelImpl();
    }
}
