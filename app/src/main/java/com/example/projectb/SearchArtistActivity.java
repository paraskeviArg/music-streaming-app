package com.example.projectb;

import androidx.appcompat.app.AppCompatActivity;

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
    String artist;
    EditText artistName;
    Button btn_go;
    private static MainActivity ma = new MainActivity();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_artist);

        btn_go = findViewById(R.id.btn_go);
        artistName = findViewById(R.id.artistName);

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchActivityAsync a = new SearchActivityAsync();
                a.execute();
            }
        });
    }



    public class SearchActivityAsync extends AsyncTask<String, String, String> {
        String current = "";

        @Override
        protected String doInBackground(String... strings) {
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

