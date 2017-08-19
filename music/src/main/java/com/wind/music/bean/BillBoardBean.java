package com.wind.music.bean;

import java.util.List;

/**
 * Created by Wind on 2017/8/19.
 */

public class BillBoardBean extends BaseBean {

    private List<Song> song_list;
    private BillBoard billboard;

    public List<Song> getSong_list() {
        return song_list;
    }

    public void setSong_list(List<Song> song_list) {
        this.song_list = song_list;
    }

    public BillBoard getBillboard() {
        return billboard;
    }

    public void setBillboard(BillBoard billboard) {
        this.billboard = billboard;
    }

    public static class BillBoard {
        private int billboard_type;
        private int billboard_no;
        private String update_date;
        private int billboard_songnum;
        private int havemore;
        private String name;

        public int getBillboard_type() {
            return billboard_type;
        }

        public void setBillboard_type(int billboard_type) {
            this.billboard_type = billboard_type;
        }

        public int getBillboard_no() {
            return billboard_no;
        }

        public void setBillboard_no(int billboard_no) {
            this.billboard_no = billboard_no;
        }

        public String getUpdate_date() {
            return update_date;
        }

        public void setUpdate_date(String update_date) {
            this.update_date = update_date;
        }

        public int getBillboard_songnum() {
            return billboard_songnum;
        }

        public void setBillboard_songnum(int billboard_songnum) {
            this.billboard_songnum = billboard_songnum;
        }

        public int getHavemore() {
            return havemore;
        }

        public void setHavemore(int havemore) {
            this.havemore = havemore;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Song {
        private int song_id;
        private String title;
        private String path;
        private int file_duration;

        private int artist_id;
        private String artist_name;

        private int album_no;
        private String album;

        private String pic_big;
        private String pic_small;
        private String lrclink;

        public int getSong_id() {
            return song_id;
        }

        public void setSong_id(int song_id) {
            this.song_id = song_id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getFile_duration() {
            return file_duration;
        }

        public void setFile_duration(int file_duration) {
            this.file_duration = file_duration;
        }

        public int getArtist_id() {
            return artist_id;
        }

        public void setArtist_id(int artist_id) {
            this.artist_id = artist_id;
        }

        public String getArtist_name() {
            return artist_name;
        }

        public void setArtist_name(String artist_name) {
            this.artist_name = artist_name;
        }

        public int getAlbum_no() {
            return album_no;
        }

        public void setAlbum_no(int album_no) {
            this.album_no = album_no;
        }

        public String getAlbum() {
            return album;
        }

        public void setAlbum(String album) {
            this.album = album;
        }

        public String getPic_big() {
            return pic_big;
        }

        public void setPic_big(String pic_big) {
            this.pic_big = pic_big;
        }

        public String getPic_small() {
            return pic_small;
        }

        public void setPic_small(String pic_small) {
            this.pic_small = pic_small;
        }

        public String getLrclink() {
            return lrclink;
        }

        public void setLrclink(String lrclink) {
            this.lrclink = lrclink;
        }
    }
}
