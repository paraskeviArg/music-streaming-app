package com.example.projectb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.ContextWrapper;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class ListenSongActivity extends AppCompatActivity {

    private static ChooseSongActivity csa = new ChooseSongActivity();
    ObjectInputStream input;
    ObjectOutputStream output;

    public void play(File a) throws IOException {

        final MediaPlayer mPlayer = new MediaPlayer();
        final File finalTempMp = a;
        FileInputStream fis = new FileInputStream(finalTempMp);

        mPlayer.setDataSource(fis.getFD());
        mPlayer.prepareAsync();
        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                mPlayer.start();

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_song);
        MediaPlayer mPlayer = MediaPlayer.create(ListenSongActivity.this, R.raw.aaa);
        String chosenSong = getIntent().getStringExtra("songname");
        System.out.println("Chosen song: " + chosenSong);
        ListenSongAsyncTask listenSongAsyncTask = new ListenSongAsyncTask();
        listenSongAsyncTask.execute(chosenSong);

    }


    public class ListenSongAsyncTask extends AsyncTask<String, String, File> {

        MusicFile chunk = null;

        @Override
        public File doInBackground(String... song) {
            input = csa.getInput();
            output = csa.getOutput();
            File tempMp3 = null;
            try {
                output.writeObject(song[0]);
                tempMp3 = File.createTempFile("kurchina", "mp3", getCacheDir());
                tempMp3.deleteOnExit();
                FileOutputStream fos = new FileOutputStream(tempMp3, true);
                chunk = (MusicFile) input.readObject();
                int i = 0;
                while (chunk.getId() != 0) {
                    fos.write(chunk.getMusicFileExtract());
                    chunk = (MusicFile) input.readObject();
                    i++;
                }
                System.out.println(i);
                fos.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }


            return tempMp3;
        }


        protected void onPostExecute(File tempMp3) {

            super.onPostExecute(tempMp3);
            System.out.println("post");
            try {
                play(tempMp3);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
