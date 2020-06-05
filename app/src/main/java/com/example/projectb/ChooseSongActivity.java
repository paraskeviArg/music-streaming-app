package com.example.projectb;

import androidx.annotation.RequiresApi;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
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
    private static ObjectInputStream input;
    private static ObjectOutputStream output;
    String sessionType;
    public void createList(final Song[] songs) {
        SongList listAdapter = new SongList(ChooseSongActivity.this, songs);
        list = findViewById(R.id.list);
        list.setAdapter(listAdapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                song = songs[+position].getTrackName();
                Intent intent = new Intent(ChooseSongActivity.this, ListenSongActivity.class);
                intent.putExtra("songname",song);
                intent.putExtra("sessionType",sessionType);
                startActivity(intent);
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_song);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarSong);
        sessionType = getIntent().getStringExtra("sessionType");
        System.out.println(sessionType);
        String title = saa.getArtist();
        toolbar.setTitle("Showing all songs by: " + title);

        ViewArtistSongsAsyncTask viewArtistSongsAsyncTask = new ViewArtistSongsAsyncTask();
        viewArtistSongsAsyncTask.execute();

    }

    @SuppressLint("StaticFieldLeak")
    public class ViewArtistSongsAsyncTask extends AsyncTask<String, String, ArrayList<Song>> {


        @Override
        public ArrayList<Song> doInBackground(String... strings) {
            input = ma.getInput();
            output = ma.getOutput();

            try {
                String newPort = (String) input.readObject();
                System.out.println();
                if (newPort.equals("")) {
                    System.out.println("This artist does not exist in our database. Please search for an other artist.");
                } else {
                    if (!newPort.equals("noRedirect")) {
                        Socket connection = new Socket("10.0.2.2", Integer.parseInt(newPort));

                    }
                }

                ArrayList<Song> artistSongs;
                allSongs = (ArrayList<Song>) input.readObject();
                System.out.println(allSongs);
                System.out.println(input.readObject());    // pick a song:


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
            Song[] songsArray = new Song[allSongs.size()];
            int i = 0;
            for (Song song : allSongs) {
                songsArray[i] = song;
                i++;
            }
            createList(songsArray);
        }
    }
    public ObjectInputStream getInput() {
        return input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }
}
