package com.example.projectb;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;


public class SearchArtistActivity extends Activity {
    private static ArrayList<String> allArtists;
    private static String artist;
    private static MainActivity ma = new MainActivity();
    LinearLayout linear = null;
    public void createArtistButtons (String artistName){
        Button button = new Button(this);
        button.setWidth(500);
        button.setHeight(200);
        button.setTag(artistName);
        button.setText(artist);
        System.out.println("koumpi");
        button.setText(artistName);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.MainActivityAsyncOnline myAsyncTasks = new MainActivity.MainActivityAsyncOnline();
                myAsyncTasks.execute();
                startActivity(new Intent(SearchArtistActivity.this, ChooseSongActivity.class));
            }
        });


        linear.addView(button);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_artist);
        SearchActivityAsync searchActivityAsync = new SearchActivityAsync();
        searchActivityAsync.execute();
        Button button = null;
        linear  = (LinearLayout) findViewById(R.id.linear);




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

        ObjectInputStream input = ma.getInput();
        String artistQ;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            p = new ProgressDialog(SearchArtistActivity.this);
            p.setMessage("Showing all artists...");
            p.setIndeterminate(false);
            p.setCancelable(false);
            p.show();

        }



        @Override
        public ArrayList<String> doInBackground(String... strings) {

            try {
                artistQ = (String) input.readObject();
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

            ArrayList<Button> buttons = new ArrayList<>();

            for (String artist : allArtists){
                createArtistButtons(artist);
            }

        }

    }

}

