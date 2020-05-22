package com.example.projectb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class SongList extends ArrayAdapter<Song> {
    private final Activity context;
    private final Song[] array;


    SongList(Activity context,
               Song[] array) {
        super(context, R.layout.artist_row, array);
        this.context = context;
        this.array = array;

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        @SuppressLint("ViewHolder") View rowView = inflater.inflate(R.layout.song_row, null, true);
        TextView txtTrack = rowView.findViewById(R.id.trackName);
        TextView textArtist = rowView.findViewById(R.id.artistName);
        TextView textAlbum = rowView.findViewById(R.id.albumName);
        System.out.println("trackname " + array[position].getTrackName());
        txtTrack.setText(array[position].getTrackName());
        System.out.println("artistname " + array[position].getArtistName());
        textArtist.setText("Artist: " + array[position].getArtistName());
        System.out.println("textAlbum " + array[position].getAlbumInfo());
        textAlbum.setText("Album: " + array[position].getAlbumInfo());
        txtTrack.setTextSize(20);
        textArtist.setTextSize(15);
        textAlbum.setTextSize(15);
        return rowView;
    }
}