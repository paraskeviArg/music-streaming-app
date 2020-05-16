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

    public void connect() throws IOException {
        System.out.println("geia1");
        Socket connection = new Socket("10.0.2.2", 9600);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityAsync myAsyncTasks = new MainActivityAsync();
        myAsyncTasks.execute();
/*
        Consumer consumer = new Consumer();
        File file = new File("Song Downloads");
        file.mkdir();
        Button btn_online = (Button) findViewById(R.id.btn_online);
        //Connect - Ask for type of session
        try {


            System.out.println("geia");
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

        btn_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                session = "online";
                startActivity(new Intent(MainActivity.this, SearchArtistActivity.class));
            }
        });

*/
    }
    public class MainActivityAsync extends AsyncTask<String, String, String> {
        String current = "";
        @Override
        protected String doInBackground(String... strings) {
            try {
                connect();
                System.out.println("geia2");
            } catch (IOException e) {
                e.printStackTrace();
            }      return current;
        }

    }
}

