package com.example.projectb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class SearchArtistActivity extends Activity {

    ListView list;
    private static ArrayList<String> allArtists;
    private static String artist;
    private static MainActivity ma = new MainActivity();
    RelativeLayout constraint = null;


    public void createList(final String[] artists) {
        CustomList listAdapter = new CustomList(SearchArtistActivity.this, artists);
        list = findViewById(R.id.list);
        list.setAdapter(listAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(SearchArtistActivity.this, "You chose artist: " + artists[+position], Toast.LENGTH_SHORT).show();
                artist = artists[+position];

                ChooseArtistAsyncTask chooseArtistAsyncTask = new ChooseArtistAsyncTask();
                chooseArtistAsyncTask.execute();
                startActivity(new Intent(SearchArtistActivity.this, ChooseSongActivity.class));
            }
        });
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_artist);
        SearchActivityAsync searchActivityAsync = new SearchActivityAsync();
        searchActivityAsync.execute();
        Button button = null;
        constraint = findViewById(R.id.relativeArtist);

    }

    public String getArtist() {
        return artist;
    }

    public ArrayList<String> getAllArtists() {
        return allArtists;
    }

    @SuppressLint("StaticFieldLeak")
    public class SearchActivityAsync extends AsyncTask<String, String, ArrayList<String>> {
        String current = "";
        ProgressDialog p;
/*
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(SearchArtistActivity.this);
            p.setMessage("Showing all artists...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();

        }

 */

        @Override
        public ArrayList<String> doInBackground(String... strings) {

            try {
                ObjectInputStream input = ma.getInput();
                String artistQ = (String) input.readObject();
                System.out.println(artistQ);
                allArtists = (ArrayList<String>) input.readObject();
                System.out.println(allArtists);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return allArtists;
        }

        @Override
        protected void onPostExecute(ArrayList<String> allArtists) {
            super.onPostExecute(allArtists);
            System.out.println(allArtists);
            String[] artistsArray = new String[allArtists.size()];
            int i = 0;
            for(String artist: allArtists) {
                artistsArray[i] = artist;
                i++;
            }
            createList(artistsArray);
        }

    }

    public static class ChooseArtistAsyncTask extends AsyncTask<String, String, Void> {

        @Override
        protected Void doInBackground(String... strings) {
            ObjectOutputStream output = ma.getOutput();
            try {
                output.writeObject(artist);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}

