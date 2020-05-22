package com.example.projectb;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ChooseSongActivity extends Activity {
    TextView artistPick;
    TextView viewSongs;
    private static MainActivity ma = new MainActivity();
    private static SearchArtistActivity saa = new SearchArtistActivity();


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_song);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSong);
        String title = saa.getArtist();
        toolbar.setTitle("Showing all songs by: "+title);
        ViewArtistSongsAsyncTask viewArtistSongsAsyncTask = new ViewArtistSongsAsyncTask();
        viewArtistSongsAsyncTask.execute();

    }

    public static class ViewArtistSongsAsyncTask extends AsyncTask<String, String, String> {
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
                ArrayList<Song> artistSongs = new ArrayList<>();
                artistSongs =(ArrayList<Song>) input.readObject();
                System.out.println(artistSongs);
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
