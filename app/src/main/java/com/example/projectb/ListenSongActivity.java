package com.example.projectb;

import androidx.appcompat.app.AppCompatActivity;


import android.app.ProgressDialog;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


public class ListenSongActivity extends AppCompatActivity {
    private static SearchArtistActivity saa = new SearchArtistActivity();
    private static ChooseSongActivity csa = new ChooseSongActivity();
    ObjectInputStream input;
    ObjectOutputStream output;
    final List<File> pl = Collections.synchronizedList(new ArrayList<File>());
    static File tsank;
    String sessionType;
    MediaPlayer mPlayer=null;


    public void play() throws IOException {

         mPlayer = new MediaPlayer();

        FileInputStream fis = new FileInputStream(tsank);
        mPlayer.setDataSource(fis.getFD());
        fis.close();

        mPlayer.prepareAsync();

        mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();

            }
        });

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                FileInputStream fis;
                try {
                    mp.reset();
                    fis = new FileInputStream(tsank);
                    mp.setDataSource(fis.getFD());
                    fis.close();
                    mp.prepareAsync();
                    mp.release();
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
        sessionType = getIntent().getStringExtra("sessionType");
        System.out.println("Chosen song: " + chosenSong);
        System.out.println(sessionType);
        TextView songPlaying = findViewById(R.id.songPlaying);
        ImageButton playButton = findViewById(R.id.playButton);
        ImageButton pauseButton = findViewById(R.id.pauseButton);
        Button backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ListenSongActivity.this, MainActivity.class);
                if(mPlayer.isPlaying()) {
                    mPlayer.stop();
                }
                startActivity(intent);
            }
        });

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!mPlayer.isPlaying()) {
                    mPlayer.start();
                }
            }
        });
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mPlayer.isPlaying()) {
                    mPlayer.pause();
                }
            }
        });

        songPlaying.setText(chosenSong);
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
                fos.write(chunk.getMusicFileExtract());
                if (sessionType.equals("online")) {
                    System.out.println("online");
                    tsank = tempMp3;
                    play();
                    int count = 1;
                    while (chunk.getId() != 0) {
                        chunk = (MusicFile) input.readObject();
                        fos.write(chunk.getMusicFileExtract());
                        if (count % 100 == 0) {
                            tsank = tempMp3;
                            play();
                            fos.close();
                            fos = new FileOutputStream(tempMp3, true);
                        }
                        count++;
                    }
                } else if (sessionType.equals("offline")) {
                    System.out.println("offline");
                    while (chunk.getId() != 0) {
                        chunk = (MusicFile) input.readObject();
                        if (chunk.getId() != 0) fos.write(chunk.getMusicFileExtract());
                    }
                    tsank = tempMp3;
                    play();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            return tempMp3;
        }


    }
}


