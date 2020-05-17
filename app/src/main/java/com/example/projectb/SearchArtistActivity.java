package com.example.projectb;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SearchArtistActivity extends AppCompatActivity {
    String artist;
    EditText artistName;
    Button btn_go;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_artist);

         btn_go= (Button) findViewById(R.id.btn_go);
         artistName = (EditText) findViewById(R.id.artistName);

        btn_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                artist = artistName.getText().toString();
                System.out.println(artist);

            }
        });

    }
}
