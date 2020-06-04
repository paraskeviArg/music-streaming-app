package com.example.projectb;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;


import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;

public class ListenSongActivity extends AppCompatActivity {

    private static ChooseSongActivity csa = new ChooseSongActivity();
    ObjectInputStream input;
    ObjectOutputStream output;
    private static ArrayList<File> pl = new ArrayList();

    public void play() throws Exception {

        final MediaPlayer mPlayer = new MediaPlayer();

        FileInputStream fis = new FileInputStream(pl.get(0));
        mPlayer.setDataSource(fis.getFD());
        mPlayer.prepareAsync();

        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                pl.remove(pl.get((0)));
                System.out.println("AAAAAAAAAAAAAAAA" + pl.size());
            }
        });

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                System.out.println("ok");
                mp.stop();
                mp.reset();
                FileInputStream fis;
                try {
                    fis = new FileInputStream(pl.get(0));
                    mp.setDataSource(fis.getFD());
                    mp.prepareAsync();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listen_song);
        String chosenSong = getIntent().getStringExtra("songname");
        System.out.println("Chosen song: " + chosenSong);
        ListenSongAsyncTask listenSongAsyncTask = new ListenSongAsyncTask();
        listenSongAsyncTask.execute(chosenSong);

    }


    public class ListenSongAsyncTask extends AsyncTask<String, String, File> {

        MusicFile chunk = null;
        private ProgressDialog dialog = new ProgressDialog(ListenSongActivity.this);


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
                while (chunk.getId() != 0 && i < 1000) {
                    fos.write(chunk.getMusicFileExtract());
                    chunk = (MusicFile) input.readObject();
                    pl.add(tempMp3);
                    i++;

                }
                play();
                fos = new FileOutputStream(tempMp3);
                while (chunk.getId() != 0) {
                    fos.write(chunk.getMusicFileExtract());
                    chunk = (MusicFile) input.readObject();
                    pl.add(tempMp3);
                    System.out.println(i);
                    i++;
                }
                fos.close();

            } catch (Exception e) {
                e.printStackTrace();
            }


            return tempMp3;
        }


    }
}


