package com.example.projectb;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URL;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        File file = new File("Song Downloads");
        file.mkdir();
        Button btn_online = (Button) findViewById(R.id.btn_online);
        btn_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityAsync myAsyncTasks = new MainActivityAsync();
                myAsyncTasks.execute();
                startActivity(new Intent(MainActivity.this, SearchArtistActivity.class));
            }
        });


    }
    public class MainActivityAsync extends AsyncTask<String, String, String> {
        String current = "";
        @Override
        protected String doInBackground(String... strings) {
            try {
                Consumer consumer = new Consumer();
                System.out.println("geia1");
                Socket connection = new Socket("10.0.2.2", 9600);
                System.out.println("geia2");
                System.out.println("Connected.");
                ObjectInputStream input = new ObjectInputStream(connection.getInputStream());
                ObjectOutputStream output = new ObjectOutputStream(connection.getOutputStream());
                String sessionQ = (String) input.readObject();
                System.out.println(sessionQ);
                output.writeObject("online");
                output.writeObject(consumer);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return current;
        }

    }
}

