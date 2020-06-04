package com.example.projectb;

import java.io.Serializable;

public class MusicFile implements Serializable {


    int id;
    public Song song;
    public byte[] musicFileExtract;




    public MusicFile(int id, Song song, byte[] musicFileExtract) {
        this.id = id;
        this.song = song;
        this.musicFileExtract = musicFileExtract;
    }

    public Song getSong() {
        return song;
    }

    public void setSong(Song song) {
        this.song = song;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte[] getMusicFileExtract() {
        return musicFileExtract;
    }

    public void setMusicFileExtract(byte[] musicFileExtract) {
        this.musicFileExtract = musicFileExtract;
    }
}
