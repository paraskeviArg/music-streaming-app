package com.example.projectb;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ChooseSongActivity extends AppCompatActivity {
    TextView artistPick;
    TextView viewSongs;
    private static MainActivity ma = new MainActivity();
    private static SearchArtistActivity saa = new SearchArtistActivity();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_song);

        viewSongs = findViewById(R.id.viewingSongsBy);
        artistPick = findViewById(R.id.artistPick);
        String songsByArtist = getIntent().getStringExtra("chosen_artist");
        artistPick.setText(saa.getArtist());
        ViewArtistSongsAsync viewArtistSongsAsync = new ViewArtistSongsAsync();
        viewArtistSongsAsync.execute();
    }

    public static class ViewArtistSongsAsync extends AsyncTask<String, String, String> {
        String current = "";


        @Override
        public String doInBackground(String... strings) {

            ObjectInputStream input = ma.getInput();
            ObjectOutputStream output = ma.getOutput();
            try {
                String newPort = (String) input.readObject();
                if (newPort.equals("")) {
                    System.out.println("This artist does not exist in our database. Please search for an other artist.");
                } else {
                    Socket connection = new Socket("10.0.2.2", Integer.parseInt(newPort));
                }
                System.out.println(input.readObject());
                System.out.println(input.readObject());

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return current;
        }
    }
}
