package com.wind.music.bean;

import java.util.List;

public class SearchBean extends BaseBean {

    /**
     * song : [{"bitrate_fee":"{\"0\":\"0|0\",\"1\":\"-1|-1\"}","weight":"3099","songname":"THE MASS(布兰诗歌)","resource_type":"0","songid":"20726131","has_mv":"0","yyr_artist":"0","resource_type_ext":"2","artistname":"贵族乐团","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":"850713c4173095771e846L"},{"bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","weight":"1099","songname":"The Mass","resource_type":"0","songid":"672327284","has_mv":"0","yyr_artist":"0","resource_type_ext":"2","artistname":"Jezup","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","weight":"199","songname":"Living in the mass","resource_type":"0","songid":"571984585","has_mv":"0","yyr_artist":"0","resource_type_ext":"2","artistname":"Lost Dreams","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":"94082217CABD085D49B1E9"},{"bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","weight":"199","songname":"Into The Mass","resource_type":"0","songid":"675332541","has_mv":"0","yyr_artist":"0","resource_type_ext":"2","artistname":"Shimmer","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","weight":"199","songname":"I Am the Mass","resource_type":"0","songid":"674210003","has_mv":"0","yyr_artist":"0","resource_type_ext":"2","artistname":"nobody.one","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","weight":"199","songname":"Under the Mass","resource_type":"0","songid":"607010739","has_mv":"0","yyr_artist":"0","resource_type_ext":"2","artistname":"Hellish Oblivion","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","weight":"99","songname":"The Masses","resource_type":"0","songid":"664667856","has_mv":"0","yyr_artist":"0","resource_type_ext":"2","artistname":"Veak,Kumarachi,RMS,Kumarachi, RMS & Veak","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","weight":"99","songname":"The Massacre","resource_type":"0","songid":"670101280","has_mv":"0","yyr_artist":"0","resource_type_ext":"2","artistname":"sonic symphony","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","weight":"99","songname":"The Mass of Argon","resource_type":"0","songid":"670182046","has_mv":"0","yyr_artist":"0","resource_type_ext":"2","artistname":"Deep East Music","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":""},{"bitrate_fee":"{\"0\":\"129|-1\",\"1\":\"-1|-1\"}","weight":"99","songname":"The Massacre","resource_type":"0","songid":"669713700","has_mv":"0","yyr_artist":"0","resource_type_ext":"2","artistname":"Jiberish","info":"","resource_provider":"1","control":"0000000000","encrypted_songid":""}]
     * album : [{"albumname":"Burn","weight":"0","artistname":"ALN Project, The Mass Brothers, Phonomatt, K-N","resource_type_ext":"0","artistpic":"http://qukufile2.qianqian.com/data2/pic/22a1dd02bb298f77a05ffbb541a4e970/570052189/570052189.jpg@s_2,w_40,h_40","albumid":"570052188"},{"albumname":"The Massacre","weight":"0","artistname":"The Puffballs","resource_type_ext":"0","artistpic":"http://qukufile2.qianqian.com/data2/pic/d0faada47f4e0733e75ae225311bd423/578386366/578386366.jpg@s_2,w_40,h_40","albumid":"578386361"},{"albumname":"M.A.S.S.","weight":"0","artistname":"Rob Mass, The Mass Brothers","resource_type_ext":"0","artistpic":"http://qukufile2.qianqian.com/data2/pic/da040694c09c21d6c96f99ef53c9a4eb/570182603/570182603.jpg@s_2,w_40,h_40","albumid":"570182600"}]
     * order : artist,song,album
     * artist : [{"yyr_artist":"0","artistname":"The Massey Family","artistid":"239791222","artistpic":"http://qukufile2.qianqian.com/data2/pic/default_artist.jpg@s_2,w_48,h_48","weight":"0"}]
     */

    private String order;
    private List<Song> song;
    private List<Album> album;
    private List<Artist> artist;

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<Song> getSong() {
        return song;
    }

    public void setSong(List<Song> song) {
        this.song = song;
    }

    public List<Album> getAlbum() {
        return album;
    }

    public void setAlbum(List<Album> album) {
        this.album = album;
    }

    public List<Artist> getArtist() {
        return artist;
    }

    public void setArtist(List<Artist> artist) {
        this.artist = artist;
    }

    public static class Song {
        /**
         * bitrate_fee : {"0":"0|0","1":"-1|-1"}
         * weight : 3099
         * songname : THE MASS(布兰诗歌)
         * resource_type : 0
         * songid : 20726131
         * has_mv : 0
         * yyr_artist : 0
         * resource_type_ext : 2
         * artistname : 贵族乐团
         * info :
         * resource_provider : 1
         * control : 0000000000
         * encrypted_songid : 850713c4173095771e846L
         */

