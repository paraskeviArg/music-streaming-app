package com.example.projectb;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ChooseSongActivity extends Activity {

    ListView list;
    private static String song;
    private static ArrayList<Song> allSongs;
    private static MainActivity ma = new MainActivity();
    private static SearchArtistActivity saa = new SearchArtistActivity();
    RelativeLayout constraint = null;


    public void createList(final Song[] songs) {
        SongList listAdapter = new SongList(ChooseSongActivity.this, songs);
        list = findViewById(R.id.list);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(ChooseSongActivity.this, "You chose artist: " + songs[+position], Toast.LENGTH_SHORT).show();
                song = songs[+position].getTrackName();

                SearchArtistActivity.ChooseArtistAsyncTask chooseArtistAsyncTask = new SearchArtistActivity.ChooseArtistAsyncTask();
                chooseArtistAsyncTask.execute();
                //startActivity(new Intent(ChooseSongActivity.this, ChooseSongActivity.class));
            }
        });
    }

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

    @SuppressLint("StaticFieldLeak")
    public class ViewArtistSongsAsyncTask extends AsyncTask<String, String,  ArrayList<Song>> {


        @Override
        public ArrayList<Song> doInBackground(String... strings) {

            ObjectInputStream input = ma.getInput();
            ObjectOutputStream output = ma.getOutput();
            try {
                String newPort = (String) input.readObject();
                if (newPort.equals("")) {
                    System.out.println("This artist does not exist in our database. Please search for an other artist.");
                } else {
                    Socket connection = new Socket("10.0.2.2", Integer.parseInt(newPort));
                }
                ArrayList<Song> artistSongs;
                allSongs =(ArrayList<Song>) input.readObject();
                System.out.println(allSongs);
                System.out.println(input.readObject());


            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return allSongs;
        }

        @Override
        protected void onPostExecute(ArrayList<Song> allSongs) {
            super.onPostExecute(allSongs);
            System.out.println(allSongs);
            Song[] songsArray = new Song[allSongs.size()];
            int i = 0;
            for(Song song: allSongs) {
                songsArray[i] = song;
                i++;
            }
            createList(songsArray);
        }
    }
}
