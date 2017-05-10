package com.wind.music.util;

import com.wind.music.bean.BillBoard;
import com.wind.music.bean.NetworkSong;
import com.wind.music.bean.Song;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2017/5/8.
 */

public class JSONParser {
    public static void parseBillBoard(BillBoard billBoard, String json) throws JSONException {
        JSONObject obj = new JSONObject(json);
        JSONArray arr = obj.getJSONArray("song_list");
        int len = arr.length();
        for (int i = 0; i < len; i++) {
            JSONObject sObj = arr.getJSONObject(i);

            NetworkSong song = new NetworkSong();
            billBoard.songs.add(song);
            song.artist_id = sObj.getLong("artist_id");
            song.language = sObj.getString("language");
            song.pic_big = sObj.getString("pic_big");
            song.pic_small = sObj.getString("pic_small");
            song.country = sObj.getString("country");
            song.publishtime = sObj.getString("publishtime");
            song.album_no = sObj.getLong("album_no");
            song.lrclink = sObj.getString("lrclink");
            song.file_duration = sObj.getInt("duration");
            song.song_id = sObj.getLong("_id");
            song.title = sObj.getString("title");
            song.artist_name = sObj.getString("artist");
        }
    }
}
