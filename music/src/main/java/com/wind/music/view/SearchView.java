package com.wind.music.view;

import com.wind.music.bean.SearchBean;
import com.wind.music.bean.Song;

import java.util.List;

public interface SearchView extends View {

    void onLoaded(String key, List<Song> result);

}
