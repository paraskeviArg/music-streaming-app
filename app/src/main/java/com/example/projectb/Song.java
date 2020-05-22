package com.example.projectb;

import java.io.Serializable;

public class Song implements Serializable {
    public String trackName;
    public String artistName;
    public String albumInfo;
    static final long serialVersionUID = 1L;
    public String genre;

    public Song() {
        trackName = "";
    }

    public Song(String trackName, String artistName, String albumInfo, String genre) {
        this.trackName = trackName;
        this.artistName = artistName;
        this.albumInfo = albumInfo;
        this.genre = genre;

    }

    public String getTrackName() {
        return trackName;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getAlbumInfo() {
        return albumInfo;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public String toString() {
        return "com.example.projectb.Song{" +
                "trackName='" + trackName + '\'' +
                ", artistName='" + artistName + '\'' +
                ", albumInfo='" + albumInfo + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}