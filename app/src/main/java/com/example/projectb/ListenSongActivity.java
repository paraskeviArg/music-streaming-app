package com.example.projectb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class ListenSongActivity extends AppCompatActivity {

    private static ChooseSongActivity csa = new ChooseSongActivity();
    ObjectInputStream input;
    ObjectOutputStream output;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        File file = new File("a");
        file.mkdir();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_song);
        MediaPlayer mPlayer = MediaPlayer.create(ListenSongActivity.this, R.raw.aaa);
        String chosenSong = getIntent().getStringExtra("songname");
        System.out.println("Chosen song: " +chosenSong);
        ListenSongAsyncTask listenSongAsyncTask = new ListenSongAsyncTask();
        listenSongAsyncTask.execute(chosenSong);
        //mPlayer.start();
    }





    public class ListenSongAsyncTask extends AsyncTask<String, String, String> {


        @Override
        public String doInBackground(String... song) {
            input = csa.getInput();
            output = csa.getOutput();
            try {
                output.writeObject(song[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                MusicFile chunk = (MusicFile) input.readObject();
                OutputStream outStream;
                ByteArrayOutputStream byteOutStream;
                outStream = new FileOutputStream( "a\\" + song[0] + ".mp3", true);
                byteOutStream = new ByteArrayOutputStream();
                if (chunk.getSong().getTrackName().equals("")) {
                    System.out.println((String) input.readObject());
                } else {
                    while (chunk.getId() != 0) {
                        byteOutStream.write(chunk.getMusicFileExtract());
                        chunk = (MusicFile) input.readObject();
                    }
                    byteOutStream.writeTo(outStream);
                    System.out.println("Song " + song[0] + " downloaded.");
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }


            return "A";
        }


        protected void onPostExecute(ArrayList<Song> allSongs) {

        }
    }


}
