package com.example.projectb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ChooseSongActivity extends AppCompatActivity {
    TextView artistPick;
    TextView viewSongs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_song);

        viewSongs = (TextView) findViewById(R.id.viewingSongsBy);
        artistPick = (TextView) findViewById(R.id.artistPick);
        String songsByArtist =  getIntent().getStringExtra("chosen_artist");
        artistPick.setText(songsByArtist);
        System.out.println(songsByArtist);

    }
}
