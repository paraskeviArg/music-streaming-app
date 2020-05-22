package com.example.projectb;

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

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.song_row, null, true);
        TextView txtTitle = rowView.findViewById(R.id.txt);
        TextView txtTitle1 = rowView.findViewById(R.id.txt2);
        System.out.println("trackname " + array[position].getTrackName());
        txtTitle.setText(array[position].getTrackName());
        System.out.println("artistname " + array[position].getArtistName());
        txtTitle1.setText(array[position].getArtistName());
        txtTitle.setTextSize(20);
        txtTitle1.setTextSize(20);
        return rowView;
    }
}