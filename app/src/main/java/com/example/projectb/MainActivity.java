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

public class MainActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File file = new File("Song Downloads");
        file.mkdir();
        Button btn_online = findViewById(R.id.btn_online);
        Button btn_offline = findViewById(R.id.btn_offline);
        btn_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityAsyncOnline myAsyncTasks = new MainActivityAsyncOnline();
                myAsyncTasks.execute();
                Intent intent = new Intent(MainActivity.this, SearchArtistActivity.class);
                intent.putExtra("sessionType","online");
                startActivity(intent);
            }
        });
        btn_offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivityAsyncOffline myAsyncTasks = new MainActivityAsyncOffline();
                myAsyncTasks.execute();
                Intent intent = new Intent(MainActivity.this, SearchArtistActivity.class);
                intent.putExtra("sessionType","offline");
                startActivity(intent);
            }
        });

    }

    private static ObjectInputStream input = null;
    private static ObjectOutputStream output = null;

    public ObjectInputStream getInput() {
        return input;
    }

    public ObjectOutputStream getOutput() {
        return output;
    }


    public static class MainActivityAsyncOnline extends AsyncTask<String, String, String> {
        String current = "";

        @Override
        protected String doInBackground(String... strings) {
            try {
                Consumer consumer = new Consumer();
                Socket connection = new Socket("10.0.2.2", 9600);
                System.out.println("Connected.");
                input = new ObjectInputStream(connection.getInputStream());
                output = new ObjectOutputStream(connection.getOutputStream());
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

    public static class MainActivityAsyncOffline extends AsyncTask<String, String, String> {
        String current = "";

        @Override
        protected String doInBackground(String... strings) {
            try {
                Consumer consumer = new Consumer();
                Socket connection = new Socket("10.0.2.2", 9600);
                System.out.println("Connected.");
                input = new ObjectInputStream(connection.getInputStream());
                output = new ObjectOutputStream(connection.getOutputStream());
                String sessionQ = (String) input.readObject();
                System.out.println(sessionQ);
                output.writeObject("offline");
                output.writeObject(consumer);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            return current;
        }

    }
}

