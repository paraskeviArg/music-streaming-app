package com.example.projectb;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;


public class SearchArtistActivity extends Activity {
    private static ArrayList<String> allArtists;
    private static String artist;
    private static MainActivity ma = new MainActivity();
    RelativeLayout rl;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_artist);
        SearchActivityAsync searchActivityAsync = new SearchActivityAsync();
        searchActivityAsync.execute();
        ArrayList<Button> buttons = new ArrayList<>();
        Button button = null;

        for(String a: getAllArtists()) {
            button = new Button(this);
            button.setText(a);
        }
        RelativeLayout ll = (RelativeLayout)findViewById(R.id.relativeLayout);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        ll.addView(button, lp);

    }

    public String getArtist() {
        return artist;
    }

    public ArrayList<String> getAllArtists() {
        return allArtists;
    }

    @SuppressLint("StaticFieldLeak")
    public static class SearchActivityAsync extends AsyncTask<String, String, String> {
        String current = "";
        ObjectInputStream input = ma.getInput();
        String artistQ;

        @Override
        public String doInBackground(String... strings) {


            try {
                artistQ = (String) input.readObject();
                allArtists = (ArrayList<String>) input.readObject();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return current;
        }
    }

}