        private String bitrate_fee;
        private int weight;
        private String songname;
        private String resource_type;
        private String songid;
        private int has_mv;
        private String yyr_artist;
        private String resource_type_ext;
        private String artistname;
        private String info;
        private String resource_provider;
        private String control;
        private String encrypted_songid;

        public String getBitrate_fee() {
            return bitrate_fee;
        }

        public void setBitrate_fee(String bitrate_fee) {
            this.bitrate_fee = bitrate_fee;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }

        public String getSongname() {
            return songname;
        }

        public void setSongname(String songname) {
            this.songname = songname;
        }

        public String getResource_type() {
            return resource_type;
        }

        public void setResource_type(String resource_type) {
            this.resource_type = resource_type;
        }

        public String getSongid() {
            return songid;
        }

        public void setSongid(String songid) {
            this.songid = songid;
        }

        public int getHas_mv() {
            return has_mv;
        }

        public void setHas_mv(int has_mv) {
            this.has_mv = has_mv;
        }

        public String getYyr_artist() {
            return yyr_artist;
        }

        public void setYyr_artist(String yyr_artist) {
            this.yyr_artist = yyr_artist;
        }

        public String getResource_type_ext() {
            return resource_type_ext;
        }

        public void setResource_type_ext(String resource_type_ext) {
            this.resource_type_ext = resource_type_ext;
        }

        public String getArtistname() {
            return artistname;
        }

        public void setArtistname(String artistname) {
            this.artistname = artistname;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public String getResource_provider() {
            return resource_provider;
        }

        public void setResource_provider(String resource_provider) {
            this.resource_provider = resource_provider;
        }

        public String getControl() {
            return control;
        }

        public void setControl(String control) {
            this.control = control;
        }

        public String getEncrypted_songid() {
            return encrypted_songid;
        }

        public void setEncrypted_songid(String encrypted_songid) {
            this.encrypted_songid = encrypted_songid;
        }
    }

    public static class Album {
        /**
         * albumname : Burn
         * weight : 0
         * artistname : ALN Project, The Mass Brothers, Phonomatt, K-N
         * resource_type_ext : 0
         * artistpic : http://qukufile2.qianqian.com/data2/pic/22a1dd02bb298f77a05ffbb541a4e970/570052189/570052189.jpg@s_2,w_40,h_40
         * albumid : 570052188
         */

        private String albumname;
        private String weight;
        private String artistname;
        private String resource_type_ext;
        private String artistpic;
        private int albumid;

        public String getAlbumname() {
            return albumname;
        }

        public void setAlbumname(String albumname) {
            this.albumname = albumname;
        }

        public String getWeight() {
            return weight;
        }

        public void setWeight(String weight) {
            this.weight = weight;
        }

        public String getArtistname() {
            return artistname;
        }

        public void setArtistname(String artistname) {
            this.artistname = artistname;
        }

        public String getResource_type_ext() {
            return resource_type_ext;
        }

        public void setResource_type_ext(String resource_type_ext) {
            this.resource_type_ext = resource_type_ext;
        }

        public String getArtistpic() {
            return artistpic;
        }

        public void setArtistpic(String artistpic) {
            this.artistpic = artistpic;
        }

        public int getAlbumid() {
            return albumid;
        }

        public void setAlbumid(int albumid) {
            this.albumid = albumid;
        }
    }

    public static class Artist {
        /**
         * yyr_artist : 0
         * artistname : The Massey Family
         * artistid : 239791222
         * artistpic : http://qukufile2.qianqian.com/data2/pic/default_artist.jpg@s_2,w_48,h_48
         * weight : 0
         */

        private String yyr_artist;
        private String artistname;
        private int artistid;
        private String artistpic;
        private int weight;

        public String getYyr_artist() {
            return yyr_artist;
        }

        public void setYyr_artist(String yyr_artist) {
            this.yyr_artist = yyr_artist;
        }

        public String getArtistname() {
            return artistname;
        }

        public void setArtistname(String artistname) {
            this.artistname = artistname;
        }

        public int getArtistid() {
            return artistid;
        }

        public void setArtistid(int artistid) {
            this.artistid = artistid;
        }

        public String getArtistpic() {
            return artistpic;
        }

        public void setArtistpic(String artistpic) {
            this.artistpic = artistpic;
        }

        public int getWeight() {
            return weight;
        }

        public void setWeight(int weight) {
            this.weight = weight;
        }
    }
}
