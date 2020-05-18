package com.example.projectb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SearchArtistActivity extends AppCompatActivity {

    EditText artistName;
    Button btn_go;
    private static String artist;
    private static MainActivity ma = new MainActivity();
    public static String getArtist() {
        return artist;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_artist);

        btn_go = findViewById(R.id.btn_go);
        artistName = findViewById(R.id.artistName);

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivityAsync chooseSong = new SearchActivityAsync();
                chooseSong.execute();
                Intent intent = new Intent(SearchArtistActivity.this, ChooseSongActivity.class);
                intent.putExtra("chosen_artist",getArtist());
                startActivity(intent);
            }


        });
    }



    public class SearchActivityAsync extends AsyncTask<String, String, String> {
        String current = "";


        @Override
        public String doInBackground(String... strings) {
            artist = artistName.getText().toString();
            System.out.println(artist);

            ObjectInputStream input = ma.getInput();
            ObjectOutputStream output = ma.getOutput();
            String artistQ = null;
            try {
                artistQ = (String) input.readObject();
                System.out.println(artistQ);
                output.writeObject(artist);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return current;
        }
    }

}

