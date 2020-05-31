package com.wind.music.bean;

public class Song {
    private boolean isLocal;

    private String song_id;
    private String title;

    private String pic_small;
    private String pic_big;
    private String pic_radio;
    private String pic_premium;
    private String pic_huge;

    private String artist_id;
    private String author;
    private String all_artist_id;

    private String album_id;
    private String album_no;
    private String album_title;

    private int has_mv;
    private int has_mv_mobile;
    private String lrclink;

    private String show_link;
    private String file_link;
    private String song_file_id;
    private int file_duration;
    private int file_size;
    private String file_extension;

    public void setBillBoardSong(BillBoardBean.Song song) {
        this.isLocal = false;
        this.song_id = song.getSong_id();
        this.title = song.getTitle();

        this.pic_small = song.getPic_small();
        this.pic_big = song.getPic_big();
        this.pic_radio = song.getPic_radio();
        this.pic_premium = song.getPic_premium();
        this.pic_huge = song.getPic_huge();

        this.artist_id = song.getArtist_id();
        this.author = song.getArtist_name();
        this.all_artist_id = song.getAll_artist_id();

        this.album_id = song.getAlbum_id();
        this.album_no = song.getAlbum_no();
        this.album_title = song.getAlbum_title();

        this.has_mv = song.getHas_mv();
        this.lrclink = song.getLrclink();
        this.has_mv = song.getHas_mv();
        this.has_mv_mobile = song.getHas_mv_mobile();
    }

    public void setSongInfoBean(SongInfoBean songInfo) {
        setSongInfo(songInfo.getSonginfo());
        setSongBitrate(songInfo.getBitrate());
    }

    public void setSongInfo(SongInfoBean.SongInfo song) {
        this.isLocal = false;
        this.song_id = song.getSong_id();
        this.title = song.getTitle();

        this.pic_small = song.getPic_small();
        this.pic_big = song.getPic_big();
        this.pic_radio = song.getPic_radio();
        this.pic_premium = song.getPic_premium();
        this.pic_huge = song.getPic_huge();

        this.artist_id = song.getArtist_id();
        this.author = song.getAuthor();
        this.all_artist_id = song.getAll_artist_id();

        this.album_id = song.getAlbum_id();
        this.album_no = song.getAlbum_no();
        this.album_title = song.getAlbum_title();

        this.has_mv = song.getHas_mv();
        this.lrclink = song.getLrclink();
        this.has_mv = song.getHas_mv();
        this.has_mv_mobile = song.getHas_mv_mobile();
    }

    public void setSongBitrate(SongInfoBean.Bitrate bitrate) {
        this.show_link = bitrate.getShow_link();
        this.file_link = bitrate.getFile_link();
        this.song_file_id = bitrate.getSong_file_id();
        this.file_duration = bitrate.getFile_duration();
        this.file_size = bitrate.getFile_size();
        this.file_extension = bitrate.getFile_extension();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Song)) {
            return false;
        }

        if (this == obj) {
            return true;
        }

        Song that = (Song) obj;
        if (that.isLocal == this.isLocal && that.song_id == this.song_id) {
            return true;
        }

        return false;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public String getSong_id() {
        return song_id;
    }

    public void setSong_id(String song_id) {
        this.song_id = song_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPic_small() {
        return pic_small;
    }

    public void setPic_small(String pic_small) {
        this.pic_small = pic_small;
    }

    public String getPic_big() {
        return pic_big;
    }

    public void setPic_big(String pic_big) {
        this.pic_big = pic_big;
    }

    public String getPic_radio() {
        return pic_radio;
    }

    public void setPic_radio(String pic_radio) {
        this.pic_radio = pic_radio;
    }

    public String getPic_premium() {
        return pic_premium;
    }

    public void setPic_premium(String pic_premium) {
        this.pic_premium = pic_premium;
    }

    public String getPic_huge() {
        return pic_huge;
    }

    public void setPic_huge(String pic_huge) {
        this.pic_huge = pic_huge;
    }

    public String getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(String artist_id) {
        this.artist_id = artist_id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAll_artist_id() {
        return all_artist_id;
    }

    public void setAll_artist_id(String all_artist_id) {
        this.all_artist_id = all_artist_id;
    }

    public String getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(String album_id) {
        this.album_id = album_id;
    }

    public String getAlbum_no() {
        return album_no;
    }

    public void setAlbum_no(String album_no) {
        this.album_no = album_no;
    }

    public String getAlbum_title() {
        return album_title;
    }

    public void setAlbum_title(String album_title) {
        this.album_title = album_title;
    }

    public int getHas_mv() {
        return has_mv;
    }

    public void setHas_mv(int has_mv) {
        this.has_mv = has_mv;
    }

    public int getHas_mv_mobile() {
        return has_mv_mobile;
    }

    public void setHas_mv_mobile(int has_mv_mobile) {
        this.has_mv_mobile = has_mv_mobile;
    }

    public String getLrclink() {
        return lrclink;
    }

    public void setLrclink(String lrclink) {
        this.lrclink = lrclink;
    }

    public String getShow_link() {
        return show_link;
    }

    public void setShow_link(String show_link) {
        this.show_link = show_link;
    }

    public String getFile_link() {
        return file_link;
    }

    public void setFile_link(String file_link) {
        this.file_link = file_link;
    }

    public String getSong_file_id() {
        return song_file_id;
    }

    public void setSong_file_id(String song_file_id) {
        this.song_file_id = song_file_id;
    }

    public int getFile_duration() {
        return file_duration;
    }

    public void setFile_duration(int file_duration) {
        this.file_duration = file_duration;
    }

    public int getFile_size() {
        return file_size;
    }

    public void setFile_size(int file_size) {
        this.file_size = file_size;
    }

    public String getFile_extension() {
        return file_extension;
    }

    public void setFile_extension(String file_extension) {
        this.file_extension = file_extension;
    }
}
